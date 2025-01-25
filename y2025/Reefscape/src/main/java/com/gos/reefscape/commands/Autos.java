// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape.commands;

import com.gos.reefscape.auto.modes.right.Center_H_GH_EFCommandGroup;
import com.gos.reefscape.auto.modes.right.Left_J_LCommandGroup;
import com.gos.reefscape.auto.modes.right.Right_E_CCommandGroupCommandGroup;
import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public final class Autos {

    private final SendableChooser<Command> m_autoModes;

    public Autos(GOSSwerveDrive swerveDrive) {
        m_autoModes = new SendableChooser<>();
        SmartDashboard.putData("Auto Modes", m_autoModes);

        m_autoModes.addOption("Left.J.L);", new Left_J_LCommandGroup(swerveDrive));
        m_autoModes.addOption("Center.H.GH.EF", new Center_H_GH_EFCommandGroup(swerveDrive));
        m_autoModes.addOption("Right.E.C", new Right_E_CCommandGroupCommandGroup(swerveDrive));
    }

    public Command getSelectedAuto() {
        return m_autoModes.getSelected();
    }
}
