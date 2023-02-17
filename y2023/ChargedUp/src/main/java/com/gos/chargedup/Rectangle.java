package com.gos.chargedup;

public class Rectangle {
    private double leftX;
    private double rightX;
    private double topY;

    public Rectangle(double left_x, double right_x, double top_y) {
        leftX = left_x;
        rightX = right_x;
        topY = top_y;
    }

    public boolean contains(double x, double y) {
        if (((x < rightX) && (x > leftX)) && (y < topY)) {
            return true;
        }
        return false;
    }
}
