// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package autonomous;

import com.gos.chargedup.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;


public final class AutonomousFactory {
    private final SendableChooser<Command> m_autonomousModes;

    public AutonomousFactory(ExampleSubsystem subsystem) {
        m_autonomousModes = new SendableChooser<>();

        m_autonomousModes.addOption("Example", subsystem.exampleMethodCommand());

        SmartDashboard.putData(m_autonomousModes);
    }

    public Command getAutonomousCommand() {
        return m_autonomousModes.getSelected();
    }
}
