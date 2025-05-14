import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements MouseListener {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int TILE_SIZE = 50;

    private Block[][] grid;
    private int score;
    private int durability = 30;
    private boolean gameOver = false;
    private Random random = new Random();

    private BufferedImage dirtImg, stoneImg, coalImg, ironImg, diamondImg, questionImg;

    public GamePanel() {
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE + 100));
        addMouseListener(this);
        loadImages();
        generateGrid();
    }

    private void loadImages() {
        try {
            dirtImg = ImageIO.read(new File("resources/dirt.png"));
            stoneImg = ImageIO.read(new File("resources/stone.png"));
            coalImg = ImageIO.read(new File("resources/coal.png"));
            ironImg = ImageIO.read(new File("resources/iron.png"));
            diamondImg = ImageIO.read(new File("resources/diamond.png"));
            questionImg = ImageIO.read(new File("resources/question.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Image loading failed.");
            System.exit(1);
        }
    }

    private void generateGrid() {
        score = 0;
        durability = 30;
        gameOver = false;
        grid = new Block[ROWS][COLS];

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                double chance = random.nextDouble();
                String type;

                if (chance < 0.01) type = "diamond";
                else if (chance < 0.05) type = "iron";
                else if (chance < 0.15) type = "coal";
                else if (chance < 0.40) type = "stone";
                else type = "dirt";

                grid[r][c] = new Block(type);
            }
        }
        repaint();
    }

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
        g.drawString("Score: " + score, 10, ROWS * TILE_SIZE + 20);
        g.drawString("Clicks Left: " + durability, 10, ROWS * TILE_SIZE + 40);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Game Over! Final Score: " + score, 10, ROWS * TILE_SIZE + 70);
            g.drawString("Click to Play Again or Close Window to Quit.", 10, ROWS * TILE_SIZE + 90);
        }
    }

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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver) {
            generateGrid();
            return;
        }

        int col = e.getX() / TILE_SIZE;
        int row = e.getY() / TILE_SIZE;
        if (row < ROWS && col < COLS) {
            Block block = grid[row][col];
            if (!block.isMined()) {
                block.setMined(true);
                score += block.getPoints();
                durability--;
                if (durability <= 0) {
                    gameOver = true;
                    revealAllBlocks();
                }
                repaint();
            }
        }
    }

    private void revealAllBlocks() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c].setMined(true);
            }
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
