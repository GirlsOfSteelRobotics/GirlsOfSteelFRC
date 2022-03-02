package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class AutoTurnCounterClockwise extends CommandBase {

    private final Chassis m_chassis;
    private double m_gyroInitial;

    public AutoTurnCounterClockwise(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_gyroInitial = m_chassis.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.autoTurnCounterclockwise();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_gyroInitial - m_chassis.getGyroAngle()) >= 90;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
