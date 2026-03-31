package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class MiddleRightBumpCenter extends GosAuto {
    public MiddleRightBumpCenter(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.CENTER, List.of(AutoActions.PRELOAD, AutoActions.SHOOOT));
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("MiddleRightBumpCenter").getPose())));
        addCommands(followChoreoPath("MiddleRightBumpCenter.0"));
        addCommands(combinedCommand.shootBall().withTimeout(5));

        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("MiddleRightBumpCenter.1").alongWith(combinedCommand.intake()));

    }
}
