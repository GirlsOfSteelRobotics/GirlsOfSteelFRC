package com.gos.deep_space;

import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

public class TargetPair {

    private final RotatedRect left;
    private final RotatedRect right;

    public TargetPair(RotatedRect left, RotatedRect right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return the x coordinate in the middle of the two contours
     */
    public double getTargetCenterX() {
        return (left.center.x + right.center.x) / 2;
    }

    public Point getTargetCenterPoint() {
        return new Point(getTargetCenterX(), (left.center.y + right.center.y) / 2);
    }

    /**
     * @return the average area of the two contours that form a set
     */
    public double getTargetAverageArea() {
        return ((left.size.height * left.size.width) + (right.size.height * right.size.width)) / 2;
    }

    /**
     * @return the average height of the two contours
     */
    public double getHeight() {
        return (left.size.height + right.size.height) / 2;
    }

    /**
     * @return the distance between the center of mass of the two target pairs
     */
    public double getPairDistance() {
        return (right.center.x - left.center.x);
    }

}
