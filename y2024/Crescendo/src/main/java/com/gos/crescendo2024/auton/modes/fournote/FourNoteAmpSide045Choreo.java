
package com.gos.crescendo2024.auton.modes.fournote;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class FourNoteAmpSide045Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "FourNoteAmpSide045";
    
    public FourNoteAmpSide045Choreo() {
        super( 
            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".2"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                followChoreoPath(PATH_BASE + ".3"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".4"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".5")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
    );
    }
}

