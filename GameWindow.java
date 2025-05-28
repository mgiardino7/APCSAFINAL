import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        // Set the title of the window
        setTitle("DilksCraft");
        // Ensure the program exits when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make the window non-resizable
        setResizable(false);
        // Add the game panel (where the game is rendered) to the window
        add(new GamePanel());
        // Pack the window, which sizes it based on its content
        pack();
        // Set the window to be centered on the screen
        setLocationRelativeTo(null);
        // Make the window visible
        setVisible(true);
    }
}
