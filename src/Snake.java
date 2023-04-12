import java.util.List;
import java.util.Random;

public class Snake {
    public static final int SNAKE_NODE_SIZE = 25;

    private Node[] nodes;
    private int snakeLength;
    private boolean moving;

    public Snake() {
        nodes = new Node[Game.BOARD_SIZE];
        snakeLength = 8;
        moving = true;

        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            nodes[i] = new Node(0, 0);
        }
    }

    public boolean hasCollision(Node node) {
        for (int i = 0; i < snakeLength; i++) {
            if (nodes[i].equals(node)) {
                return true;
            }
        }
        return false;
    }

    public Node getHead() {
        return nodes[0];
    }

    public Node getTail() {
        return nodes[snakeLength - 1];
    }

    public Node getNode(int i) {
        return nodes[i];
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setLength(int length) {
        snakeLength = length;
    }

    public int getLength() {
        return snakeLength;
    }

    public void increaseLength() {
        snakeLength++;
    }

    public void move(Node next) {
        if (hasCollision(next)) {
            moving = false;
            return;
        }

        for (int i = snakeLength; i > 0; i--) {
            nodes[i].setX(nodes[i - 1].getX() % Game.WIDTH);
            nodes[i].setY(nodes[i - 1].getY() % Game.HEIGHT);
        }
        nodes[0].setX(next.getX());
        nodes[0].setY(next.getY());
    }

    public Node moveRandom() {
        Node head = getHead();
        List<Node> neighbours = head.getNeighbours();

        Random random = new Random();
        int idx = random.nextInt(4);
        if (canMoveTo(neighbours.get(idx))) {
            return neighbours.get(idx);
        }

        return null;
    }

    public boolean canMoveTo(Node node) {
        if (node.getX() > (Game.WIDTH - SNAKE_NODE_SIZE) ||
                node.getY() > (Game.HEIGHT - SNAKE_NODE_SIZE)) {
            return false;
        }
        if (node.getX() < 0 || node.getY() < 0) {
            return false;
        }

        for (int i = 0; i < snakeLength; i++) {
            if (nodes[i].equals(node)) {
                return false;
            }
        }

        return true;
    }
}
