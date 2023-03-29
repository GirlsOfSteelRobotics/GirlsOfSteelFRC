package com.gos.chargedup;

import edu.wpi.first.math.geometry.Translation2d;

public class RectangleInterface {

    //right bottom corner
    private final double m_rightBottomX;
    private final double m_rightBottomY;

    //left top corner
    private final double m_leftTopX;
    private final double m_leftTopY;

    //field
    private final GosField.RectangleObject m_field;

    //constructor
    public RectangleInterface(double leftTopX, double leftTopY, double rightBottomX, double rightBottomY, GosField field, String name) {
        m_rightBottomX = rightBottomX;
        m_rightBottomY = rightBottomY;

        m_leftTopX = leftTopX;
        m_leftTopY = leftTopY;

        m_field = new GosField.RectangleObject(leftTopX, leftTopY, rightBottomX, rightBottomY, field, name);
    }

    public boolean pointIsInRect(Translation2d loc) {
        AllianceFlipper.maybeFlip(loc);
        double yLoc = loc.getY();
        double xLoc = loc.getX();
        m_field.updateRectangle();

        return (m_rightBottomY < yLoc && yLoc < m_leftTopY
            && m_leftTopX < xLoc && xLoc < m_rightBottomX);


    }

}
