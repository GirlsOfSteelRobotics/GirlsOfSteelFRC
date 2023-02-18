package com.gos.chargedup.commands.testing;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;
import java.util.List;

public class TestLineCommandGroup extends SequentialCommandGroup {

    public static final PathPlannerTrajectory TEST_LINE = PathPlanner.loadPath("TestLine", Constants.DEFAULT_PATH_CONSTRAINTS);

    public TestLineCommandGroup(ChassisSubsystem chassis) {

        List<PathPlannerTrajectory> testLine = PathPlanner.loadPathGroup("TestLine", Constants.DEFAULT_PATH_CONSTRAINTS);
        Command testLineCommand = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(testLine);
        addCommands(testLineCommand);

        setName("TestTrajectoryStraightLine");
    }
}
