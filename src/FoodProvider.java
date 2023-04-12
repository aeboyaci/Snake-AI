import java.util.Random;

public class FoodProvider {
    private Node food;

    public void spawn(Snake snake) {
        Node node = generate();
        while (snake.hasCollision(node)) {
            node = generate();
        }
        food = node;
    }

    public Node getFood() {
        return food;
    }

    private Node generate() {
        int x = generateRandomInteger(Game.WIDTH);
        int y = generateRandomInteger(Game.HEIGHT);

        return new Node(x, y);
    }

    private int generateRandomInteger(int max) {
        Random random = new Random();
        return random.nextInt(max / Snake.SNAKE_NODE_SIZE) * Snake.SNAKE_NODE_SIZE;
    }
}
