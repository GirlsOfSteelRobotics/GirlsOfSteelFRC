// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.gos.crescendo2024.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class Autos {
    public enum StartPosition {
        STARTING_LOCATION_AMP_SIDE,
        STARTING_LOCATION_MIDDLE,
        STARTING_LOCATION_SOURCE_SIDE,
        CURRENT_LOCATION
    }

    public enum AutoModes {
        //four note auto
        FOUR_NOTE_012("FourNoteAmpSide012Choreo", StartPosition.STARTING_LOCATION_AMP_SIDE, List.of(0, 1, 2)),
        FOUR_NOTE_034("FourNoteAmpSide034Choreo", StartPosition.STARTING_LOCATION_AMP_SIDE, List.of(0, 3, 4)),
        FOUR_NOTE_045("FourNoteAmpSide045Choreo", StartPosition.STARTING_LOCATION_AMP_SIDE, List.of(0, 4, 5)),

        FOUR_NOTE_MIDDLE_012("FourNoteMiddle012Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(0, 1, 2)),
        // Three Note.THREE_NOTE_12("ThreeNoteMiddle12Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(1, 2)),
        THREE_NOTE_76("ThreeNotesSourceSide76Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(7, 6)),
        THREE_NOTE_65("ThreeNotesSourceSide65Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(6, 5)),
        //two note autos - Amp Side
        //two note autos - middle
        TWO_NOTE_MIDDLE_1("TwoNoteMiddle1Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(1)),
        TWO_NOTE_MIDDLE_2("TwoNoteMiddle2Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(2)),
        //two note autos - Source Side
        TWO_NOTE_RIGHT_6("TwoNoteSourceSide6Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(6)),
        TWO_NOTE_RIGHT_7("TwoNoteSourceSide7Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(7)),
        //just shoot preload
        ONE_NOTE_JUST_SHOOT("OneNoteJustShoot", StartPosition.CURRENT_LOCATION, List.of()),
        // Preload and drive
        ONE_NOTE_AND_LEAVE_WING("OneNoteSourceSideAndLeaveWingChoreo", StartPosition.CURRENT_LOCATION, List.of()),
        // No shooting
        NO_NOTE_LEAVE_WING("NoNoteLeaveWingChoreo", StartPosition.CURRENT_LOCATION, List.of());



        public final String m_modeName;
        public final StartPosition m_location;
        public final List<Integer> m_notes;

        AutoModes(String modeName, StartPosition location, List<Integer> notes) {
            m_modeName = modeName;
            m_location = location;
            m_notes = notes;
        }
    }

    private static final AutoModes DEFAULT_MODE = AutoModes.ONE_NOTE_JUST_SHOOT;

    private final SendableChooser<AutoModes> m_autonChooser;

    private final Map<AutoModes, Command> m_modes;

    private static final GosDoubleProperty AUTON_TIMEOUT = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "autoTimeoutSeconds", 0);


    public Autos() {
        m_autonChooser = new SendableChooser<>();
        m_modes = new HashMap<>();

        for (AutoModes mode : AutoModes.values()) {
            if (mode == DEFAULT_MODE) {
                m_autonChooser.setDefaultOption(mode.m_modeName, mode);
            } else {
                m_autonChooser.addOption(mode.m_modeName, mode);
            }
            m_modes.put(mode, new DeferredCommand(() -> new WaitCommand(AUTON_TIMEOUT.getValue()), new HashSet<>()).andThen(AutoBuilder.buildAuto(mode.m_modeName)));
        }

        SmartDashboard.putData("Auto Chooser", m_autonChooser);
    }

    public Command getSelectedAutonomous() {
        AutoModes mode = m_autonChooser.getSelected();
        return m_modes.get(mode);
    }

    public AutoModes autoModeLightSignal() {
        return m_autonChooser.getSelected();
    }
}
