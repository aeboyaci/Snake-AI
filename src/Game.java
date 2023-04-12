import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Game extends JPanel {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    public static final int BOARD_SIZE = (WIDTH * HEIGHT) / (Snake.SNAKE_NODE_SIZE * Snake.SNAKE_NODE_SIZE);

    private final FoodProvider foodProvider = new FoodProvider();

    private final Snake snake;
    private final AI snakeAi;

    public Game(JFrame homeGraphics, JFrame gameGraphics) {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.decode("#004e00"));
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gameGraphics.setVisible(false);
                    setVisible(false);
                    homeGraphics.setVisible(true);
                }
            }
        });

        snake = new Snake();
        spawnFood();

        snakeAi = new AI(snake);

        GameRunner gameRunner = new GameRunner(this);
        new Thread(gameRunner).start();
    }

    private void spawnFood() {
        foodProvider.spawn(snake);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Node next = snakeAi.performSearch(foodProvider.getFood());

        if (next == null) {
            snake.setMoving(false);
        }
        else {
            snake.move(next);
        }

        if (snake.isMoving()) {
            Node food = foodProvider.getFood();
            g.setColor(Color.RED);
            g.fillOval(food.getX(), food.getY(), Snake.SNAKE_NODE_SIZE, Snake.SNAKE_NODE_SIZE);

            // Draw head
            Node head = snake.getHead();
            g.setColor(Color.ORANGE);
            g.fillRect(head.getX(), head.getY(), Snake.SNAKE_NODE_SIZE, Snake.SNAKE_NODE_SIZE);

            g.setColor(Color.decode("#6179F2"));
            for (int i = 1; i < snake.getLength(); i++) {
                Node node = snake.getNode(i);
                g.fillRect(node.getX(), node.getY(), Snake.SNAKE_NODE_SIZE, Snake.SNAKE_NODE_SIZE);
            }

            if (food.equals(head)) {
                snake.increaseLength();
                spawnFood();
            }

            return;
        }

        String gameOverText = "GAME OVER";
        String restartGameText = "Press ENTER to restart the game!";

        Font headerFont = new Font("Times New Roman", Font.BOLD, 36);
        Font textFont = new Font("Times New Roman", Font.PLAIN, 24);

        g.setColor(Color.RED);
        g.setFont(headerFont);
        int x = (WIDTH - getFontMetrics(g.getFont()).stringWidth(gameOverText)) / 2;
        int y = HEIGHT / 2;
        g.drawString(gameOverText, x, y);

        g.setColor(Color.WHITE);
        g.setFont(textFont);
        x = (WIDTH - getFontMetrics(g.getFont()).stringWidth(restartGameText)) / 2;
        y = (HEIGHT / 2) + 40;
        g.drawString(restartGameText, x, y);
    }
}
