package com.gos.rebuilt.autos;

import com.gos.rebuilt.ChoreoUtils;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartMiddleShootMiddle extends SequentialCommandGroup {
    public StartMiddleShootMiddle(ChassisSubsystem chassisSubsystem) {
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartMiddleShootMiddle").getPose())));
        addCommands(followChoreoPath("StartMiddleShootMiddle"));
    }
}
