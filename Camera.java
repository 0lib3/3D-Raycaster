import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
public class Camera {
    private double positionX;
    private double positionY;
    private List<Ray> rayObjects;
    private int currentAngle;
    private final double FOV = 70;
    private CameraMotionListener cameraMotionListener;
    private CameraAngleListener cameraAngleListener;

    public Camera(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.currentAngle = currentAngle;
        this.cameraMotionListener = new CameraMotionListener();
        this.cameraAngleListener = new CameraAngleListener();
        this.rayObjects = new ArrayList<>();
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
    public List<Ray> getRayObjects() {
        return rayObjects;
    }
    public void setRayObjects(List<Ray> rayObjects) {
        this.rayObjects = rayObjects;
    }
    public double getCurrentAngle() {
        return this.currentAngle;
    }
    public double getFov() {
        return this.FOV;
    }
    public CameraMotionListener getCameraMotionListener() {
        return cameraMotionListener;
    }
    public CameraAngleListener getCameraAngleListener() {return cameraAngleListener;}

    // Tracks the cursor
    // Allows you to 'advance' through a hallway
    private class CameraMotionListener extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            Point position = e.getPoint();
            setPositionX(position.x);
            setPositionY(position.y);
        }
    }

    // Tracking user key movement, left or right key adjusts the angle of the camera
    // Imitates standing in a certain spot and rotating head
    private class CameraAngleListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                currentAngle++;
            }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                currentAngle--;
            }
        }
    }
}
