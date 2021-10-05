package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OldTurnInPlace extends Command {

    private static final double ERROR = 3.0;

    private final double m_headingTarget;
    private final double m_speed;

    private final WPI_TalonSRX m_leftTalon = Robot.m_chassis.getLeftTalon();
    private final WPI_TalonSRX m_rightTalon = Robot.m_chassis.getRightTalon();

    public OldTurnInPlace(double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_chassis);
        m_headingTarget = degrees;
        m_speed = 0.2;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.m_chassis.setInverted(true);
        Robot.m_chassis.zeroSensors();
        System.out.println("OldTurnInPlace: intitialized");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (m_headingTarget > 0) {
            m_leftTalon.set(ControlMode.PercentOutput, -m_speed);
            m_rightTalon.set(ControlMode.PercentOutput, m_speed);
        } else {
            m_leftTalon.set(ControlMode.PercentOutput, m_speed);
            m_rightTalon.set(ControlMode.PercentOutput, -m_speed);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.m_chassis.getYaw() - m_headingTarget) < ERROR;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_chassis.stop();
        Robot.m_chassis.setInverted(false);
        System.out.println("OldTurnInPlace: finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
