package com.gos.chargedup.autonomous;

import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OnlyLeaveCommunityCommandGroup extends SequentialCommandGroup {


    public OnlyLeaveCommunityCommandGroup(ChassisSubsystemInterface chassis, String path) {
        Command driveAutoOnlyLeave = chassis.createFollowPathCommand(path);
        addCommands(driveAutoOnlyLeave);

    }
}
