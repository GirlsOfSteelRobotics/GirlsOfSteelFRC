package com.gos.outreach2016.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors.Direction;

/**
 *
 */
public class AccessoryRightFwd extends Command {

    private final AccessoryMotors m_accessoryMotors;

    public AccessoryRightFwd(AccessoryMotors accessoryMotors) {
        m_accessoryMotors = accessoryMotors;
        requires(m_accessoryMotors);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_accessoryMotors.startRight(Direction.kFwd);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_accessoryMotors.stopRight();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
