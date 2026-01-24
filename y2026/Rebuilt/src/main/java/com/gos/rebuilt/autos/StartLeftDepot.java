package com.gos.rebuilt.autos;

import com.gos.rebuilt.ChoreoUtils;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartLeftDepot extends SequentialCommandGroup {
    public StartLeftDepot(ChassisSubsystem chassisSubsystem) {
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartLeftDepot").getPose())));
        addCommands(followChoreoPath("StartLeftDepot.0"));
        addCommands(Commands.waitSeconds(2));
        addCommands(followChoreoPath("StartLeftDepot.1"));
//
//        addCommands(followChoreoPath("Path.1"));
//        // intake
//
//        addCommands(followChoreoPath("Path.2"));
    }
}
