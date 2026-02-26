package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartRightPreloadOutpost extends SequentialCommandGroup {
    public StartRightPreloadOutpost(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartRightOutpost").getPose())));
        addCommands(followChoreoPath("StartRightPreloadOutpost.0"));
        addCommands(combinedCommand.shootBall().withTimeout(5));
        addCommands(followChoreoPath("StartRightPreloadOutpost.1"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(5));
        addCommands(followChoreoPath("StartRightPreloadOutpost.2"));
        addCommands(combinedCommand.shootBall());

    }
}
