package com.gos.recycle_rush.robot.commands.lifter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.OI;
import com.gos.recycle_rush.robot.subsystems.Lifter;

/**
 *
 */
public class LiftByJoystick extends Command {

    private final Lifter m_lifter;
    private final Joystick m_operatorJoystick;

    public LiftByJoystick(OI oi, Lifter lifter) {
        m_lifter = lifter;
        m_operatorJoystick = oi.getOperatorJoystick();
        requires(m_lifter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_lifter.moveByJoystick(m_operatorJoystick);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // return (m_lifter.isAtTopLevel() || m_lifter.isAtTop());
        // return (m_lifter.isAtTop() || m_lifter.isAtTopLevel()); //||
        // m_lifter.isAtBottom());
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_lifter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
