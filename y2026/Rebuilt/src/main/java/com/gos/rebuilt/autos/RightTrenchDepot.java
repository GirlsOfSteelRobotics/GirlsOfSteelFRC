package com.gos.rebuilt.autos;

import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class RightTrenchDepot extends GosAuto {
    public RightTrenchDepot(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.RIGHT, List.of(AutoActions.CROSSTHETRENCH, AutoActions.DEPOT, AutoActions.SHOOOT));

        addCommands(new RightTrenchCenter(chassisSubsystem,combinedCommand));
        addCommands(followChoreoPath("RightShootToDepot.0"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("RightShootToDepot.1"));
        addCommands(combinedCommand.shootBall());
    }
}
