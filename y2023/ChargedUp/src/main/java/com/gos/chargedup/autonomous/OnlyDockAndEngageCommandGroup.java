package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class OnlyDockAndEngageCommandGroup extends SequentialCommandGroup {

    public OnlyDockAndEngageCommandGroup(ChassisSubsystem chassis, String path) {

        List<PathPlannerTrajectory> trajectory = PathPlanner.loadPathGroup(path, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command fullAutoDockAndEngage = chassis.createPathPlannerBuilder(trajectory);

        addCommands(fullAutoDockAndEngage);
        addCommands(chassis.createAutoEngageCommand());
    }
}
