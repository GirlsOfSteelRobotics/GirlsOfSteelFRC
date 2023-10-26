package com.gos.chargedup.autonomous;


import com.gos.chargedup.Constants;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class SwerveStrafeCommandGroup extends SequentialCommandGroup {
    public SwerveStrafeCommandGroup(ChassisSubsystemInterface chassis) {
        List<PathPlannerTrajectory> trajectory = PathPlanner.loadPathGroup("Swerve Strafe Path", Constants.DEFAULT_PATH_CONSTRAINTS);
        Command testLineCommand = chassis.createPathPlannerBuilder(trajectory);
        addCommands(testLineCommand);
    }
}
