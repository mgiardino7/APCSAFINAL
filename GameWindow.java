import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("DilksCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
