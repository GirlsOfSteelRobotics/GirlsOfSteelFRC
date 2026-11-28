package com.gos.codelabs.basic_simulator.auton_modes;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import org.wpilib.command2.Command;
import org.wpilib.tunable.Selectable;
import org.wpilib.tunable.Tunables;

public class AutonFactory {

    private final Selectable<Command> m_sendableChooser;

    public AutonFactory(ChassisSubsystem chassis, ElevatorSubsystem lift, PunchSubsystem punch) {
        m_sendableChooser = new Selectable<>();
        m_sendableChooser.addDefault("Drive, Elevate, Punch", new DriveElevatePunchCommandGroup(chassis, lift, punch));
        Tunables.publish("Auto Chooser", m_sendableChooser);
    }

    public Command getAutonMode() {
        return m_sendableChooser.getSelected();
    }
}
