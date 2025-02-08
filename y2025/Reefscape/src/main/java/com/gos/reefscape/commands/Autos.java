// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape.commands;

import com.gos.reefscape.auto.modes.GosAuto;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIE;
import com.gos.reefscape.auto.modes.TwoPieceAlgae;
import com.gos.reefscape.auto.modes.MultiPieceCoral;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.List;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public final class Autos {

    private final SendableChooser<GosAuto> m_autoModes;

    public Autos(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands) {
        m_autoModes = new SendableChooser<>();
        SmartDashboard.putData("Auto Modes", m_autoModes);

        createMutliCoralAuto(PIE.L4, StartingPositions.RIGHT, List.of(CoralPositions.E, CoralPositions.B, CoralPositions.C), swerveDrive, combinedCommands);
        createMutliCoralAuto(PIE.L4, StartingPositions.LEFT, List.of(
            CoralPositions.H,
            CoralPositions.I,
            CoralPositions.J,
            CoralPositions.K,
            CoralPositions.L,
            CoralPositions.A), swerveDrive, combinedCommands);
        m_autoModes.setDefaultOption("Center.H.GH.EF", new TwoPieceAlgae(swerveDrive, combinedCommands, CoralPositions.H, StartingPositions.CENTER, AlgaePositions.GH, AlgaePositions.EF));
        // m_autoModes.addOption("Right.E.B", new TwoPieceCoral(swerveDrive, combinedCommands, PIE.L1, StartingPositions.RIGHT, List.of(CoralPositions.E, CoralPositions.B, CoralPositions.A)));
        //m_autoModes.addOption("Right.E.C", new TwoPieceCoral(swerveDrive, combinedCommands, PIE.L4, "Right", "E", "C"));
        //m_autoModes.addOption("Right.F.B", new TwoPieceCoral(swerveDrive, combinedCommands, PIE.L4, "Right", "F", "B"));
    }

    private void createMutliCoralAuto(PIE height, StartingPositions starting, List<CoralPositions> positions, ChassisSubsystem chassis, CombinedCommands combinedCommands) {
        GosAuto example = new MultiPieceCoral(chassis, combinedCommands, height, starting, positions);
        m_autoModes.addOption(example.getName(), example);

    }

    public GosAuto getSelectedAuto() {
        return m_autoModes.getSelected();
    }
}
