import javax.swing.*;
//Launcher
public class MiningGameLauncher {
    public static void main(String[] args) {
        //AI generated, told to use to reduce stress on a single thread
        //Allows us to run the program and the HomeMenu separately, meaning if you close one tab, the others will stay open
        SwingUtilities.invokeLater(() -> new HomeMenu());
    }
}
