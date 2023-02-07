package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OnlyDockAndEngageCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory ONLY_DOCK_AND_ENGAGE = PathPlanner.loadPath("OnlyDockandEngage", new PathConstraints(Constants.AUTO_VELOCITY, Constants.AUTO_ACCELERATION));

    public OnlyDockAndEngageCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(ONLY_DOCK_AND_ENGAGE, true)
        );
    }
}
