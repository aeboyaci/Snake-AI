import java.util.*;

public class AI {
    private static SearchMethod searchMethod;

    private final Snake snake;

    public AI(Snake snake) {
        this.snake = snake;
    }

    public static void setSearchMethod(SearchMethod method) {
        searchMethod = method;
    }

    public Node performSearch(Node goal) {
        if (searchMethod == SearchMethod.BEST_FIRST_SEARCH) {
            LinkedList<Node> nodes = bestFirstSearch(goal);
            if (nodes == null) {
                return snake.moveRandom();
            }
            if (nodes.size() != 0) {
                return nodes.remove();
            }
            return null;
        }
        if (searchMethod == SearchMethod.A_STAR_SEARCH) {
            return playAStar(goal);
        }
        return null;
    }

    private LinkedList<Node> bestFirstSearch(Node goal) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> manhattanDistance(node, goal)));
        Map<Node, Node> visited = new HashMap<>();

        Node start = snake.getHead();
        queue.add(start);

        int depth = 0;
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(goal) || depth == 8) {
                LinkedList<Node> path = new LinkedList<>();
                if (!current.equals(start)) {
                    path.addFirst(current);
                }
                while (visited.containsKey(current)) {
                    current = visited.get(current);
                    if (!current.equals(start)) {
                        path.addFirst(current);
                    }
                }
                return path;
            }

            List<Node> neighbors = current.getNeighbours();
            for (Node neighbor : neighbors) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, current);
                    if (!snake.hasCollision(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }

            depth++;
        }

        return null;
    }

    private Node playAStar(Node goal) {
        Snake virtualSnake = new Snake();
        Node[] virtualNodes = new Node[Game.BOARD_SIZE];
        int count = 0;
        for (Node node : snake.getNodes()) {
            virtualNodes[count++] = new Node(node.getX(), node.getY());
        }
        virtualSnake.setNodes(virtualNodes);
        virtualSnake.setLength(snake.getLength());

        LinkedList<Node> realPath = aStarSearch(snake, goal);
        if (realPath == null) {
            LinkedList<Node> tailPath = aStarSearch(snake, snake.getTail());
            if (tailPath == null) {
                int i = 0;
                Node n = snake.moveRandom();
                while (i < 32 && n == null) {
                    n = snake.moveRandom();
                    i++;
                }
                return n;
            }
            if (!tailPath.isEmpty()) {
                return tailPath.remove();
            }
            return null;
        }

        LinkedList<Node> virtualPath = (LinkedList<Node>)realPath.clone();
        while (!virtualSnake.getHead().equals(goal)) {
            while (!virtualPath.isEmpty()) {
                virtualSnake.move(virtualPath.remove());
            }
        }
        LinkedList<Node> virtualTailPath = aStarSearch(virtualSnake, virtualSnake.getTail());
        if (virtualTailPath != null) {
            if (!realPath.isEmpty()) {
                return realPath.remove();
            }
            return null;
        }
        LinkedList<Node> tailPath = aStarSearch(snake, snake.getTail());
        if (tailPath != null) {
            if (!tailPath.isEmpty()) {
                return tailPath.remove();
            }
            return null;
        }
        if (!realPath.isEmpty()) {
            return realPath.remove();
        }
        return null;
    }

    private LinkedList<Node> aStarSearch(Snake s, Node goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<Node>(Comparator.comparingInt(node -> manhattanDistance(node, goal)));
        List<String> closedSet = new ArrayList<String>();
        Node start = s.getHead();

        start.setG(0);
        start.setH(0);
        openSet.add(start);
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            List<Node> neighbours = current.getNeighbours();
            for (Node node : neighbours) {
                if (closedSet.contains(node.toString()) || s.hasCollision(node) || !s.canMoveTo(node)) {
                    continue;
                }
                node.setParent(current);

                if (node.equals(goal)) {
                    LinkedList<Node> path = new LinkedList<Node>();
                    while (node.getParent() != null) {
                        path.addFirst(node);
                        node = node.getParent();
                    }
                    return path;
                }

                int tentativeGScore = current.getG() + manhattanDistance(current, node);
                if (tentativeGScore < node.getG()) {
                    node.setG(tentativeGScore);
                    node.setH(manhattanDistance(node, goal));
                    openSet.add(node);
                }
            }

            openSet.remove(current);
            closedSet.add(current.toString());
        }

        return null;
    }

    private int manhattanDistance(Node first, Node second) {
        return Math.abs(first.getX() - second.getX()) + Math.abs(first.getY() - second.getY());
    }
}
