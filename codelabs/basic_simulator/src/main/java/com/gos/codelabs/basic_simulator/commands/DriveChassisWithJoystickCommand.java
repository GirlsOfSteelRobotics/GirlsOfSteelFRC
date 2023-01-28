package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveChassisWithJoystickCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_driverJoystick;

    public DriveChassisWithJoystickCommand(ChassisSubsystem chassis, XboxController driverJoystick) {
        m_chassis = chassis;
        m_driverJoystick = driverJoystick;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
