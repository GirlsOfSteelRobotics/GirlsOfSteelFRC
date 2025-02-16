// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape.commands;

import com.gos.reefscape.auto.modes.GosAuto;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.auto.modes.MultiPieceAlgae;
import com.gos.reefscape.auto.modes.MultiPieceCoral;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.List;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public final class Autos {

    private static final String DEFAULT_MODE = "RIGHT.EL4.BL4.CL4";

    private final SendableChooser<GosAuto> m_autoModes;

    public Autos(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands) {
        m_autoModes = new SendableChooser<>();
        SmartDashboard.putData("Auto Modes", m_autoModes);

        ///////////////////////////////
        /// Right Side
        ///////////////////////////////
        createMultiCoralAuto(swerveDrive, combinedCommands, PIECoral.L4, StartingPositions.RIGHT, List.of(
            CoralPositions.G,
            CoralPositions.F,
            CoralPositions.E,
            CoralPositions.D,
            CoralPositions.C,
            CoralPositions.B)
        );
        createMultiCoralAuto(swerveDrive, combinedCommands, PIECoral.L4, StartingPositions.RIGHT, List.of(
            CoralPositions.E,
            CoralPositions.B,
            CoralPositions.C));

        ///////////////////////////////
        /// Center Side
        ///////////////////////////////
        createMultiCoralAuto(swerveDrive, combinedCommands, PIECoral.L1, StartingPositions.CENTER, List.of(CoralPositions.G));

        createMultiAlgaeAuto(swerveDrive, combinedCommands, PIECoral.L1, CoralPositions.H, StartingPositions.CENTER, List.of(
            AlgaePositions.GH,
            AlgaePositions.EF,
            AlgaePositions.IJ));

        ///////////////////////////////
        /// Left Side
        ///////////////////////////////
        createMultiCoralAuto(swerveDrive, combinedCommands, PIECoral.L4, StartingPositions.LEFT, List.of(
            CoralPositions.H,
            CoralPositions.I,
            CoralPositions.J,
            CoralPositions.K,
            CoralPositions.L,
            CoralPositions.A));
    }

    private void createMultiCoralAuto(ChassisSubsystem chassis, CombinedCommands combinedCommands, PIECoral height, StartingPositions starting, List<CoralPositions> positions) {
        GosAuto example = new MultiPieceCoral(chassis, combinedCommands, height, starting, positions);
        addAutoMode(example);
    }

    private void createMultiAlgaeAuto(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands, PIECoral height, CoralPositions coralPosition, StartingPositions start, List<AlgaePositions> algaelist) {
        GosAuto multiPieceAlgae = new MultiPieceAlgae(swerveDrive, combinedCommands, height, coralPosition, start, algaelist);
        addAutoMode(multiPieceAlgae);
    }

    private void addAutoMode(GosAuto auto) {
        String name = auto.getName();
        System.out.println("Adding auto: '" + name + "'");
        if (DEFAULT_MODE.equals(name)) {
            m_autoModes.setDefaultOption(name, auto);
        } else {
            m_autoModes.addOption(name, auto);
        }
    }

    public GosAuto getSelectedAuto() {
        return m_autoModes.getSelected();
    }
}
