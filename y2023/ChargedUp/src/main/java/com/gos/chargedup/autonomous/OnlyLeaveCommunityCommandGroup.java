package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OnlyLeaveCommunityCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory ONLY_LEAVE_COMMUNITY = PathPlanner.loadPath("OnlyLeaveCommunity", Constants.DEFAULT_PATH_CONSTRAINTS);

    public OnlyLeaveCommunityCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(ONLY_LEAVE_COMMUNITY, true)
        );
    }
}
