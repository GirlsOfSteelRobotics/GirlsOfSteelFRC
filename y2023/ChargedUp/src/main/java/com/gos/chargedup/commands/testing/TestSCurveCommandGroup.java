package com.gos.chargedup.commands.testing;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class TestSCurveCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TEST_S_CURVE = PathPlanner.loadPath("TestSCurve", Constants.DEFAULT_PATH_CONSTRAINTS);

    public TestSCurveCommandGroup(ChassisSubsystemInterface chassis) {

        List<PathPlannerTrajectory> testSCurve = PathPlanner.loadPathGroup("TestSCurve", Constants.DEFAULT_PATH_CONSTRAINTS);
        Command testSCurveCommand = chassis.createPathPlannerBuilder(testSCurve);
        addCommands(testSCurveCommand);

        setName("TestTrajectorySCurve");
    }
}
