// Launches the DilksCraft game
public class MiningGameLauncher {
    public static void main(String[] args) {
        // Start the GUI on the Event Dispatch Thread for thread safety (used chatGPT to learn how)
        javax.swing.SwingUtilities.invokeLater(() -> new GameWindow());
    }
}
