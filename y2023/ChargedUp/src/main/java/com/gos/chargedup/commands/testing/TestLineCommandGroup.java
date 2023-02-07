package com.gos.chargedup.commands.testing;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestLineCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TEST_LINE = PathPlanner.loadPath("TestLine", new PathConstraints(4, 3));

    public TestLineCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(TEST_LINE, true)
        );

        setName("TestTrajectoryStraightLine");
    }
}
