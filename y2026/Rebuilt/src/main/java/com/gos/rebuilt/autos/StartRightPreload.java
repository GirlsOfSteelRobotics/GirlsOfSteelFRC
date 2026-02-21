package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.GainBalls;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.rebuilt.commands.CombinedCommand;


import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartRightPreload extends GosAuto {
    public StartRightPreload(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.RIGHT, GainBalls.Preload, AutoActions.Shoot);
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartRightPreload").getPose())));
        addCommands(followChoreoPath("StartRightPreload"));
        addCommands(combinedCommand.shootBall());

    }
}
