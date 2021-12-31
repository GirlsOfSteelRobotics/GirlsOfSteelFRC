package com.gos.preseason2016.team_squirtle.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDrive extends Command {

    private final Chassis m_chassis;
    public double m_distance;
    public Timer m_tim;

    public AutoDrive(Chassis chassis) {
        m_chassis = chassis;
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.resetDistance(); //need to create resetDistance method
        m_tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.driveForward(); //need to created driveForward method
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (m_tim.get() > 3);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
