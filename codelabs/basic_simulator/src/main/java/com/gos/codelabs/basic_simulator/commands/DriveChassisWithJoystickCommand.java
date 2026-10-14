package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class DriveChassisWithJoystickCommand extends Command {
    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_driverJoystick;

    public DriveChassisWithJoystickCommand(ChassisSubsystem chassis, CommandXboxController driverJoystick) {
        m_chassis = chassis;
        m_driverJoystick = driverJoystick;


        addRequirements(chassis);
    }

    @Override
    public void execute() {
        double leftY = m_driverJoystick.getLeftY();
        double rightX = m_driverJoystick.getRightX();
        double rightY = m_driverJoystick.getRightY();
        m_chassis.arcadeDrive(-leftY, -rightX);


    }
}
