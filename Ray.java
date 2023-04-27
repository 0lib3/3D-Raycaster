import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
public class Ray extends Line2D.Double{
    private Point2D startingCoor;
    private Point2D endingCoor;
    private double distance;

    public Ray(Point2D startingCoor, Point2D endingCoor) {
        super(startingCoor.getX(), startingCoor.getY(), endingCoor.getX(), endingCoor.getY());
        this.startingCoor = startingCoor;
        this.endingCoor = endingCoor;
    }

    public Point2D getStartingCoor() {
        return startingCoor;
    }

    public void setStartingCoor(Point2D startingCoor) {
        this.startingCoor = startingCoor;
    }

    public Point2D getEndingCoor() {
        return endingCoor;
    }

    public void setEndingCoor(Point2D endingCoor) {
        this.endingCoor = endingCoor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // Draws a 2D ray on the left side screen
    public void drawRay(final Graphics2D g2){
        g2.setColor(Color.WHITE);
        g2.draw(new Line2D.Double(startingCoor, endingCoor));
    }
}
