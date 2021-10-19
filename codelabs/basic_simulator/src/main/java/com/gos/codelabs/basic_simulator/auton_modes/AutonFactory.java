package com.gos.codelabs.basic_simulator.auton_modes;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.codelabs.basic_simulator.RobotContainer;

public class AutonFactory {

    private final SendableChooser<Command> m_sendableChooser;

    public AutonFactory(RobotContainer robotContainer) {
        m_sendableChooser = new SendableChooser<>();

        // Alias for readability
        ChassisSubsystem chassis = robotContainer.getChassis();
        ElevatorSubsystem lift = robotContainer.getElevator();
        PunchSubsystem punch = robotContainer.getPunch();

        // Add all of the "real", full autonomous modes
    }

    public Command getAutonMode() {
        return m_sendableChooser.getSelected();
    }
}
