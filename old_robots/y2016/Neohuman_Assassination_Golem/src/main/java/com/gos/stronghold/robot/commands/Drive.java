package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class Drive extends Command {
    private final Chassis m_chassis;
    private final double m_move;
    private final double m_angle;

    public Drive(Chassis chassis, double distance, double rotate) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        m_move = distance;
        m_angle = rotate;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.drive(m_move, m_angle);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
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
