import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    public static final int SNAKE_NODE_SIZE = 25;
    public static final int BOARD_SIZE = (WIDTH * HEIGHT) / (SNAKE_NODE_SIZE * SNAKE_NODE_SIZE);

    private final Timer timer = new Timer(150, this);

    private int[] snakePositionX;
    private int[] snakePositionY;
    public Direction direction;
    public int snakeLength;
    public boolean isMoving;

    public Food food;

    public Board() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isMoving) {
                    start();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && canMoveToDirection(Direction.LEFT)) {
                    direction = Direction.LEFT;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && canMoveToDirection(Direction.RIGHT)) {
                    direction = Direction.RIGHT;
                } else if (e.getKeyCode() == KeyEvent.VK_UP && canMoveToDirection(Direction.UP)) {
                    direction = Direction.UP;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && canMoveToDirection(Direction.DOWN)) {
                    direction = Direction.DOWN;
                }
            }
        });

        start();
    }

    public void start() {
        snakePositionX = new int[BOARD_SIZE];
        snakePositionY = new int[BOARD_SIZE];
        snakeLength = 5;
        direction = Direction.RIGHT;
        isMoving = true;
        spawnFood();
        timer.start();
    }

    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakePositionX[i] = snakePositionX[i - 1];
            snakePositionY[i] = snakePositionY[i - 1];
        }

        switch (direction) {
            case LEFT:
                snakePositionX[0] -= SNAKE_NODE_SIZE;
                break;
            case RIGHT:
                snakePositionX[0] += SNAKE_NODE_SIZE;
                break;
            case UP:
                snakePositionY[0] -= SNAKE_NODE_SIZE;
                break;
            case DOWN:
                snakePositionY[0] += SNAKE_NODE_SIZE;
                break;
        }
    }

    public void checkCollision() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePositionX[0] == snakePositionX[i]) && (snakePositionY[0] == snakePositionY[i])) {
                isMoving = false;
                break;
            }
        }

        boolean isXOverflowed = snakePositionX[0] < 0 || snakePositionX[0] > WIDTH - SNAKE_NODE_SIZE;
        boolean isYOverflowed = snakePositionY[0] < 0 || snakePositionY[0] > HEIGHT - SNAKE_NODE_SIZE;
        if (isXOverflowed || isYOverflowed) {
            isMoving = false;
        }

        if (!isMoving) {
            timer.stop();
        }
    }

    public void spawnFood() {
        food = new Food();
    }

    public void eatFood() {
        if ((snakePositionX[0] == food.getPositionX()) && (snakePositionY[0] == food.getPositionY())) {
            snakeLength++;
            spawnFood();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isMoving) {
            g.setColor(Color.RED);
            g.fillOval(food.getPositionX(), food.getPositionY(), SNAKE_NODE_SIZE, SNAKE_NODE_SIZE);

            g.setColor(Color.GREEN);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(snakePositionX[i], snakePositionY[i], SNAKE_NODE_SIZE, SNAKE_NODE_SIZE);
            }
            return;
        }

        String gameOverText = "GAME OVER";
        String pressAnyKeyText = "Press any key to restart the game";

        Font headerFont = new Font("Times New Roman", Font.BOLD, 36);
        Font textFont = new Font("Times New Roman", Font.PLAIN, 24);

        g.setColor(Color.RED);
        g.setFont(headerFont);
        int x = (WIDTH - getFontMetrics(g.getFont()).stringWidth(gameOverText)) / 2;
        int y = HEIGHT / 2;
        g.drawString(gameOverText, x, y);

        g.setColor(Color.WHITE);
        g.setFont(textFont);
        x = (WIDTH - getFontMetrics(g.getFont()).stringWidth(pressAnyKeyText)) / 2;
        y = (HEIGHT / 2) + 40;
        g.drawString(pressAnyKeyText, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving) {
            move();
            checkCollision();
            eatFood();
        }

        repaint();
    }

    private boolean canMoveToDirection(Direction nextDirection) {
        if (nextDirection == Direction.LEFT && direction == Direction.RIGHT) {
            return false;
        }
        if (nextDirection == Direction.RIGHT && direction == Direction.LEFT) {
            return false;
        }
        if (nextDirection == Direction.UP && direction == Direction.DOWN) {
            return false;
        }
        if (nextDirection == Direction.DOWN && direction == Direction.UP) {
            return false;
        }

        return true;
    }
}
