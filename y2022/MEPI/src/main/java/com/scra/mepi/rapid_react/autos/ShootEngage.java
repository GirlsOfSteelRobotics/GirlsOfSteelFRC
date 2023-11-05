package com.scra.mepi.rapid_react.autos;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.scra.mepi.rapid_react.Constants;
import com.scra.mepi.rapid_react.commands.ShooterPIDCommand;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;

import java.util.HashMap;

public class ShootEngage extends SequentialCommandGroup {
    public ShootEngage(DrivetrainSubsystem drivetrain, ShooterSubsytem shooter, String path) {
        PathPlannerTrajectory shootAndEngage = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command shootEngage = drivetrain.ramseteAutoBuilderNoPoseReset(new HashMap<>()).fullAuto(shootAndEngage);

        // STEP 1: shoot a ball
        addCommands(new ShooterPIDCommand(shooter));

        // STEP 2: move back onto charging station
        addCommands(shootEngage);


    }


}
