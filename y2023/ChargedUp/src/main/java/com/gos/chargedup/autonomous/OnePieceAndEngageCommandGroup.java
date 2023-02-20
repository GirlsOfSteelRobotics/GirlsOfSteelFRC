package com.gos.chargedup.autonomous;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;

public class OnePieceAndEngageCommandGroup extends SequentialCommandGroup {



    public OnePieceAndEngageCommandGroup(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, String path, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {

        PathPlannerTrajectory oneNodeAndEngage = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command driveAutoOnePieceEngage = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(oneNodeAndEngage);

        //score
        addCommands(new ScorePieceCommandGroup(turret, arm, claw, pivotHeightType, gamePieceType));

        //drive to docking station
        addCommands((driveAutoOnePieceEngage)
            .alongWith(arm.commandPivotArmToAngleHold(ArmSubsystem.MIN_ANGLE_DEG)));

        //dock and engage
        addCommands(chassis.createAutoEngageCommand());
    }
}
