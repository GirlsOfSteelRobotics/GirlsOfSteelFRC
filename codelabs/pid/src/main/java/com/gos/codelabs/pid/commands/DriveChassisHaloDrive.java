package com.gos.codelabs.pid.commands;

import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveChassisHaloDrive extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_driverJoystick;

    public DriveChassisHaloDrive(ChassisSubsystem chassis, XboxController driverJoystick) {
        m_chassis = chassis;
        m_driverJoystick = driverJoystick;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        m_chassis.setSpeedAndSteer(-m_driverJoystick.getLeftY(), m_driverJoystick.getRightX());
    }
}
