
package com.gos.crescendo2024.auton.modes;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

public class OneNoteJustShoot extends GosAutoMode {
    public OneNoteJustShoot() {
        super(
            "Three Note Amp Side - 03",
            GosAutoMode.StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
        );
    }
}

