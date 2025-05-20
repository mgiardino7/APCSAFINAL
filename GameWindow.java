import javax.swing.JFrame;

// Sets up the main game window for DilksCraft
public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("DilksCraft"); // Title of the game window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        setResizable(false); // Prevent resizing
        add(new GamePanel()); // Add the game panel (core gameplay)
        pack(); // Adjust window size to fit contents
        setLocationRelativeTo(null); // Center window on screen
        setVisible(true); // Display the window
    }
}
