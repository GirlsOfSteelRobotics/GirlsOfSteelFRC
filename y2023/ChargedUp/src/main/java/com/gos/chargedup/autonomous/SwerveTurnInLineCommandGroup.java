package com.gos.chargedup.autonomous;


import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class SwerveTurnInLineCommandGroup extends SequentialCommandGroup {
    public SwerveTurnInLineCommandGroup(ChassisSubsystemInterface chassis) {
        List<PathPlannerTrajectory> trajectory = PathPlanner.loadPathGroup("SwerveTurnInPath", Constants.DEFAULT_PATH_CONSTRAINTS);
        Command testLineCommand = chassis.createFollowPathCommand(trajectory);
        addCommands(testLineCommand);

    }
}
