// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.gos.crescendo2024.auton.modes.asshole.AThreeNoteSource5Choreo;
import com.gos.crescendo2024.auton.modes.asshole.AThreeNoteSource65Choreo;
import com.gos.crescendo2024.auton.modes.asshole.DisruptionAuto;
import com.gos.crescendo2024.auton.modes.NoNoteLeaveWingChoreo;
import com.gos.crescendo2024.auton.modes.OneNoteJustShoot;
import com.gos.crescendo2024.auton.modes.source.OneNoteSourceSideAndLeaveWingChoreo;
import com.gos.crescendo2024.auton.modes.ozone.OzoneTwoNoteSource7;
import com.gos.crescendo2024.auton.modes.amp.FourNoteAmpSide012Choreo;
import com.gos.crescendo2024.auton.modes.amp.FourNoteAmpSide045Choreo;
import com.gos.crescendo2024.auton.modes.middle.FourNoteMiddle012Choreo;
import com.gos.crescendo2024.auton.modes.middle.FourNoteMiddle120Choreo;
import com.gos.crescendo2024.auton.modes.middle.FourNoteMiddle152Choreo;
import com.gos.crescendo2024.auton.modes.middle.FourNoteMiddle521Choreo;
import com.gos.crescendo2024.auton.modes.amp.ThreeNoteAmpSide03Choreo;
import com.gos.crescendo2024.auton.modes.middle.ThreeNoteMiddle12Choreo;
import com.gos.crescendo2024.auton.modes.middle.ThreeNoteMiddle52Choreo;
import com.gos.crescendo2024.auton.modes.source.ThreeNotesSourceSide65Choreo;
import com.gos.crescendo2024.auton.modes.source.ThreeNotesSourceSide76Choreo;
import com.gos.crescendo2024.auton.modes.middle.TwoNoteMiddle1Choreo;
import com.gos.crescendo2024.auton.modes.middle.TwoNoteMiddle2Choreo;
import com.gos.crescendo2024.auton.modes.middle.TwoNoteMiddleAnnaSpecial;
import com.gos.crescendo2024.auton.modes.source.TwoNoteSourceSide6Choreo;
import com.gos.crescendo2024.auton.modes.source.TwoNoteSourceSide7Choreo;
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
        addAutoMode(new FourNoteAmpSide012Choreo(combinedCommands));
        addAutoMode(new FourNoteAmpSide045Choreo(combinedCommands));

        // Middle
        addAutoMode(new FourNoteMiddle012Choreo(combinedCommands));
        addDefaultAutoMode(new FourNoteMiddle120Choreo(combinedCommands));
        addAutoMode(new FourNoteMiddle152Choreo(combinedCommands));
        addAutoMode(new FourNoteMiddle521Choreo(combinedCommands));

        //-------------------------
        // Three Notes
        //-------------------------

        // Amp Side
        addAutoMode(new ThreeNoteAmpSide03Choreo(combinedCommands));

        // Middle
        addAutoMode(new ThreeNoteMiddle12Choreo(combinedCommands));
        addAutoMode(new ThreeNoteMiddle52Choreo(combinedCommands));

        // Source
        addAutoMode(new ThreeNotesSourceSide65Choreo(combinedCommands));
        addAutoMode(new ThreeNotesSourceSide76Choreo(combinedCommands));

        //-------------------------
        // Two Note
        //-------------------------

        // Middle
        addAutoMode(new TwoNoteMiddle1Choreo(combinedCommands));
        addAutoMode(new TwoNoteMiddle2Choreo(combinedCommands));
        addAutoMode(new TwoNoteMiddleAnnaSpecial(combinedCommands));

        // Source
        addAutoMode(new TwoNoteSourceSide6Choreo(combinedCommands));
        addAutoMode(new TwoNoteSourceSide7Choreo(combinedCommands));

        // Ozone
        addAutoMode(new OzoneTwoNoteSource7(combinedCommands));


        //-------------------------
        // One Note
        //-------------------------
        addAutoMode(new OneNoteJustShoot(combinedCommands));
        addAutoMode(new OneNoteSourceSideAndLeaveWingChoreo(combinedCommands));

        //-------------------------
        // Asshole
        //-------------------------
        addAutoMode(new AThreeNoteSource5Choreo(combinedCommands));
        addAutoMode(new AThreeNoteSource65Choreo(combinedCommands));
        addAutoMode(new DisruptionAuto(combinedCommands));

        //-------------------------
        // Just Drive
        //-------------------------
        addAutoMode(new NoNoteLeaveWingChoreo(combinedCommands));


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
