
package com.gos.crescendo2024.auton.modes.fournote;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FourNoteAmpSide034Choreo {
    
    public static Command create() {
        return 
            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),
                Commands.deadline(
                    AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("FourNoteAmpSide034.1")),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("FourNoteAmpSide034.2")),
                    NamedCommands.getCommand("IntakePiece")
                ),
                AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("FourNoteAmpSide034.3")),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("FourNoteAmpSide034.4")),
                    NamedCommands.getCommand("IntakePiece")
                ),
                AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("FourNoteAmpSide034.5")),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            );
    }
}

