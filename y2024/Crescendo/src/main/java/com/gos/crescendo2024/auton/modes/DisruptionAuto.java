
package com.gos.crescendo2024.auton.modes;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DisruptionAuto {
    
    public static Command create() {
        return 
            Commands.sequence(
                AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("DisruptionAuto.1"))
            );
    }
}

