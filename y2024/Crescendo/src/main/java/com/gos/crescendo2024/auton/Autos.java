// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.gos.crescendo2024.auton.modes.OneNoteJustShoot;
import com.gos.crescendo2024.auton.modes.amp.FourNoteAmpSide045Choreo;
import com.gos.crescendo2024.auton.modes.asshole.Asshole7TwoNote6;
import com.gos.crescendo2024.auton.modes.asshole.DisruptionAuto;
import com.gos.crescendo2024.auton.modes.middle.FourNoteMiddle120Choreo;
import com.gos.crescendo2024.auton.modes.middle.FourNoteMiddle152Choreo;
import com.gos.crescendo2024.auton.modes.middle.ThreeNoteMiddle52Choreo;
import com.gos.crescendo2024.auton.modes.source.OzoneTwoNoteSource7;
import com.gos.crescendo2024.auton.modes.source.ThreeNotesSourceSide76Choreo;
import com.gos.crescendo2024.commands.CombinedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class Autos {
    private final SendableChooser<GosAutoMode> m_autonChooser;


    public Autos(CombinedCommands combinedCommands) {
        m_autonChooser = new SendableChooser<>();

        //-------------------------
        // Four Notes
        //-------------------------

        // Amp Side
        addAutoMode(new FourNoteAmpSide045Choreo(combinedCommands));

        // Middle
        addDefaultAutoMode(new FourNoteMiddle120Choreo(combinedCommands));
        addAutoMode(new FourNoteMiddle152Choreo(combinedCommands));

        //-------------------------
        // Three Notes
        //-------------------------

        // Amp Side

        // Middle
        addAutoMode(new ThreeNoteMiddle52Choreo(combinedCommands));

        // Source
        addAutoMode(new ThreeNotesSourceSide76Choreo(combinedCommands));

        // Ozone
        addAutoMode(new OzoneTwoNoteSource7(combinedCommands));


        //-------------------------
        // One Note
        //-------------------------
        addAutoMode(new OneNoteJustShoot(combinedCommands));

        //-------------------------
        // Asshole
        //-------------------------
        addAutoMode(new Asshole7TwoNote6(combinedCommands));
        addAutoMode(new DisruptionAuto(combinedCommands));


        SmartDashboard.putData("Auto Chooser", m_autonChooser);
    }

    private void addDefaultAutoMode(GosAutoMode mode) {
        m_autonChooser.setDefaultOption(mode.getName(), mode);
    }

    private void addAutoMode(GosAutoMode mode) {
        m_autonChooser.addOption(mode.getName(), mode);
    }

    public GosAutoMode getSelectedCommand() {
        return m_autonChooser.getSelected();
    }
}
