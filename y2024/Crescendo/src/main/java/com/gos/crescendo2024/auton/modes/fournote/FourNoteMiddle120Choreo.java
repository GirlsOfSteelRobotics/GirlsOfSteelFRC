
package com.gos.crescendo2024.auton.modes.fournote;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class FourNoteMiddle120Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "FourNoteMiddle120";
    
    public FourNoteMiddle120Choreo() {
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
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".3"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
    );
    }
}

