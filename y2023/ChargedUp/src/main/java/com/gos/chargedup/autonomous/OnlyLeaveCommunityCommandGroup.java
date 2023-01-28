package com.gos.chargedup.autonomous;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OnlyLeaveCommunityCommandGroup extends SequentialCommandGroup {
    public static final PathPlannerTrajectory ONLY_LEAVE_COMMUNITY = PathPlanner.loadPath("OnlyLeaveCommunity", new PathConstraints(4, 3));
    public OnlyLeaveCommunityCommandGroup(ChassisSubsystem chassis) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
            chassis.followTrajectoryCommand(ONLY_LEAVE_COMMUNITY, true)
        );
    }
}