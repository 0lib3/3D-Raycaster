import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class RaycasterProjectionPanel extends JPanel {

    /**
     * Root driver object to keep track of sizing.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Overhead panel to access the generated rays.
     */
    private final RaycasterPanel RAYCASTER_PANEL;
    private Camera camera;
    private Ray ray;
    private static final double DELTA = 40.0;

    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
        this.camera = new Camera(0,0);
    }

    public void update() {
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Drawing the 3D projection
        project(g2d);
    }

    // Iterating through the Camera object's ray list to draw walls
    // Walls have a height, and span along x and y
    private void project(final Graphics2D g2){
        Ray[] rayList = this.RAYCASTER_PANEL.getRayList();
        for (int i = 0; i < rayList.length; i++) {
            if (rayList[i] == null) continue;
            double angle = RaycasterUtils.normalize(i, 0, rayList.length, -camera.getFov() / 2, camera.getFov() / 2);
            double newAngle = Math.toRadians(angle);

            double newDistance = rayList[i].getDistance() * Math.cos(newAngle);
            double wallX = RaycasterUtils.normalize(i, 0, rayList.length, 0, this.getPreferredSize().width);
            double wallHeight = this.getPreferredSize().height * DELTA / newDistance;
            double wallY = this.getPreferredSize().height / 2 - wallHeight / 2;

            this.drawProjection(rayList[i], wallX, wallHeight, wallY, g2);
        }
    }

    private void drawProjection(Ray ray, double wallX, double wallHeight, double wallY, Graphics2D g2){
        g2.setColor(Color.WHITE);
        g2.drawLine((int) wallX, (int) wallY, (int) (wallX), (int) (wallY + wallHeight));
    }
}

