package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class ShootLeftDepot extends GosAuto {


    public ShootLeftDepot(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.LEFT, List.of(AutoActions.DEPOT, AutoActions.SHOOOT));
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartLeftPreload").getPose())));
        addCommands(followChoreoPath("StartLeftPreload"));
        addCommands(combinedCommand.shootBall().withTimeout(5));
        addCommands(followChoreoPath("ShootLeftDepot.0"));
        addCommands(chassisSubsystem.createStop());
        addCommands(combinedCommand.intake().withTimeout(5));
        addCommands(followChoreoPath("ShootLeftDepot.1"));
        addCommands(combinedCommand.shootBall());
    }
}
