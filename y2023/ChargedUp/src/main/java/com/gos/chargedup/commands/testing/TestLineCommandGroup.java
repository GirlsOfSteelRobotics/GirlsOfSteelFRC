package com.gos.chargedup.commands.testing;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestLineCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TEST_LINE = PathPlanner.loadPath("TestLine", Constants.DEFAULT_PATH_CONSTRAINTS);

    public TestLineCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(TEST_LINE, true)
        );

        setName("TestTrajectoryStraightLine");
    }
}
