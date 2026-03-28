package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class MiddleShootDepot extends GosAuto {

    public MiddleShootDepot(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.CENTER, List.of(AutoActions.DEPOT, AutoActions.SHOOOT));

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartMiddleShootMiddle").getPose())));
        addCommands(followChoreoPath("StartMiddleShootMiddle"));
        addCommands(combinedCommand.shootBall().withTimeout(5));
        addCommands(followChoreoPath("MiddleShootDepot.0"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("MiddleShootDepot.1"));
        addCommands(combinedCommand.shootBall().withTimeout(5));
    }
}
