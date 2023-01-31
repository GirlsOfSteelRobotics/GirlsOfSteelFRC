package com.gos.chargedup.autonomous;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OneNodeAndDockCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory ONE_NODE_AND_DOCK = PathPlanner.loadPath("OneNodeandDock", new PathConstraints(4, 3));

    public OneNodeAndDockCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(ONE_NODE_AND_DOCK, true)
        );
    }
}
