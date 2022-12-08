package com.gos.preseason2023.commands;

import com.gos.preseason2023.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickSwerveCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_joystick;

    private static final double m_maxSpeedSwerve = Units.feetToMeters(10); // intended: meters per second

    public JoystickSwerveCommand(ChassisSubsystem chassis, XboxController joystick) {
        m_chassis = chassis;
        m_joystick = joystick;
        addRequirements(m_chassis);
    }

    @Override
    public void execute() {
        double robotXSpeed = -m_joystick.getLeftY();
        double robotYSpeed = -m_joystick.getLeftX();
        double robotRotation = -m_joystick.getRightX();
        m_chassis.setRobotOrientedDrive(robotXSpeed * m_maxSpeedSwerve, robotYSpeed * m_maxSpeedSwerve, robotRotation);



    }
}
