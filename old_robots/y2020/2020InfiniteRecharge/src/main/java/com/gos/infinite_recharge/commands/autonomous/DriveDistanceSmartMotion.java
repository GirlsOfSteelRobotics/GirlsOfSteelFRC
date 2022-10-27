package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistanceSmartMotion extends CommandBase {


    private final Chassis m_chassis;
    private final double m_distance;
    private final double m_allowableError;

    private double m_leftGoalPosition;
    private double m_rightGoalPosition;


    public DriveDistanceSmartMotion(Chassis chassis, double distance, double allowableError) {
        this.m_chassis = chassis;

        m_distance = distance;
        m_allowableError = allowableError;

        addRequirements(chassis);
    }

    @Override
    public void initialize() {

        m_leftGoalPosition = m_chassis.getLeftEncoder() + m_distance;
        m_rightGoalPosition = m_chassis.getRightEncoder() + m_distance;
        System.out.println("Drive Distance Smart Motion Starting; distnace " + m_distance + ", left=" + m_leftGoalPosition + ", " + m_rightGoalPosition + " deadband " + m_allowableError);
    }

    @Override
    public void execute() {
        m_chassis.driveDistance(m_leftGoalPosition, m_rightGoalPosition);
        // m_chassis.smartVelocityControl(48, 48);

        System.out.println("left goal position" + m_leftGoalPosition + ", right goal position" + m_rightGoalPosition);
    }

    @Override
    public boolean isFinished() {
        double leftError = m_chassis.getLeftEncoder() - m_leftGoalPosition;
        double rightError = m_chassis.getRightEncoder() - m_rightGoalPosition;
        double avgError = (leftError + rightError) / 2;

        if (Math.abs(avgError) < m_allowableError) {
            System.out.println("Done!");
            return true;
        }
        else {
            // System.out.println("drive to distance, left error: " + leftError + ", right error: " + rightError + ", allowableError: " + m_allowableError);
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeed(0);
        System.out.println("Drive Distance Smart Motion, end");

    }
}
