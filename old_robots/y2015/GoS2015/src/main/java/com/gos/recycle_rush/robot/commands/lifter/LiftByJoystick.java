package com.gos.recycle_rush.robot.commands.lifter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.OI;
import com.gos.recycle_rush.robot.subsystems.Lifter;

/**
 *
 */
public class LiftByJoystick extends CommandBase {

    private final Lifter m_lifter;
    private final Joystick m_operatorJoystick;

    public LiftByJoystick(OI oi, Lifter lifter) {
        m_lifter = lifter;
        m_operatorJoystick = oi.getOperatorJoystick();
        addRequirements(m_lifter);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_lifter.moveByJoystick(m_operatorJoystick);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        // return (m_lifter.isAtTopLevel() || m_lifter.isAtTop());
        // return (m_lifter.isAtTop() || m_lifter.isAtTopLevel()); //||
        // m_lifter.isAtBottom());
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_lifter.stop();
    }


}
