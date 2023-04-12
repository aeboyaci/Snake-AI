import java.util.ArrayList;
import java.util.List;

public class Node {
    private int x;
    private int y;
    private int g; // cost from start to current node
    private int h; // g + heuristic estimate of cost to goal
    private Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        g = Integer.MAX_VALUE;
        h = Integer.MAX_VALUE;
        parent = null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public double getF() {
        return g + h;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getNeighbours() {
        List<Node> neighbours = new ArrayList<Node>();
        neighbours.add(new Node(this.getX() + Snake.SNAKE_NODE_SIZE, this.getY()));  // Right
        neighbours.add(new Node(this.getX() - Snake.SNAKE_NODE_SIZE, this.getY()));  // Left
        neighbours.add(new Node(this.getX(), this.getY() + Snake.SNAKE_NODE_SIZE));  // Down
        neighbours.add(new Node(this.getX(), this.getY() - Snake.SNAKE_NODE_SIZE));  // Up

        return neighbours;
    }

    @Override
    public String toString() {
        return x + "-" + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node other = (Node)obj;
            return this.getX() == other.getX() && this.getY() == other.getY();
        }
        return false;
    }
}
