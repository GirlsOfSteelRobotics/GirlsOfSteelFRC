package com.gos.codelabs.basic_simulator.auton_modes;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class AutonFactory {

    private final SendableChooser<Command> m_sendableChooser;

    public AutonFactory(ChassisSubsystem chassis, ElevatorSubsystem lift, PunchSubsystem punch) {
        m_sendableChooser = new SendableChooser<>();
        m_sendableChooser.setDefaultOption("Drive, Elevate, Punch", new DriveElevatePunchCommandGroup(chassis, lift, punch));
        SmartDashboard.putData("Auto Chooser", m_sendableChooser);
    }

    public Command getAutonMode() {
        return m_sendableChooser.getSelected();
    }
}
