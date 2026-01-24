package com.gos.rebuilt.autos;

import com.gos.rebuilt.ChoreoUtils;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartLeftPreload extends SequentialCommandGroup {
    public StartLeftPreload(ChassisSubsystem chassisSubsystem) {
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartLeftPreload").getPose())));
        addCommands(followChoreoPath("StartLeftPreload"));

    }
}
