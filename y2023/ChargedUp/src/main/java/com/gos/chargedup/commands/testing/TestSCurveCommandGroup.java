package com.gos.chargedup.commands.testing;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestSCurveCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TEST_S_CURVE = PathPlanner.loadPath("TestSCurve", Constants.DEFAULT_PATH_CONSTRAINTS);

    public TestSCurveCommandGroup(ChassisSubsystem chassis) {
        super(
            chassis.followTrajectoryCommand(TEST_S_CURVE, true)
        );
        setName("TestTrajectorySCurve");
    }
}
