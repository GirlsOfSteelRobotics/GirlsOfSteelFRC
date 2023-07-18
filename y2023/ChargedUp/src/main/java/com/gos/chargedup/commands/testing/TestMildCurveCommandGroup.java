package com.gos.chargedup.commands.testing;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class TestMildCurveCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TEST_MILD_CURVE = PathPlanner.loadPath("TestMildCurve", Constants.DEFAULT_PATH_CONSTRAINTS);

    public TestMildCurveCommandGroup(ChassisSubsystemInterface chassis) {

        List<PathPlannerTrajectory> testMildCurve = PathPlanner.loadPathGroup("TestMildCurve", Constants.DEFAULT_PATH_CONSTRAINTS);
        Command testMildCurveCommand = chassis.createPathPlannerBuilder(testMildCurve);
        addCommands(testMildCurveCommand);

        setName("TestTrajectoryMildCurve");
    }
}
