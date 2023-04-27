import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class RectangleObject implements Drawable {
    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;

    public RectangleObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Creating random colors for the randomly sized rectangles
        // We will see these appear in white no matter the color in the 2D projection
        Random rainbowColors = new Random();
        this.color = new Color(rainbowColors.nextInt(250), rainbowColors.nextInt(250), rainbowColors.nextInt(250));
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Method to draw rectangle objects
    @Override
    public void drawObject(final Graphics2D g2d) {
        Color difColor = g2d.getColor();
        g2d.setColor(color);
        g2d.drawRect((int) x, (int) y, (int) width, (int) height);
        g2d.setColor(difColor);
    }

    // A distance calculation using 4 points on the rectangle
    public Point2D.Double rectIntersection(final Ray ray){
        Point2D.Double closestIntersection = null;
        Double closestDistance = Double.MAX_VALUE;

        Line2D.Double L1 = new Line2D.Double(x, y, x + width, y);
        Line2D.Double L2 = new Line2D.Double(x + width, y, x + width, y + height);
        Line2D.Double L3 = new Line2D.Double(x + width, y + height, x, y + height);
        Line2D.Double L4 = new Line2D.Double(x, y + height, x, y);

        Line2D.Double[] sides = {L1, L2, L3, L4};

        for (Line2D.Double side : sides) {
             Point2D.Double intersection = RaycasterUtils.intersection(ray, side);

            if (intersection != null) {
                double distance = intersection.distance(ray.getStartingCoor());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestIntersection = intersection;
                }
            }

        }
        return closestIntersection;
    }
}
