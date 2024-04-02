
package com.gos.crescendo2024.auton.modes.twonote;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoNoteSourceSide7Choreo {
    
    public static Command create() {
        return 
            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),
                Commands.deadline(
                    AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("TwoNotesSourceSide7.1")),
                    NamedCommands.getCommand("IntakePiece")
                ),
                Commands.deadline(
                    AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("TwoNotesSourceSide7.2")),
                    NamedCommands.getCommand("MoveArmToSpeakerAngle"),
                    NamedCommands.getCommand("ShooterDefaultRpm")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            );
    }
}

