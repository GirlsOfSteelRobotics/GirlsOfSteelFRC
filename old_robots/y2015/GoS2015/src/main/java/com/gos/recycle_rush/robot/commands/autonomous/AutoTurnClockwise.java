package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class AutoTurnClockwise extends CommandBase {

    private final Chassis m_chassis;
    private double m_gyroInitial;

    public AutoTurnClockwise(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_gyroInitial = m_chassis.getGyroAngle();

        // setTimeout(1);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.autoTurnClockwise();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_chassis.getGyroAngle() - m_gyroInitial) >= 90;

        // return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
