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
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import jdk.jfr.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TWOPieceNodesCommandGroup extends SequentialCommandGroup {
    public static final PathConstraints FASTER_PATH_CONSTRAINTS = new PathConstraints(Units.inchesToMeters(120), Units.inchesToMeters(120));
    public static final PathConstraints NOT_AS_FAST_PATH_CONSTRAINTS = new PathConstraints(Units.inchesToMeters(70), Units.inchesToMeters(70));

    public TWOPieceNodesCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, String pathStart, String pathMiddle, String pathEnd) {
        PathPlannerTrajectory firstPiece = PathPlanner.loadPath(pathStart, FASTER_PATH_CONSTRAINTS, true);
        Command driveToFirstPiece = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(firstPiece);

        List<PathPlannerTrajectory> getSecondPiece = PathPlanner.loadPathGroup(pathMiddle, false, NOT_AS_FAST_PATH_CONSTRAINTS);
        Map<String, Command> eventMap = new HashMap<>();
        eventMap.put("GrabPiece", CombinedCommandsUtil.goToGroundPickup(armPivot, armExtension, 10, 200000));
        Command driveToGetSecondPiece = chassis.ramseteAutoBuilder(eventMap).fullAuto(getSecondPiece);


        PathPlannerTrajectory scoreSecondPiece = PathPlanner.loadPath(pathEnd, FASTER_PATH_CONSTRAINTS, false);
        Command driveToScoreSecondPiece = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(scoreSecondPiece);

        //score piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, GamePieceType.CONE));

        //first part
        addCommands(driveToFirstPiece
            .alongWith(armPivot.commandPivotArmToAngleHold(-10))
            .alongWith(Commands.waitSeconds(0.25)
                .andThen(armExtension.commandMiddleRetract())));

        //turn 180
        addCommands(chassis.createTurnPID(0));
            // .andThen(armPivot.commandPivotArmToAngleHold(-10)
            //     .alongWith(Commands.waitUntil(() -> armPivot.getArmAngleDeg() > armPivot.GROUND_PICKUP_ANGLE)
            //     .andThen(armExtension.commandMiddleRetract()))));

        //drive second part
        addCommands(driveToGetSecondPiece
            .raceWith(claw.createMoveClawIntakeInCommand()));

        //turn 180
        addCommands(chassis.createTurnPID(180)
            .raceWith(claw.createMoveClawIntakeInCommand()));


        //third part
        addCommands(driveToScoreSecondPiece
            .alongWith(Commands.waitSeconds(0.75).andThen(armExtension.commandFullRetract()))
            .raceWith(claw.createMoveClawIntakeInCommand()));

        //score piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, GamePieceType.CUBE));

    }
}
