import javax.swing.*;

public class GameGraphics extends JFrame {
    public GameGraphics(JFrame homeGraphics) {
        homeGraphics.setVisible(false);

        this.add(new Game(homeGraphics, this));
        this.setTitle("Snake Game AI Solver");
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);  // Center the screen
    }
}
