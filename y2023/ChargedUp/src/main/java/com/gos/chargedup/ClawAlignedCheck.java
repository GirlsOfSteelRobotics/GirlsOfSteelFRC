package com.gos.chargedup;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ClawAlignedCheck {

    private ChassisSubsystem m_chassis;
    private ArmSubsystem m_arm;
    private TurretSubsystem m_turret;

    private double m_robotAngle;

    private double m_turretAngle;

    private double m_armExtension;


    public ClawAlignedCheck(ChassisSubsystem chassis, ArmSubsystem arm, TurretSubsystem turret) {
        m_chassis = chassis;
        m_arm = arm;
        m_turret = turret;

        updateValues();

    }

    public void updateValues() {
        m_robotAngle = m_chassis.getPose().getRotation().getRadians();
        m_turretAngle = Math.toRadians(m_turret.getTurretAngleDeg());
        m_armExtension = m_arm.getArmLengthMeters();
    }

    //get claw position relative to the robot (assume robot is at (0,0))
    public Translation2d getRelativeClawPosition() {
        double xComp = m_armExtension * Math.cos(m_robotAngle - m_turretAngle);
        double yComp = m_armExtension * Math.sin(m_robotAngle - m_turretAngle);;
        return new Translation2d(xComp, yComp);
    }

    //add claw position to absolute position of robot
    public boolean isClawAtPoint(Translation2d nodePos) {

        updateValues();

        double xPos = nodePos.getX();
        double yPos = nodePos.getY();

        double acceptError = 2.0; //Todo: Update with the actual error value in meters
        Pose2d chassisPos = m_chassis.getPose();
        Translation2d clawPos = new Translation2d(chassisPos.getX() + getRelativeClawPosition().getX(), chassisPos.getY() + getRelativeClawPosition().getY());

        System.out.println("Claw at point: " + Boolean.toString(Math.abs(clawPos.getX() - xPos) < acceptError && Math.abs(clawPos.getY() - yPos) < acceptError));
        System.out.print("x" + Math.abs(clawPos.getX() - xPos));
        System.out.print("  ");
        System.out.print("y" + Math.abs(clawPos.getY() - yPos));
        return (Math.abs(clawPos.getX() - xPos) < acceptError && Math.abs(clawPos.getY() - yPos) < acceptError);

    }

    public double getClawXValue(Translation2d nodePos) {
        return m_chassis.getPose().getX() + getRelativeClawPosition().getX() - nodePos.getX();
    }

    public double getClawYValue(Translation2d nodePos) {
        return m_chassis.getPose().getY() + getRelativeClawPosition().getY() - nodePos.getY();
    }


}
