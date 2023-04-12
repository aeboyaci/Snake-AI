import javax.swing.*;
import java.awt.*;

public class HomeGraphics extends JFrame {
    public HomeGraphics() {
        this.setLayout(new FlowLayout());

        JButton bestFirstSearchButton = new JButton("Best-First Search");
        bestFirstSearchButton.addActionListener(e -> {
            System.out.println("Best-First Search\n---");
            AI.setSearchMethod(SearchMethod.BEST_FIRST_SEARCH);
            new GameGraphics(this);
        });
        add(bestFirstSearchButton);

        JButton aStarSearchButton = new JButton("A* Search");
        aStarSearchButton.addActionListener(e -> {
            System.out.println("A* Search\n---");
            AI.setSearchMethod(SearchMethod.A_STAR_SEARCH);
            new GameGraphics(this);
        });
        add(aStarSearchButton);

        this.setTitle("Snake Game AI Solver");
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);  // Center the screen
    }
}
