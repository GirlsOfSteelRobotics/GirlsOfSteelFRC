package com.gos.recycle_rush.robot.commands.tests;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class TestWheels extends CommandBase {
    private final Chassis m_chassis;

    public TestWheels(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.spinWheelsSlowly();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
