package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import org.wpilib.command2.Command;
import org.wpilib.command2.button.CommandNiDsXboxController;

public class DriveChassisWithJoystickCommand extends Command {
    private final ChassisSubsystem m_chassis;
    private final CommandNiDsXboxController m_driverJoystick;

    public DriveChassisWithJoystickCommand(ChassisSubsystem chassis, CommandNiDsXboxController driverJoystick) {
        m_chassis = chassis;
        m_driverJoystick = driverJoystick;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
