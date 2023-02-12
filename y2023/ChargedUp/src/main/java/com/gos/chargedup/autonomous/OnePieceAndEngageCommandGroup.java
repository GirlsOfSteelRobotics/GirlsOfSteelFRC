package com.gos.chargedup.autonomous;

import com.gos.chargedup.Constants;
import com.gos.chargedup.commands.AutoScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OnePieceAndEngageCommandGroup extends SequentialCommandGroup {



    public OnePieceAndEngageCommandGroup(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, String path) {

        PathPlannerTrajectory oneNodeAndEngage = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS);

        //score
        addCommands(new AutoScorePieceCommandGroup(turret, arm, claw));

        //drive to docking station
        addCommands(chassis.followTrajectoryCommand(oneNodeAndEngage, true));

        //dock and engage
        addCommands(chassis.createAutoEngageCommand());
    }
}
