package com.gos.rebuilt.autos;

import com.gos.rebuilt.ChoreoUtils;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.rebuilt.commands.CombinedCommand;



import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartRightOutpost extends SequentialCommandGroup {
    public StartRightOutpost(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartRightOutpost").getPose())));
        addCommands(followChoreoPath("StartRightOutpost.0"));
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("StartRightOutpost.1"));
        addCommands(combinedCommand.shootBall());


    }
}
