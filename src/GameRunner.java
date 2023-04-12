public class GameRunner implements Runnable {
    private final Game panel;

    public GameRunner(Game panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(25);
                panel.repaint();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
