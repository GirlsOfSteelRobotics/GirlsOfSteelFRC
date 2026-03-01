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

public class LeftTrenchDepot extends GosAuto {
    public LeftTrenchDepot(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.LEFT, List.of(AutoActions.CROSSTHETRENCH, AutoActions.DEPOT, AutoActions.SHOOOT));

        addCommands(new LeftTrenchCenter(chassisSubsystem,combinedCommand));
        addCommands(followChoreoPath("LeftShootToDepot.0"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("LeftShootToDepot.1"));
        addCommands(combinedCommand.shootBall());
    }
}
