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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;
import java.util.List;


public class TWOPieceNodesCommandGroup extends SequentialCommandGroup {

    public TWOPieceNodesCommandGroup(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, String autoName, AutoPivotHeight pivotHeightType) {
        double goalAngle;
        if (pivotHeightType == AutoPivotHeight.HIGH) {
            goalAngle = ArmSubsystem.ARM_CUBE_HIGH_DEG;
        } else if (pivotHeightType == AutoPivotHeight.MEDIUM) {
            goalAngle = ArmSubsystem.ARM_CUBE_MIDDLE_DEG;
        } else {
            goalAngle = ArmSubsystem.MIN_ANGLE_DEG;
        }

        HashMap<String, Command> eventMap = new HashMap<>();
        eventMap.put("pickUpObject", new SequentialCommandGroup(
            claw.createMoveClawIntakeCloseCommand() //piece is firmly in the claw? finish + tune soon
        ));

        eventMap.put("resetArmAndTurret", new ParallelCommandGroup(
            turret.commandTurretPID(0),
            arm.commandPivotArmToAngleReg(ArmSubsystem.MIN_ANGLE_DEG)

        ));

        eventMap.put("setArmAndTurretToScore", new ParallelCommandGroup(
            turret.commandTurretPID(180),
            arm.commandPivotArmToAngleHold(goalAngle)

        ));

        List<PathPlannerTrajectory> twoPieceNodes0And1 = PathPlanner.loadPathGroup(autoName, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command fullAuto = chassis.ramseteAutoBuilder(eventMap).fullAuto(twoPieceNodes0And1);

        //score first piece:
        addCommands(new ScorePieceCommandGroup(turret, arm, claw, pivotHeightType, GamePieceType.CONE));

        //drive, get piece, drive back
        addCommands(fullAuto);

        //score second piece
        //addCommands(turret.commandTurretPID(69)); //NICE
        addCommands(new ScorePieceCommandGroup(turret, arm, claw, pivotHeightType, GamePieceType.CUBE));


    }
}
