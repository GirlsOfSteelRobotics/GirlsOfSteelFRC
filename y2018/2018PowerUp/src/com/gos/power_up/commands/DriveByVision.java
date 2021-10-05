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

    private final WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    private final WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    private double dist;
    private static final double SPEED_PERCENT = 0.2;

    public DriveByVision() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.blobs);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        dist = Robot.blobs.distanceBetweenBlobs();
        if (Robot.blobs.distanceBetweenBlobs() == -1) {
            System.out.println("DriveByVision initialize: line not in sight!!");
            end();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        dist = Robot.blobs.distanceBetweenBlobs();
        System.out.print("Distance = " + dist + " ");
        if (dist == -1) {
            System.out.println("DriveByVision: Can't see line!");
        } else if (dist < Blobs.GOAL_DISTANCE) {//too far --> go forward
            System.out.println("DriveByVision: driving forward");
            leftTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
            rightTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
        } else if (dist > Blobs.GOAL_DISTANCE) {//too close --> go backward
            System.out.println("DriveByVision: driving backard");
            leftTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
            rightTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (dist == -1 || Math.abs(dist - Blobs.GOAL_DISTANCE) < Blobs.ERROR_THRESHOLD);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
