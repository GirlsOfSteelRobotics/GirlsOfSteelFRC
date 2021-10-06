package com.gos.deep_space;

import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

public class TargetPair {

    private final RotatedRect m_left;
    private final RotatedRect m_right;

    public TargetPair(RotatedRect left, RotatedRect right) {
        this.m_left = left;
        this.m_right = right;
    }

    /**
     * @return the x coordinate in the middle of the two contours
     */
    public double getTargetCenterX() {
        return (m_left.center.x + m_right.center.x) / 2;
    }

    public Point getTargetCenterPoint() {
        return new Point(getTargetCenterX(), (m_left.center.y + m_right.center.y) / 2);
    }

    /**
     * @return the average area of the two contours that form a set
     */
    public double getTargetAverageArea() {
        return ((m_left.size.height * m_left.size.width) + (m_right.size.height * m_right.size.width)) / 2;
    }

    /**
     * @return the average height of the two contours
     */
    public double getHeight() {
        return (m_left.size.height + m_right.size.height) / 2;
    }

    /**
     * @return the distance between the center of mass of the two target pairs
     */
    public double getPairDistance() {
        return m_right.center.x - m_left.center.x;
    }

}
