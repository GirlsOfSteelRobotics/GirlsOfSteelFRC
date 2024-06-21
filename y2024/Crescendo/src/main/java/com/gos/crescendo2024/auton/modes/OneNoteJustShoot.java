
package com.gos.crescendo2024.auton.modes;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OneNoteJustShoot extends SequentialCommandGroup {
    public OneNoteJustShoot() {
        super(NamedCommands.getCommand("AimAndShootIntoSpeaker"));
    }
}

