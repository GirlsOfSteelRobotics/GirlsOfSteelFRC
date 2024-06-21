// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.gos.crescendo2024.auton.modes.AThreeNoteSource5Choreo;
import com.gos.crescendo2024.auton.modes.AThreeNoteSource65Choreo;
import com.gos.crescendo2024.auton.modes.fournote.FourNoteAmpSide012Choreo;
import com.gos.crescendo2024.auton.modes.fournote.FourNoteAmpSide045Choreo;
import com.gos.crescendo2024.auton.modes.fournote.FourNoteMiddle012Choreo;
import com.gos.crescendo2024.auton.modes.fournote.FourNoteMiddle120Choreo;
import com.gos.crescendo2024.auton.modes.fournote.FourNoteMiddle152Choreo;
import com.gos.crescendo2024.auton.modes.fournote.FourNoteMiddle521Choreo;
import com.gos.crescendo2024.auton.modes.threenote.ThreeNoteAmpSide03Choreo;
import com.gos.crescendo2024.auton.modes.threenote.ThreeNoteMiddle12Choreo;
import com.gos.crescendo2024.auton.modes.threenote.ThreeNoteMiddle52Choreo;
import com.gos.crescendo2024.auton.modes.threenote.ThreeNotesSourceSide65Choreo;
import com.gos.crescendo2024.auton.modes.threenote.ThreeNotesSourceSide76Choreo;
import com.gos.crescendo2024.auton.modes.twonote.TwoNoteMiddle1Choreo;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class Autos {
    private final SendableChooser<GosAutoMode> m_autonChooser;


    public Autos() {
        m_autonChooser = new SendableChooser<>();

        addAutoMode(new FourNoteAmpSide012Choreo());
        addDefaultAutoMode(new FourNoteMiddle120Choreo());
        addAutoMode(new FourNoteAmpSide045Choreo());
        addAutoMode(new FourNoteMiddle012Choreo());
        addAutoMode(new FourNoteMiddle521Choreo());
        addAutoMode(new FourNoteMiddle152Choreo());

        addAutoMode(new ThreeNoteAmpSide03Choreo());
        addAutoMode(new ThreeNoteMiddle12Choreo());
        addAutoMode(new ThreeNoteMiddle52Choreo());
        addAutoMode(new ThreeNotesSourceSide76Choreo());
        addAutoMode(new ThreeNotesSourceSide65Choreo());

        addAutoMode(new AThreeNoteSource65Choreo());
        addAutoMode(new AThreeNoteSource5Choreo());

        addAutoMode(new TwoNoteMiddle1Choreo());

        SmartDashboard.putData("Auto Chooser", m_autonChooser);
    }

    private void addDefaultAutoMode(GosAutoMode mode) {
        m_autonChooser.setDefaultOption(mode.getDisplayName(), mode);
    }

    private void addAutoMode(GosAutoMode mode) {
        m_autonChooser.addOption(mode.getDisplayName(), mode);
    }

    public GosAutoMode getSelectedCommand() {
        return m_autonChooser.getSelected();
    }
}
