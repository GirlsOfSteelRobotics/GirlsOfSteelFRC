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
import java.util.Map;

public final class Autos {
    public enum StartPosition {
        STARTING_LOCATION_TOP,
        STARTING_LOCATION_MIDDLE,
        STARTING_LOCATION_BACK,
        CURRENT_LOCATION
    }

    public enum AutoModes {
        //four note auto
        FOUR_NOTE("FourNoteSpeakerChoreo", StartPosition.STARTING_LOCATION_TOP),
        //two note autos - left
        //        TWO_NOTE_LEFT_0("TwoNoteSpeakerLeft0"),
        //        TWO_NOTE_LEFT_3("TwoNoteSpeakerLeft3"),
        //        TWO_NOTE_LEFT_4("TwoNoteSpeakerLeft4"),
        //two note autos - middle
        TWO_NOTE_MIDDLE_1("TwoNoteSpeakerMiddle1Choreo", StartPosition.STARTING_LOCATION_MIDDLE),
        TWO_NOTE_MIDDLE_2("TwoNoteSpeakerMiddle2Choreo", StartPosition.STARTING_LOCATION_MIDDLE),
        //        TWO_NOTE_MIDDLE_5("TwoNoteSpeakerMiddle5"),
        //        TWO_NOTE_MIDDLE_6("TwoNoteSpeakerMiddle6"),
        //two note autos - right
        TWO_NOTE_RIGHT_6("TwoNoteSpeakerRight6Choreo", StartPosition.STARTING_LOCATION_BACK),
        TWO_NOTE_RIGHT_7("TwoNotesSpeakerRight7Choreo", StartPosition.STARTING_LOCATION_BACK),
        //leave wing
        LEAVE_WING("LeaveWingChoreo", StartPosition.CURRENT_LOCATION),
        PRELOAD_AND_LEAVE_WING("OneNoteSpeakerAndLeaveWingChoreo", StartPosition.CURRENT_LOCATION),
        //just shoot preload
        PRELOAD_AND_SHOOT("JustShoot", StartPosition.CURRENT_LOCATION);



        public final String m_modeName;
        public final StartPosition m_location;

        AutoModes(String modeName, StartPosition location) {
            m_modeName = modeName;
            m_location = location;
        }
    }

    private static final AutoModes DEFAULT_MODE = AutoModes.PRELOAD_AND_SHOOT;

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
