
package com.gos.crescendo2024.auton.modes;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

public class OneNoteJustShoot extends GosAutoMode {

    public OneNoteJustShoot(CombinedCommands combinedCommands) {
        super(
            StartPosition.CURRENT_LOCATION,
            List.of(),

            Commands.sequence(
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
