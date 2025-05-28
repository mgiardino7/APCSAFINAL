import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements MouseListener {
    //learned off of https://www.youtube.com/watch?v=5VrMVSDjeso&t=1190s
    // VERY VERY helpful as it helped us understand how to make the GUI
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int TILE_SIZE = 50;

    private Block[][] grid;
    private int score;
    private int durability;
    private boolean gameOver = false;
    private Random random = new Random();

    private BufferedImage dirtImg, stoneImg, coalImg, ironImg, diamondImg, questionImg;
    private JButton backButton;

    // constructor for initializing game panel and ui
    public GamePanel() {
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE + 100));
        setLayout(null); // absolute positioning for button
        addMouseListener(this);
        loadImages(); // load all resource images
        generateGrid(); // create new game board

        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(10, ROWS * TILE_SIZE + 50, 200, 30);
        backButton.addActionListener(e -> backToMainMenu());
        this.add(backButton);
    }

    // loads image assets used for blocks
    private void loadImages() {
        try {
            dirtImg = ImageIO.read(new File("resources/dirt.png"));
            stoneImg = ImageIO.read(new File("resources/stone.png"));
            coalImg = ImageIO.read(new File("resources/coal.png"));
            ironImg = ImageIO.read(new File("resources/iron.png"));
            diamondImg = ImageIO.read(new File("resources/diamond.png"));
            questionImg = ImageIO.read(new File("resources/question.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "image loading failed.");
            System.exit(1);
        }
    }

    // creates a randomized grid of blocks and resets score and durability
    private void generateGrid() {
        score = 0;
        durability = 30 + UpgradeManager.getClickBonus();
        gameOver = false;
        grid = new Block[ROWS][COLS];

        double luck = UpgradeManager.getLuckMultiplier();
        double diamondChance = 0.01 * luck;
        double ironChance = 0.04 * luck;
        double coalChance = 0.10 * luck;
        double stoneChance = 0.25 * luck;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                double chance = random.nextDouble();
                String type;
                if (chance < diamondChance) type = "diamond";
                else if (chance < diamondChance + ironChance) type = "iron";
                else if (chance < diamondChance + ironChance + coalChance) type = "coal";
                else if (chance < diamondChance + ironChance + coalChance + stoneChance) type = "stone";
                else type = "dirt";

                grid[r][c] = new Block(type);
            }
        }
        repaint();
    }

    // draws the game board and ui elements
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Block block = grid[r][c];
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                BufferedImage img = block.isMined() || gameOver ? getImageForType(block.getType()) : questionImg;
                g.drawImage(img, x, y, TILE_SIZE, TILE_SIZE, null);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("score: " + score, 10, ROWS * TILE_SIZE + 20);
        g.drawString("clicks left: " + durability, 10, ROWS * TILE_SIZE + 40);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("game over! final score: " + score, 10, ROWS * TILE_SIZE + 70);
            g.drawString("click to play again or use back button.", 10, ROWS * TILE_SIZE + 90);
        }
    }

    // returns the correct image for each block type
    private BufferedImage getImageForType(String type) {
        switch (type) {
            case "dirt": return dirtImg;
            case "stone": return stoneImg;
            case "coal": return coalImg;
            case "iron": return ironImg;
            case "diamond": return diamondImg;
            default: return questionImg;
        }
    }

    // handles mouse click events for mining blocks
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver) {
            generateGrid(); // restart game on click after game over
            return;
        }

        int col = e.getX() / TILE_SIZE;
        int row = e.getY() / TILE_SIZE;
        if (row < ROWS && col < COLS) {
            Block block = grid[row][col];
            if (!block.isMined()) {
                block.setMined(true);
                int blockPoints = block.getPoints();
                if (isValuable(block.getType())) {
                    blockPoints += UpgradeManager.getPointBonus();
                }
                score += blockPoints;
                durability--;

                if (durability <= 0) {
                    gameOver = true;
                    UpgradeManager.totalScore += score;
                    revealAllBlocks(); // show all blocks at end of game
                }
                repaint();
            }
        }
    }

    // reveals all blocks when the game ends
    private void revealAllBlocks() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c].setMined(true);
            }
        }
        repaint();
    }

    // handles returning to the main menu
    private void backToMainMenu() {
        if (!gameOver && score > 0) {
            UpgradeManager.totalScore += score; // save score if game quit early
        }

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose(); // close the game window
        new HomeMenu(); // open main menu
    }

    // checks if a block type is valuable (adds point bonus)
    private boolean isValuable(String type) {
        return type.equals("coal") || type.equals("iron") || type.equals("diamond");
    }

    // unused mouse events (required by interface)
    // ChatGPT recommended that we add this to prevent errors
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
