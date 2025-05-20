import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

// Main game panel where the DilksCraft game logic and rendering takes place (used chatgpt along w/ a youtube tutorial to learn)
public class GamePanel extends JPanel implements MouseListener {
    // Grid dimensions and tile size in pixels
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int TILE_SIZE = 50;

    private Block[][] grid;       // 2D array representing the game grid
    private int score;            // Player's current score
    private int durability = 30;  // Number of allowed clicks before game ends
    private boolean gameOver = false; // Flag to track if the game is over
    private Random random = new Random(); // Random generator for block types

    // Images for each block type
    private BufferedImage dirtImg, stoneImg, coalImg, ironImg, diamondImg, questionImg;

    // Constructor sets up the game panel
    public GamePanel() {
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE + 100));
        addMouseListener(this); // Enable mouse input
        loadImages();           // Load images for rendering
        generateGrid();         // Create the initial grid
    }

    // Loads images from the file system for different block types
    private void loadImages() {
        try {
            dirtImg = ImageIO.read(new File("resources/dirt.png"));
            stoneImg = ImageIO.read(new File("resources/stone.png"));
            coalImg = ImageIO.read(new File("resources/coal.png"));
            ironImg = ImageIO.read(new File("resources/iron.png"));
            diamondImg = ImageIO.read(new File("resources/diamond.png"));
            questionImg = ImageIO.read(new File("resources/question.png")); // Hidden block image
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Image loading failed.");
            System.exit(1);
        }
    }

    // Fills the grid with randomly chosen blocks
    private void generateGrid() {
        score = 0;
        durability = 30;
        gameOver = false;
        grid = new Block[ROWS][COLS];

        // Randomly assign block types based on probability
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
        repaint(); // Redraw the grid
    }

    // Draws the entire game screen (chatgpt was used to do Graphics/Graphics2D class material)
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw each block
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Block block = grid[r][c];
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                // Show actual image if mined or game over, otherwise show mystery box
                BufferedImage img = block.isMined() || gameOver ? getImageForType(block.getType()) : questionImg;
                g.drawImage(img, x, y, TILE_SIZE, TILE_SIZE, null);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        // Display score and durability
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, ROWS * TILE_SIZE + 20);
        g.drawString("Clicks Left: " + durability, 10, ROWS * TILE_SIZE + 40);

        // Show game over message if applicable
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Game Over! Final Score: " + score, 10, ROWS * TILE_SIZE + 70);
            g.drawString("Click to Play Again or Close Window to Quit.", 10, ROWS * TILE_SIZE + 90);
        }
    }

    // Returns the image corresponding to a block type
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

    // Handles mouse click events (mining logic, created by chatGPT)
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver) {
            generateGrid(); // Restart game on click if game is over
            return;
        }

        int col = e.getX() / TILE_SIZE;
        int row = e.getY() / TILE_SIZE;

        // Ensure click is inside the grid
        if (row < ROWS && col < COLS) {
            Block block = grid[row][col];
            if (!block.isMined()) {
                block.setMined(true); // Mine the block
                score += block.getPoints(); // Add points
                durability--; // Decrease durability
                if (durability <= 0) {
                    gameOver = true;
                    revealAllBlocks(); // Show the whole grid
                }
                repaint();
            }
        }
    }

    // Reveals all blocks when the game ends
    private void revealAllBlocks() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c].setMined(true);
            }
        }
        repaint();
    }

    // Unused mouse events (required by MouseListener interface)
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
