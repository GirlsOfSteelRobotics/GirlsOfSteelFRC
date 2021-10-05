package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import com.gos.power_up.subsystems.Blobs;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByVision extends Command {
    private static final double SPEED_PERCENT = 0.2;

    private final WPI_TalonSRX m_leftTalon = Robot.m_chassis.getLeftTalon();
    private final WPI_TalonSRX m_rightTalon = Robot.m_chassis.getRightTalon();

    private double m_dist;

    public DriveByVision() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_blobs);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_dist = Robot.m_blobs.distanceBetweenBlobs();
        if (Robot.m_blobs.distanceBetweenBlobs() == -1) {
            System.out.println("DriveByVision initialize: line not in sight!!");
            end();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_dist = Robot.m_blobs.distanceBetweenBlobs();
        System.out.print("Distance = " + m_dist + " ");
        if (m_dist == -1) {
            System.out.println("DriveByVision: Can't see line!");
        } else if (m_dist < Blobs.GOAL_DISTANCE) {  //too far --> go forward
            System.out.println("DriveByVision: driving forward");
            m_leftTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
            m_rightTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
        } else if (m_dist > Blobs.GOAL_DISTANCE) {  //too close --> go backward
            System.out.println("DriveByVision: driving backard");
            m_leftTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
            m_rightTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_dist == -1 || Math.abs(m_dist - Blobs.GOAL_DISTANCE) < Blobs.ERROR_THRESHOLD;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
