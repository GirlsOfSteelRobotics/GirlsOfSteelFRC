package com.gos.preseason2016.team_squirtle.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends CommandBase {

    private final Joystick m_stick;
    private final Chassis m_chassis;

    public DriveByJoystick(Joystick stick, Chassis chassis) {
        m_stick = stick;
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
        m_chassis.driveByJoystick(m_stick);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
