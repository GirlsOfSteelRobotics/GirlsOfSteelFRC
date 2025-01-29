// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape.commands;

import com.gos.reefscape.PIE;
import com.gos.reefscape.auto.modes.center.Center_H_GH_EFCommandGroup;
import com.gos.reefscape.auto.modes.TwoPiece;
import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public final class Autos {

    private final SendableChooser<Command> m_autoModes;

    public Autos(GOSSwerveDrive swerveDrive, CombinedCommands combinedCommands) {
        m_autoModes = new SendableChooser<>();
        SmartDashboard.putData("Auto Modes", m_autoModes);

        m_autoModes.addOption("Left.J4.L4", new TwoPiece(swerveDrive, combinedCommands, PIE.L4, "Left", "J", "L"));
        m_autoModes.addOption("Center.H.GH.EF", new Center_H_GH_EFCommandGroup(swerveDrive, combinedCommands));
        m_autoModes.addOption("Right.E.C", new TwoPiece(swerveDrive, combinedCommands, PIE.L4, "Right", "E", "C"));
    }

    public Command getSelectedAuto() {
        return m_autoModes.getSelected();
    }
}
