// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public final class Autos {
    private final SendableChooser<Command> m_autonChooser;

    public Autos() {
        m_autonChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", m_autonChooser);
    }

    public Command getSelectedAutonomous() {
        return m_autonChooser.getSelected();
    }
}
