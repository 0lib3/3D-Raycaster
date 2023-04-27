import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Displays and updates the logic for the top-down raycasting implementation.
 * This class is where the collision detection and movement occurs, whereas the
 * RaycasterPerspectivePanel just projects it to a pseudo-3d environment.
 */
public final class RaycasterPanel extends JPanel {

    /**
     * We need to keep a reference to the parent swing app for sizing and
     * other bookkeeping.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Number of rays to fire from the camera.
     */
    private final int RESOLUTION;
    private static final int MAX_DIST = 2000;
    private List<RectangleObject> recList;
    private Camera camera;
    private Ray ray;
    private final Ray[] RAY_LIST;
    public RaycasterPanel(final RaycasterRunner raycasterRunner) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RESOLUTION = this.getPreferredSize().width;
        this.requestFocusInWindow(true);

        // Lists to be populated
        this.recList = createList();
        this.RAY_LIST = new Ray[this.RESOLUTION];

        // Passing in the camera object
        // Passing in motion listener for mouse tracking and key listener to detect left or right angle adjustment
        this.camera = new Camera(0,0);
        this.addMouseMotionListener(this.camera.getCameraMotionListener());
        this.addKeyListener(this.camera.getCameraAngleListener());
    }

    public Ray[] getRayList() {
        return RAY_LIST;
    }

    // Populating recList
    public java.util.List createList() {
        List<RectangleObject> objects = new ArrayList<>();
        Random ran = new Random();
        // for loop that generates 15 objects on the panel
        for (int i = 0; i < 16; i++) {
            double x = ran.nextDouble(500);
            double y = ran.nextDouble(500);
            double width = ran.nextDouble(300) + 10;
            double height = ran.nextDouble(100) + 10;
            objects.add(new RectangleObject(x, y, width, height));
        }
        return objects;
    }

    public void update() {
    }

    private void drawRays(final Graphics2D g2) {
        for (int i = 0; i < this.RAY_LIST.length; i++) {
            this.RAY_LIST[i].drawRay(g2);
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Populating the field
        for (RectangleObject r : recList) {
            r.drawObject((Graphics2D) g);
        }

        // Run the calculation
        // Draw rays so they are visible on the 2D field
        computeRays();
        drawRays(g2d);
    }

    // Ray goes from a single ray to ~640 rays (this.RESOLUTION) to simulate an FOV
    private void computeRays(){
        for (int r = 0; r < this.RESOLUTION; r++) {
            Point mousePosition = new Point((int) camera.getPositionX(), (int) camera.getPositionY());

            double max = this.camera.getCurrentAngle() + this.camera.getFov() / 2;
            double min = this.camera.getCurrentAngle() - this.camera.getFov() / 2;

            double rayAngle = RaycasterUtils.normalize(r,0, this.RESOLUTION, min, max);

            Point2D.Double startingCoor = new Point2D.Double(mousePosition.getX(), mousePosition.getY());
            Point2D.Double endingCoor = new Point2D.Double(mousePosition.getX() + RaycasterPanel.MAX_DIST * Math.cos(Math.toRadians(rayAngle)), mousePosition.getY() + RaycasterPanel.MAX_DIST * Math.sin(Math.toRadians(rayAngle)));

            Ray ray = new Ray(startingCoor, endingCoor);

            Point2D.Double closestIntersection = null;
            double closestDistance = Double.MAX_VALUE;

            for (RectangleObject rectangle : recList) {
                Point2D.Double intersection = rectangle.rectIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.distance(startingCoor);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestIntersection = intersection;
                        ray.setDistance(closestDistance);
                    }
                }
            }
            if (closestIntersection != null) {
                ray.setEndingCoor(closestIntersection);
            }
            RAY_LIST[r] = ray;
        }
    }
}
