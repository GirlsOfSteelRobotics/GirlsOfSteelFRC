package com.gos.chargedup.commands.testing;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestSCurveCommandGroup extends SequentialCommandGroup {
    public static final PathPlannerTrajectory TEST_S_CURVE = PathPlanner.loadPath("TestSCurve", new PathConstraints(4, 3));
    public TestSCurveCommandGroup(ChassisSubsystem chassis) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
            chassis.followTrajectoryCommand(TEST_S_CURVE, true)
        );
    }
}