package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OnlyDockAndEngageCommandGroup extends SequentialCommandGroup {

    public OnlyDockAndEngageCommandGroup(ChassisSubsystemInterface chassis, String path) {

        Command fullAutoDockAndEngage = chassis.createFollowPathCommand(path, false, Constants.DEFAULT_PATH_CONSTRAINTS);

        addCommands(fullAutoDockAndEngage);
        addCommands(chassis.createAutoEngageCommand());
    }
}
