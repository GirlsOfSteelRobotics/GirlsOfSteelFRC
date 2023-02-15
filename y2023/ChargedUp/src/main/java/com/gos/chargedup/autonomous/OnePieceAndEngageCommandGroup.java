package com.gos.chargedup.autonomous;

import com.gos.chargedup.AutoEnumsWithScorePiecePivot;
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



    public OnePieceAndEngageCommandGroup(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, String path, AutoEnumsWithScorePiecePivot piece, GamePieceType gamePieceType) {

        PathPlannerTrajectory oneNodeAndEngage = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command driveAutoOnePieceEngage = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(oneNodeAndEngage);

        //score
        //addCommands(turret.commandTurretPID(180));
        addCommands(new ScorePieceCommandGroup(turret, arm, claw, piece, gamePieceType));

        //drive to docking station
        addCommands(driveAutoOnePieceEngage);

        //dock and engage
        addCommands(chassis.createAutoEngageCommand());
    }
}
