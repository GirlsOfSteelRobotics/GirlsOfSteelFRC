// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.HashMap;
import java.util.Map;

public final class Autos {

    public enum AutoModes {
        FOUR_NOTE("FourNoteSpeaker"),
        TWO_NOTE_LEFT_3("TwoNoteSpeakerLeft3"),
        TWO_NOTE_LEFT_4("TwoNoteSpeakerLeft4"),
        TWO_NOTE_MIDDLE_5("TwoNoteSpeakerMiddle5"),
        TWO_NOTE_MIDDLE_6("TwoNoteSpeakerMiddle6"),
        TWO_NOTE_RIGHT_6("TwoNoteSpeakerRight6"),
        TWO_NOTE_RIGHT_7("TwoNoteSpeakerRight7"),
        LEAVE_WING("LeaveWing"),
        PRELOAD_AND_LEAVE_WING("OneNoteSpeakerAndLeaveWing"),
        PRELOAD_AND_CENTER_LINE("TwoNotesSpeaker");


        public final String m_modeName;

        AutoModes(String modeName) {
            m_modeName = modeName;
        }
    }

    private static final AutoModes DEFAULT_MODE = AutoModes.PRELOAD_AND_CENTER_LINE;

    private final SendableChooser<AutoModes> m_autonChooser;

    private final Map<AutoModes, Command> m_modes;

    public Autos() {
        m_autonChooser = new SendableChooser<>();
        m_modes = new HashMap<>();

        for (AutoModes mode : AutoModes.values()) {
            if (mode == DEFAULT_MODE) {
                m_autonChooser.setDefaultOption(mode.m_modeName, mode);
            } else {
                m_autonChooser.addOption(mode.m_modeName, mode);
            }
            m_modes.put(mode, AutoBuilder.buildAuto(mode.m_modeName));
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
