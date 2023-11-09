package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Chassis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveByJoystick extends Command {

    private final Chassis m_chassis;
    private final Joystick m_drivingPad;

    public DriveByJoystick(Chassis chassis, Joystick drivingPad) {
        m_chassis = chassis;
        m_drivingPad = drivingPad;
        addRequirements(m_chassis);
    }


    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        // 4 is the axis number right x on the gamepad
        m_chassis.driveByJoystick(-m_drivingPad.getY(), m_drivingPad.getRawAxis(4));
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
