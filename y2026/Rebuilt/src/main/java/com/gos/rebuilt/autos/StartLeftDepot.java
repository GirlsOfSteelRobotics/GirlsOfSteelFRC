package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartLeftDepot extends SequentialCommandGroup {


    public StartLeftDepot(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartLeftDepot").getPose())));
        addCommands(followChoreoPath("StartLeftDepot.0"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("StartLeftDepot.1"));
        addCommands(combinedCommand.shootBall());
    }
}
