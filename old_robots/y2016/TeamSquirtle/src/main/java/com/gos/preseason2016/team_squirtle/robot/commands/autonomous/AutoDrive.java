package com.gos.preseason2016.team_squirtle.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
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
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.resetDistance(); //need to create resetDistance method
        m_tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.driveForward(); //need to created driveForward method
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_tim.get() > 3);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
