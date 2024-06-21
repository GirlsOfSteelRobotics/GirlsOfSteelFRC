
package com.gos.crescendo2024.auton.modes;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class AThreeNoteSource65Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "AssholeAuto65";
    
    public AThreeNoteSource65Choreo() {
        super( 
            Commands.sequence(
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("PrepSpeakerShot")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    Commands.sequence(
                        followChoreoPath(PATH_BASE + ".2"),
                        followChoreoPath(PATH_BASE + ".3")
                    ),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
    );
    }
}

