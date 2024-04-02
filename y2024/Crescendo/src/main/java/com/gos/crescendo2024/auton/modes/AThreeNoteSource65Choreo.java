
package com.gos.crescendo2024.auton.modes;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AThreeNoteSource65Choreo {
    
    public static Command create() {
        return 
            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    Commands.sequence(
                        AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("AssholeAuto65.1")),
                        AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("AssholeAuto65.2")),
                        AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("AssholeAuto65.3"))
                    ),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("AssholeAuto65.4")),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            );
    }
}

