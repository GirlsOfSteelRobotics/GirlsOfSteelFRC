package com.gos.chargedup.autonomous;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;

public class TwoPieceNoTurretCommandGroup extends SequentialCommandGroup {
    public TwoPieceNoTurretCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, String pathStart, String pathMiddle, String pathEnd) {
        PathPlannerTrajectory firstPiece = PathPlanner.loadPath(pathStart, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveToFirstPiece = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(firstPiece);

        PathPlannerTrajectory getSecondPiece = PathPlanner.loadPath(pathMiddle, Constants.DEFAULT_PATH_CONSTRAINTS, false);
        Command driveToGetSecondPiece = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(getSecondPiece);

        PathPlannerTrajectory scoreSecondPiece = PathPlanner.loadPath(pathEnd, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveToScoreSecondPiece = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(scoreSecondPiece);

        //score piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, GamePieceType.CONE));

        //first part
        addCommands(driveToFirstPiece.alongWith(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //turn 180
        addCommands(chassis.createTurnPID(0));

        //drive second part
        addCommands(driveToGetSecondPiece);

        //get piece
        addCommands(claw.createMoveClawIntakeInWithTimeoutCommand());

        //turn 180
        addCommands(chassis.createTurnPID(180));

        //third part
        addCommands(driveToScoreSecondPiece);

        //score piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, GamePieceType.CUBE));

    }
}
