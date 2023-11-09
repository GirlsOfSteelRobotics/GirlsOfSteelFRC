package com.gos.outreach2016.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors.Direction;

/**
 *
 */
public class AccessoryRightFwd extends Command {

    private final AccessoryMotors m_accessoryMotors;

    public AccessoryRightFwd(AccessoryMotors accessoryMotors) {
        m_accessoryMotors = accessoryMotors;
        addRequirements(m_accessoryMotors);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_accessoryMotors.startRight(Direction.FWD);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_accessoryMotors.stopRight();
    }


}
