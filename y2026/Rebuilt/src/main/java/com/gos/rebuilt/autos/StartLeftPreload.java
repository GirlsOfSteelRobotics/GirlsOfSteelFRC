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

public class StartLeftPreload extends GosAuto {
    public StartLeftPreload(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.LEFT, GainBalls.Preload, AutoActions.Shoot);
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartLeftPreload").getPose())));
        addCommands(followChoreoPath("StartLeftPreload"));
        addCommands(combinedCommand.shootBall());


    }
}
