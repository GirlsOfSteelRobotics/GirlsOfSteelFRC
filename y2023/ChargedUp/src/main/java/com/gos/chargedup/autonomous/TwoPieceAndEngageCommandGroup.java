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
import com.gos.chargedup.subsystems.TurretSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;
import java.util.List;


public class TwoPieceAndEngageCommandGroup extends SequentialCommandGroup {
    public TwoPieceAndEngageCommandGroup(ChassisSubsystem chassis, TurretSubsystem turret, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, String autoName, AutoPivotHeight pivotHeightType) {

        HashMap<String, Command> eventMap = new HashMap<>();
        eventMap.put("pickUpObject", claw.createMoveClawIntakeInCommand());
        eventMap.put("resetArmAndTurret", CombinedCommandsUtil.goHome(armPivot, armExtension, turret));
        eventMap.put("setArmAndTurretToScore", CombinedCommandsUtil.moveToScore(180, pivotHeightType, GamePieceType.CUBE, turret, armPivot));
        eventMap.put("scorePiece", new ScorePieceCommandGroup(turret, armPivot, armExtension, claw, pivotHeightType, GamePieceType.CUBE));
        eventMap.put("engage", chassis.createAutoEngageCommand());

        List<PathPlannerTrajectory> twoPieceEngage = PathPlanner.loadPathGroup(autoName, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command fullAuto = chassis.ramseteAutoBuilder(eventMap).fullAuto(twoPieceEngage);

        //score first piece
        addCommands(new ScorePieceCommandGroup(turret, armPivot, armExtension, claw, pivotHeightType, GamePieceType.CONE));

        //drive
        addCommands(fullAuto);
    }
}
