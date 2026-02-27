package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class RightTrenchCenter extends SequentialCommandGroup {
    public RightTrenchCenter(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("RightTrenchCenter").getPose())));
        addCommands(new ParallelDeadlineGroup(followChoreoPath("RightTrenchCenter"), combinedCommand.intake()));
        addCommands(chassisSubsystem.createStop());
        addCommands(combinedCommand.shootBall());
    }
}
