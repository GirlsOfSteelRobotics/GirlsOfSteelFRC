// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.reefscape.commands;

import com.gos.reefscape.auto.modes.GosAuto;
import com.gos.reefscape.auto.modes.IceCreamCoral;
import com.gos.reefscape.auto.modes.MultiPieceNet;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.auto.modes.MultiPieceProcessor;
import com.gos.reefscape.auto.modes.MultiPieceCoral;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.List;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public final class Autos {

    private static final String DEFAULT_MODE = "LEFT.multiCoral.IL3.LL3";

    private final SendableChooser<GosAuto> m_autoModes;

    public Autos(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands) {
        m_autoModes = new SendableChooser<>();
        SmartDashboard.putData("Auto Modes", m_autoModes);

        AutoModeCommandHelpers autoHelpers = new AutoModeCommandHelpers(swerveDrive, combinedCommands);

        ///////////////////////////////
        /// Right Side
        ///////////////////////////////
        // createMultiCoralAuto(swerveDrive, combinedCommands, PIECoral.L2, StartingPositions.RIGHT, List.of(
        //     CoralPositions.G,
        //     CoralPositions.F,
        //     CoralPositions.E,
        //     CoralPositions.D,
        //     CoralPositions.C,
        //     CoralPositions.B)
        // );
        createMultiCoralAuto(autoHelpers, combinedCommands, PIECoral.L2, StartingPositions.RIGHT, List.of(
            CoralPositions.E,
            CoralPositions.F));

        createMultiCoralAuto(autoHelpers, combinedCommands, PIECoral.L4, StartingPositions.RIGHT, List.of(
            CoralPositions.F,
            CoralPositions.D,
            CoralPositions.C));



        createCoralWithDessert(autoHelpers, combinedCommands, PIECoral.L4, StartingPositions.RIGHT, List.of(
            CoralPositions.F,
            CoralPositions.D));


        ///////////////////////////////
        /// Center Side
        ///////////////////////////////
        createMultiCoralAuto(autoHelpers, combinedCommands, PIECoral.L4, StartingPositions.CENTER, List.of(CoralPositions.H));

        createMultiProcessorAuto(autoHelpers, combinedCommands, PIECoral.L4, CoralPositions.H, StartingPositions.CENTER, List.of(
            AlgaePositions.GH,
            AlgaePositions.EF
        ));

        createScoreNetAuto(combinedCommands, autoHelpers, PIECoral.L4, CoralPositions.H, StartingPositions.CENTER, List.of(
            AlgaePositions.GH,
            AlgaePositions.IJ));

        ///////////////////////////////
        /// Left Side
        ///////////////////////////////


        createMultiCoralAuto(autoHelpers, combinedCommands, PIECoral.L2, StartingPositions.LEFT, List.of(
            CoralPositions.I,
            CoralPositions.J
        ));
        createMultiCoralAuto(autoHelpers, combinedCommands, PIECoral.L4, StartingPositions.LEFT, List.of(
            CoralPositions.I,
            CoralPositions.L,
            CoralPositions.K
        ));

        createCoralWithDessert(autoHelpers, combinedCommands, PIECoral.L4, StartingPositions.LEFT, List.of(
            CoralPositions.I,
            CoralPositions.L));
    }

    private void createMultiCoralAuto(AutoModeCommandHelpers helpers, CombinedCommands combinedCommands, PIECoral height, StartingPositions starting, List<CoralPositions> positions) {
        GosAuto example = new MultiPieceCoral(helpers, combinedCommands, height, starting, positions);
        addAutoMode(example);
    }

    private void createMultiProcessorAuto(AutoModeCommandHelpers helpers, CombinedCommands combinedCommands, PIECoral height, CoralPositions coralPosition, StartingPositions start, List<AlgaePositions> algaelist) {
        GosAuto multiPieceAlgae = new MultiPieceProcessor(helpers, combinedCommands, height, coralPosition, start, algaelist);
        addAutoMode(multiPieceAlgae);
    }

    private void createCoralWithDessert(AutoModeCommandHelpers helpers, CombinedCommands combinedCommands, PIECoral height, StartingPositions starting, List<CoralPositions> positions) {
        GosAuto iceCreamAuto = new IceCreamCoral(helpers, combinedCommands, height, starting, positions);
        addAutoMode(iceCreamAuto);
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

    private void createScoreNetAuto(CombinedCommands combinedCommands, AutoModeCommandHelpers helpers, PIECoral combo, CoralPositions coral, StartingPositions start, List<AlgaePositions> algaePositions) {
        GosAuto scoreIntoNetAuto = new MultiPieceNet(combinedCommands, helpers, combo, coral, start, algaePositions);
        addAutoMode(scoreIntoNetAuto);
    }

    public GosAuto getSelectedAuto() {
        return m_autoModes.getSelected();
    }
}
