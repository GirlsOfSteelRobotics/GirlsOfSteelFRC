package com.gos.rebuilt.autos;

import com.gos.rebuilt.ChoreoUtils;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartRightOutpost extends SequentialCommandGroup {
    public StartRightOutpost(ChassisSubsystem chassisSubsystem) {
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartRightOutpost").getPose())));
        addCommands(followChoreoPath("StartRightOutpost.0"));
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("StartRightOutpost.1"));
//
//        addCommands(followChoreoPath("Path.1"));
//        // intake
//
//        addCommands(followChoreoPath("Path.2"));
    }
}
