import java.util.Random;

public class Food {
    private final int positionX;
    private final int positionY;

    public Food() {
        positionX = generateRandomInteger(Board.WIDTH);
        positionY = generateRandomInteger(Board.HEIGHT);
    }

    private int generateRandomInteger(int max) {
        Random random = new Random();
        return random.nextInt(max / Board.SNAKE_NODE_SIZE) * Board.SNAKE_NODE_SIZE;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
