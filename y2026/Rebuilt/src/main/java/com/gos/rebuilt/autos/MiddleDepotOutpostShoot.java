package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class MiddleDepotOutpostShoot extends GosAuto{

    public MiddleDepotOutpostShoot(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.CENTER, List.of(AutoActions.DEPOT, AutoActions.OUTPOST, AutoActions.SHOOOT));

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("MiddleDepotShoot").getPose())));
        addCommands(followChoreoPath("MiddleDepotShoot.0"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("MiddleDepotShoot.1"));
        addCommands(combinedCommand.shootBall().withTimeout(5));
        addCommands(followChoreoPath("MiddleShootOutpostShoot.0"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(5));
        addCommands(followChoreoPath("MiddleShootOutpostShoot.1"));
        addCommands(combinedCommand.shootBall());
    }
}
