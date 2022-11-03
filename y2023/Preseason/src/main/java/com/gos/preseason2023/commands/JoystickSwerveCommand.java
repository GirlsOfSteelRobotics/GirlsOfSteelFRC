package com.gos.preseason2023.commands;

import com.gos.preseason2023.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickSwerveCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_joystick;

    public JoystickSwerveCommand(ChassisSubsystem chassis, XboxController joystick) {
        m_chassis = chassis;
        m_joystick = joystick;
        addRequirements(m_chassis);
    }

    @Override
    public void execute() {
        m_chassis.setJoystickDrive(m_joystick.getLeftX(), m_joystick.getLeftY(), m_joystick.getRightX());



    }
}
