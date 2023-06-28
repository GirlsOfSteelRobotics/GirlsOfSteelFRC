package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OnlyLeaveCommunityCommandGroup extends SequentialCommandGroup {


    public OnlyLeaveCommunityCommandGroup(ChassisSubsystemInterface chassis, String path) {

        PathPlannerTrajectory onlyLeaveCommunity = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command driveAutoOnlyLeave = chassis.createPathPlannerBuilder(onlyLeaveCommunity);
        addCommands(driveAutoOnlyLeave);

    }
}
