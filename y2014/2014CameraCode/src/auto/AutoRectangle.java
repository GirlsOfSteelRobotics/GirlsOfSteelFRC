package auto;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpijavacv.WPIPoint;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CosmicHawk
 */
  public class AutoRectangle extends StaticWidget {

    WPIPoint topLeft;
    WPIPoint topRight;
    WPIPoint bottomLeft;
    WPIPoint bottomRight;

    public AutoRectangle(WPIPoint[] points) {
        Math.acos(.5);
        topLeft = points[0];
        for (int i = 1; i < 4; i++) {
            if (points[i].getX() < topLeft.getX()) {
                topLeft = points[i];
            }

            // Finds bottom left point. doing similar stuff as before.
            bottomLeft = null;
            for (int z = 0; z < 4; z++) {
                if (points[z] != topLeft && (bottomLeft == null || points[z].getX() < bottomLeft.getX())) {
                    bottomLeft = points[z];
                }
            }
            //Flips which is top and which is bottom if they are wrong from the previous bit.
            if (bottomLeft.getY() < topLeft.getY()) {
                WPIPoint temp = topLeft;
                topLeft = bottomLeft;
                bottomLeft = temp;
            }

            //Finds right most points. Checks that it is not one of the left points then decides if it is the top or bottom right point.
            topRight = null;
            bottomRight = null;
            for (int y = 0; y < 4; y++) {
                if (points[y] != topLeft && points[y] != bottomLeft) {
                    if (topRight == null) {
                        topRight = points[y];
                    } else {
                        bottomRight = points[y];
                    }
                }
            }
            //Flips which is top and which is bottom if they are wrong from the previous bit. 
            if (bottomRight.getY() < topRight.getY()) {
                WPIPoint temp = topRight;
                topRight = bottomRight;
                bottomRight = temp;
            }
        }
    }
    // Finds distance between two points.

    public double distance(WPIPoint point1, WPIPoint point2) {
        int dx = point1.getX() - point2.getX();
        int dy = point1.getY() - point2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getRightHeight() {
        return distance(topRight, bottomRight);
    }

    public double getLeftHeight() {
        return distance(topLeft, bottomLeft);
    }

    public double getBottomWidth() {
        return distance(bottomLeft, bottomRight);
    }

    public double getTopWidth() {
        return distance(topLeft, topRight);
    }
    //  public double getBottomLeftAngle(){
    //    return (90-Math.acos((bottomRight.getX()-bottomLeft.getX())/ getBottomWidth()));
    //  }

    /**
     * Finds the midpoint between two points
     * @param point1
     * @param point2
     * @return the midpoint
     */
    public WPIPoint midpoint(WPIPoint point1, WPIPoint point2) {
        int xc = (point1.getX() + point2.getX()) / 2;
        int yc = (point1.getY() + point2.getY()) / 2;
        return new WPIPoint(xc, yc);
    }

    /**
     * Finds the center of the given points
     * @param topLeft
     * @param bottomLeft
     * @param topRight
     * @param bottomRight
     * @return the center point
     */
    public WPIPoint getCenterpoint() {
        WPIPoint cy = midpoint(topLeft, bottomLeft);
        WPIPoint cx = midpoint(topLeft, topRight);
        return new WPIPoint(cx.getX(), cy.getY());
    }
    
    public double averageHeight() {
        return ((getLeftHeight()+ getRightHeight())/2);
    }
    
    public double averageWidth() {
        return ((getBottomWidth()+ getTopWidth())/2);
    }
    
    public double averageWidthHeightRatio() {
        return (averageWidth()/averageHeight());
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void propertyChanged(Property prprt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
