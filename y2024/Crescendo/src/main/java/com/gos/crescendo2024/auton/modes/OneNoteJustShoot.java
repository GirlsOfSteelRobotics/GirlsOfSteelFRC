
package com.gos.crescendo2024.auton.modes;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class OneNoteJustShoot extends SequentialCommandGroup {

    private static final String PATH_BASE = "None";
    
    public OneNoteJustShoot() {
        super( 
            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
    );
    }
}

