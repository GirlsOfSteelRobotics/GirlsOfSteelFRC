
package com.gos.crescendo2024.auton.modes.twonote;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoNoteMiddle1Choreo {
    
    public static Command create() {
        return 
            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("TwoNoteMiddle1.1")),
                    NamedCommands.getCommand("IntakePiece")
                ),
                AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("TwoNoteMiddle1.2")),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            );
    }
}

