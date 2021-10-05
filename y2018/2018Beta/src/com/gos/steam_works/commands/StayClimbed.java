package com.gos.steam_works.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StayClimbed extends Command {

    private double m_encPosition;

    public StayClimbed() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_climber);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

        Robot.m_climber.m_climbMotorB.follow(Robot.m_climber.m_climbMotorA);

        // Robot.climber.climbMotorA.setPosition(0);
        m_encPosition = Robot.m_climber.m_climbMotorA.getSelectedSensorPosition(0);
        System.out.println("Climber Encoder Position: " + m_encPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.m_climber.m_climbMotorA.set(ControlMode.Position, m_encPosition);
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
        end();
    }
}
