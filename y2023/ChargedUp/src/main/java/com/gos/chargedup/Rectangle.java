package com.gos.chargedup;

public class Rectangle {
    private double leftX;
    private double rightX;
    private double y;
    private boolean maxY;

    public Rectangle(double left_x, double right_x, double y, boolean max) {
        leftX = left_x;
        rightX = right_x;
        this.y = y;
        maxY = max;
    }

    public boolean contains(double x, double y) {
        if (maxY == true) {
            if (((x < rightX) && (x > leftX)) && (y < this.y)) {
                return true;
            }
        }
        else {
            if (((x < rightX) && (x > leftX)) && (y > this.y)) {
                return true;
            }
        }
        return false;
    }
}
