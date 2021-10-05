package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SimpleDrive extends Command {

    private final WPI_TalonSRX m_leftTalon = Robot.m_chassis.getLeftTalon();
    private final WPI_TalonSRX m_rightTalon = Robot.m_chassis.getRightTalon();

    public SimpleDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_leftTalon.set(ControlMode.PercentOutput, 0.5);
        m_rightTalon.set(ControlMode.PercentOutput, 0.5);
        System.out.println("SimpleDrive: leftA " + m_leftTalon.getInverted());
        System.out.println("SimpleDrive: rightA " + m_rightTalon.getInverted());
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_leftTalon.set(ControlMode.PercentOutput, 0.5);
        m_rightTalon.set(ControlMode.PercentOutput, 0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
