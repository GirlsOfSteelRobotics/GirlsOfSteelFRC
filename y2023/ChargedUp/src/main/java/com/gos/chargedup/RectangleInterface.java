package com.gos.chargedup;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import java.util.ArrayList;

public class RectangleInterface {

    //right bottom corner
    private final double m_rightBottomX;
    private final double m_rightBottomY;

    //left top corner
    private final double m_leftTopX;
    private final double m_leftTopY;

    //field and name
    private final Field2d m_field;

    private final String m_name;

    //constructor
    public RectangleInterface(double leftTopX, double leftTopY, double rightBottomX, double rightBottomY, Field2d field, String name) {
        m_rightBottomX = rightBottomX;
        m_rightBottomY = rightBottomY;

        m_leftTopX = leftTopX;
        m_leftTopY = leftTopY;

        m_field = field;
        m_name = name;

        ArrayList<Pose2d> poses = new ArrayList<>();
        poses.add(new Pose2d(leftTopX, leftTopY, new Rotation2d(0.0)));
        poses.add(new Pose2d(rightBottomX, leftTopY, new Rotation2d(0.0)));
        poses.add(new Pose2d(leftTopX, rightBottomY, new Rotation2d(0.0)));
        poses.add(new Pose2d(rightBottomX, rightBottomY, new Rotation2d(0.0)));
        m_field.getObject(m_name).setPoses(poses);
    }

    public boolean pointIsInRect(double xLoc, double yLoc) {
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            return (m_rightBottomY < yLoc && yLoc < m_leftTopY
                && AllianceFlipper.flipX(m_leftTopX) > xLoc && xLoc > AllianceFlipper.flipX(m_rightBottomX));
        }
        else {
            return (m_rightBottomY < yLoc && yLoc < m_leftTopY
                && m_leftTopX < xLoc && xLoc < m_rightBottomX);
        }

    }

}
