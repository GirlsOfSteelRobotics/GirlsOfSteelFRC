package com.gos.chargedup.autonomous;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;
import java.util.Map;


public class TWOPieceNodesCommandGroup extends SequentialCommandGroup {

    public TWOPieceNodesCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, String autoName, AutoPivotHeight pivotHeightType) {

        Map<String, Command> eventMap = EventMapUtil.createDefaultEventMap(chassis, armPivot, armExtension, claw, pivotHeightType, GamePieceType.CUBE);

        List<PathPlannerTrajectory> twoPieceNodes0And1 = PathPlanner.loadPathGroup(autoName, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command fullAuto = chassis.ramseteAutoBuilder(eventMap).fullAuto(twoPieceNodes0And1);

        //score first piece:
        addCommands(new InstantCommand(claw::holdPiece));
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, GamePieceType.CONE));

        // Get the turret mostly spun back around before driving the path and moving the arm
        //addCommands(turret.goHome().until(() -> turret.getTurretAngleDeg() < 90));

        //drive, get piece, drive back
        addCommands(fullAuto);

        //score second piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, GamePieceType.CUBE));


    }
}
