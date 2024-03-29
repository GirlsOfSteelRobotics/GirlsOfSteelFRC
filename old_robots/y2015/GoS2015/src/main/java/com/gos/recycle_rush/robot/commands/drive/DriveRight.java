package com.gos.recycle_rush.robot.commands.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/*
 *
 */
public class DriveRight extends Command {
    private final Joystick m_joystick;
    private final Chassis m_chassis;

    public DriveRight(Joystick joystick, Chassis chassis) {
        m_joystick = joystick;
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.driveRight(m_joystick);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }



}
