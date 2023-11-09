package com.gos.recycle_rush.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class ResetGyro extends Command {
    private final Chassis m_chassis;

    public ResetGyro(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.resetGyro();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
