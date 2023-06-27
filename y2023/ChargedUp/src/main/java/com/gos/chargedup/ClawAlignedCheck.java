package com.gos.chargedup;


import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.TankDriveChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClawAlignedCheck {

    private final TankDriveChassisSubsystem m_chassis;

    private final ArmExtensionSubsystem m_armExtension;
    private double m_angle;

    private double m_armExtensionLength;



    public ClawAlignedCheck(TankDriveChassisSubsystem chassis, ArmExtensionSubsystem armExtension) {
        m_chassis = chassis;
        m_armExtension = armExtension;
        //DONT need a turret subsystem -- turret absolute angle is passed as a parameter

    }


    //get claw position relative to the robot (assume robot is at (0,0))
    private Translation2d getRelativeClawPosition() {
        double xComp = m_armExtensionLength * Math.cos(m_angle);
        double yComp = m_armExtensionLength * Math.sin(m_angle);
        return new Translation2d(xComp, yComp);

    }

    //add claw position to absolute position of robot
    public boolean isClawAtPoint(Translation2d nodePos, double angle) {

        m_angle = angle;
        m_armExtensionLength = m_armExtension.getArmLengthMeters();

        double xPos = nodePos.getX();
        double yPos = nodePos.getY();

        GosDoubleProperty acceptError = new GosDoubleProperty(false, "Claw Aligned Acceptable Error", 1.0); //Todo: Update with the actual error value in meters
        Translation2d clawPos = getAbsoluteClawPos(m_chassis.getPose());

        SmartDashboard.putBoolean("Check If Ready To Score", Math.abs(clawPos.getX() - xPos) < acceptError.getValue() && Math.abs(clawPos.getY() - yPos) < acceptError.getValue());
        return (Math.abs(clawPos.getX() - xPos) < acceptError.getValue() && Math.abs(clawPos.getY() - yPos) < acceptError.getValue());

    }

    private Translation2d getAbsoluteClawPos(Pose2d chassisPos) {
        return new Translation2d(chassisPos.getX() + getRelativeClawPosition().getX(), chassisPos.getY() + getRelativeClawPosition().getY());
    }

}
