package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OldTurnInPlace extends Command {

    private final double headingTarget;
    private final double speed;
    private final static double ERROR = 3.0;

    private final WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    private final WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    public OldTurnInPlace(double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        headingTarget = degrees;
        speed = 0.2;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.chassis.setInverted(true);
        Robot.chassis.zeroSensors();
        System.out.println("OldTurnInPlace: intitialized");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (headingTarget > 0) {
            leftTalon.set(ControlMode.PercentOutput, -speed);
            rightTalon.set(ControlMode.PercentOutput, speed);
        } else {
            leftTalon.set(ControlMode.PercentOutput, speed);
            rightTalon.set(ControlMode.PercentOutput, -speed);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (Math.abs(Robot.chassis.getYaw() - headingTarget) < ERROR);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.chassis.stop();
        Robot.chassis.setInverted(false);
        System.out.println("OldTurnInPlace: finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
