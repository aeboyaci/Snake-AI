import javax.swing.*;

public class Graphics extends JFrame {
    public Graphics() {
        this.add(new Board());
        this.setTitle("Snake Game AI Solver");
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);  // Center the screen
    }
}
