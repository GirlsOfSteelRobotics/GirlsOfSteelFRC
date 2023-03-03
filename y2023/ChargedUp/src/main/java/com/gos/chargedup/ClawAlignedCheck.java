package com.gos.chargedup;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ClawAlignedCheck {

    private final ChassisSubsystem m_chassis;
    private final ArmSubsystem m_arm;
    private double m_angle;

    private double m_armExtension;


    public ClawAlignedCheck(ChassisSubsystem chassis, ArmSubsystem arm) {
        m_chassis = chassis;
        m_arm = arm;
        //DONT need a turret -- turret absolute angle is passed as a parameter

    }

    public void updateValues(double angle) {
        m_angle = angle;
        m_armExtension = m_arm.getArmLengthMeters();
    }

    //get claw position relative to the robot (assume robot is at (0,0))
    public Translation2d getRelativeClawPosition() {
        double xComp = m_armExtension * Math.cos(m_angle);
        double yComp = m_armExtension * Math.sin(m_angle);
        return new Translation2d(xComp, yComp);

    }

    //add claw position to absolute position of robot
    public boolean isClawAtPoint(Translation2d nodePos, double angle) {

        updateValues(angle);

        double xPos = nodePos.getX();
        double yPos = nodePos.getY();

        double acceptError = 1; //Todo: Update with the actual error value in meters
        Translation2d clawPos = getAbsoluteClawPos(m_chassis.getPose());

        System.out.println("Claw at point: " + Boolean.toString(Math.abs(clawPos.getX() - xPos) < acceptError && Math.abs(clawPos.getY() - yPos) < acceptError));

        return (Math.abs(clawPos.getX() - xPos) < acceptError && Math.abs(clawPos.getY() - yPos) < acceptError);

    }

    public Translation2d getAbsoluteClawPos(Pose2d chassisPos) {
        return new Translation2d(chassisPos.getX() + getRelativeClawPosition().getX(), chassisPos.getY() + getRelativeClawPosition().getY());
    }

}
