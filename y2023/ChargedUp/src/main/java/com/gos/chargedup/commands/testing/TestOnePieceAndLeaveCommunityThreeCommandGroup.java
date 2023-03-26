package com.gos.chargedup.commands.testing;


import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;
import java.util.List;

public class TestOnePieceAndLeaveCommunityThreeCommandGroup extends SequentialCommandGroup {
    public TestOnePieceAndLeaveCommunityThreeCommandGroup(ChassisSubsystem chassis) {
        List<PathPlannerTrajectory> onePieceLeaveCommunity3 = PathPlanner.loadPathGroup("OnePieceLeaveCommunity3", Constants.DEFAULT_PATH_CONSTRAINTS);
        Command testOnePieceLeaveCommunity3 = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(onePieceLeaveCommunity3);
        addCommands(testOnePieceLeaveCommunity3);

        setName("TestTrajectoryOnePieceLeaveCommunity3");
    }
}
