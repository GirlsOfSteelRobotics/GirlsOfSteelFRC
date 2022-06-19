package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;
import com.gos.stronghold.robot.subsystems.Pivot;

/**
 *
 */
public class ResetEncoderDistance extends CommandBase {

    private final Chassis m_chassis;
    private final Flap m_flap;
    private final Pivot m_pivot;

    public ResetEncoderDistance(Chassis chassis, Flap flap, Pivot pivot) {
        m_chassis = chassis;
        m_flap = flap;
        m_pivot = pivot;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.resetEncoderDistance();
        m_flap.resetDistance();
        m_pivot.resetDistance();
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
