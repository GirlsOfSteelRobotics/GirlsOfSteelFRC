package com.gos.chargedup.commands.testing;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestMildCurveCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TEST_MILD_CURVE = PathPlanner.loadPath("TestMildCurve", new PathConstraints(4, 3));

    public TestMildCurveCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(TEST_MILD_CURVE, true)
        );
        setName("TestTrajectoryMildCurve");
    }
}
