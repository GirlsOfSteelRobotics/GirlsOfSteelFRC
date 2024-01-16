// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public final class Autos {
    private final SendableChooser<Command> m_autonChooser;

    public enum AutoMode {
        DRIVE_TO_POINT_TEST
    }

    public Autos() {
        m_autonChooser = new SendableChooser<>();

    }

    public Command getSelectedAutonomous() {
        return m_autonChooser.getSelected();
    }
}
