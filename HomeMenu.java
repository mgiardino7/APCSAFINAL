import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomeMenu extends JFrame {
    // constructor to create the main menu window
    public HomeMenu() {
        setTitle("DilksCraft - Main Menu"); // set the window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit app when window is closed
        setSize(300, 300); // set fixed window size
        setLocationRelativeTo(null); // center the window on screen
        setResizable(false); // prevent resizing

        JPanel panel = new JPanel(); // main panel for layout
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // grid layout with 4 rows, vertical spacing

        // create title label and center it
        JLabel title = new JLabel("Welcome to DilksCraft!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18)); // set font style and size
        panel.add(title); // add title to panel

        // create button to start the game
        JButton playButton = new JButton("Play Game");
        playButton.addActionListener((ActionEvent e) -> {
            new GameWindow(); // open game window
            dispose(); // close the main menu
        });
        panel.add(playButton); // add play button to panel

        // create button to open the upgrade menu
        JButton upgradeButton = new JButton("Upgrade Menu");
        upgradeButton.setEnabled(true); // ensure the button is active
        upgradeButton.addActionListener(e -> new UpgradeMenu()); // open upgrade menu
        panel.add(upgradeButton); // add upgrade button to panel


        add(panel); // add the panel to the frame
        setVisible(true); // show the window
    }
}
