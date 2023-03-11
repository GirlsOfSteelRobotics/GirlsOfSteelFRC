package com.gos.chargedup.autonomous;


import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;
import java.util.List;

public class EngageAndLeaveCommunityCommandGroup extends SequentialCommandGroup {
    public EngageAndLeaveCommunityCommandGroup(ChassisSubsystem chassis) {
        List<PathPlannerTrajectory> engageAndLeaveCommunity = PathPlanner.loadPathGroup("DockandEngage_Community", Constants.DEFAULT_PATH_CONSTRAINTS);
        Command engageAndLeaveCommunityCommand = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(engageAndLeaveCommunity);

        addCommands(engageAndLeaveCommunityCommand);
        addCommands(chassis.createAutoEngageCommand());
    }
}