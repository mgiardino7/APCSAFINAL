import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradeMenu extends JFrame {
    // labels to show score and upgrade information
    private JLabel scoreLabel;
    private JLabel clickLabel, pointLabel, luckLabel;

    // buttons for each upgrade and for going back
    private JButton clickUpgradeBtn, pointUpgradeBtn, luckUpgradeBtn;
    private JButton backButton;

    // constructor to set up the upgrade menu window
    public UpgradeMenu() {
        setTitle("Upgrades"); // set window title
        setSize(400, 300); // set window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // only close this window
        setLocationRelativeTo(null); // center the window on screen
        setLayout(new GridLayout(5, 1, 10, 10)); // layout with 5 rows and vertical spacing

        scoreLabel = new JLabel(); // label to show total score
        updateScoreLabel(); // set initial score display

        clickLabel = new JLabel(); // label for click upgrade
        pointLabel = new JLabel(); // label for point upgrade
        luckLabel = new JLabel(); // label for luck upgrade

        clickUpgradeBtn = new JButton("Upgrade Clicks"); // button for click upgrade
        pointUpgradeBtn = new JButton("Upgrade Points"); // button for point upgrade
        luckUpgradeBtn = new JButton("Upgrade Luck"); // button for luck upgrade

        backButton = new JButton("Back to Main Menu"); // button to return to main menu
        backButton.addActionListener(e -> {
            dispose(); // close upgrade menu
            new HomeMenu(); // open main menu
        });

        // action when click upgrade button is pressed
        clickUpgradeBtn.addActionListener(e -> {
            int cost = UpgradeManager.getUpgradeCost(UpgradeManager.clickUpgradeLevel); // get upgrade cost
            if (UpgradeManager.totalScore >= cost) { // check if user can afford upgrade
                UpgradeManager.totalScore -= cost; // subtract cost
                UpgradeManager.clickUpgradeLevel++; // increase upgrade level
                refreshLabels(); // update ui
            }
        });

        // action when point upgrade button is pressed
        pointUpgradeBtn.addActionListener(e -> {
            int cost = UpgradeManager.getUpgradeCost(UpgradeManager.pointUpgradeLevel); // get upgrade cost
            if (UpgradeManager.totalScore >= cost) { // check if user can afford upgrade
                UpgradeManager.totalScore -= cost; // subtract cost
                UpgradeManager.pointUpgradeLevel++; // increase upgrade level
                refreshLabels(); // update ui
            }
        });

        // action when luck upgrade button is pressed
        luckUpgradeBtn.addActionListener(e -> {
            int cost = UpgradeManager.getUpgradeCost(UpgradeManager.luckUpgradeLevel); // get upgrade cost
            if (UpgradeManager.totalScore >= cost) { // check if user can afford upgrade
                UpgradeManager.totalScore -= cost; // subtract cost
                UpgradeManager.luckUpgradeLevel++; // increase upgrade level
                refreshLabels(); // update ui
            }
        });

        // add all components to the window
        add(scoreLabel);
        add(buildPanel(clickLabel, clickUpgradeBtn)); // add click upgrade row
        add(buildPanel(pointLabel, pointUpgradeBtn)); // add point upgrade row
        add(buildPanel(luckLabel, luckUpgradeBtn)); // add luck upgrade row
        add(backButton); // add back button

        refreshLabels(); // update all labels with current data
        setVisible(true); // show the window
    }

    // creates a panel that contains a label and a button in a single row
    private JPanel buildPanel(JLabel label, JButton button) {
        JPanel panel = new JPanel(new BorderLayout()); // layout with center and side
        panel.add(label, BorderLayout.CENTER); // label goes in the center
        panel.add(button, BorderLayout.EAST); // button goes on the right
        return panel;
    }

    // updates the score label at the top
    private void updateScoreLabel() {
        scoreLabel.setText("Total Score: " + UpgradeManager.totalScore);
    }

    // updates all upgrade labels and enables/disables buttons
    private void refreshLabels() {
        updateScoreLabel(); // update the score label first

        // calculate costs for each upgrade
        int clickCost = UpgradeManager.getUpgradeCost(UpgradeManager.clickUpgradeLevel);
        int pointCost = UpgradeManager.getUpgradeCost(UpgradeManager.pointUpgradeLevel);
        int luckCost = UpgradeManager.getUpgradeCost(UpgradeManager.luckUpgradeLevel);

        // update label text with current level and cost
        clickLabel.setText("More Clicks (Lvl " + UpgradeManager.clickUpgradeLevel + ") — Cost: " + clickCost);
        pointLabel.setText("More Points (Lvl " + UpgradeManager.pointUpgradeLevel + ") — Cost: " + pointCost);
        luckLabel.setText("More Luck (Lvl " + UpgradeManager.luckUpgradeLevel + ") — Cost: " + luckCost);

        // enable or disable buttons based on current score
        clickUpgradeBtn.setEnabled(UpgradeManager.totalScore >= clickCost);
        pointUpgradeBtn.setEnabled(UpgradeManager.totalScore >= pointCost);
        luckUpgradeBtn.setEnabled(UpgradeManager.totalScore >= luckCost);
    }
}
