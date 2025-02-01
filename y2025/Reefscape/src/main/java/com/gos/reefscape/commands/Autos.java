// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape.commands;

import com.gos.reefscape.PIE;
import com.gos.reefscape.auto.modes.TwoPieceAlgae;
import com.gos.reefscape.auto.modes.TwoPieceCoral;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public final class Autos {

    private final SendableChooser<Command> m_autoModes;

    public Autos(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands) {
        m_autoModes = new SendableChooser<>();
        SmartDashboard.putData("Auto Modes", m_autoModes);

        m_autoModes.addOption("Left.J4.L4", new TwoPieceCoral(swerveDrive, combinedCommands, PIE.L4, "Left", "J", "L"));
        m_autoModes.setDefaultOption("Center.H.GH.EF", new TwoPieceAlgae(swerveDrive, combinedCommands, "GH", "EF"));
        m_autoModes.addOption("Right.E.C", new TwoPieceCoral(swerveDrive, combinedCommands, PIE.L4, "Right", "E", "C"));
        m_autoModes.addOption("Right.F.B", new TwoPieceCoral(swerveDrive, combinedCommands, PIE.L4, "Right", "F", "B"));
    }

    public Command getSelectedAuto() {
        return m_autoModes.getSelected();
    }
}
