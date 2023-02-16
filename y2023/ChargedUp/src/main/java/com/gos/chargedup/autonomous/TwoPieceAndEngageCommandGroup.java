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
import java.util.List;


public class TwoPieceAndEngageCommandGroup extends SequentialCommandGroup {
    public TwoPieceAndEngageCommandGroup(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, String autoName, AutoPivotHeight pivotHeightType) {
        HashMap<String, Command> eventMap = new HashMap<>();
        eventMap.put("pickUpObject", new SequentialCommandGroup(
            claw.createMoveClawIntakeCloseCommand()
        ));

        eventMap.put("scorePiece", new SequentialCommandGroup(
            new ScorePieceCommandGroup(turret, arm, claw, pivotHeightType, GamePieceType.CONE) //fix height
        ));

        eventMap.put("engage", new SequentialCommandGroup(
            chassis.createAutoEngageCommand()
        ));

        List<PathPlannerTrajectory> twoPieceEngage = PathPlanner.loadPathGroup(autoName, Constants.DEFAULT_PATH_CONSTRAINTS);
        Command fullAuto = chassis.ramseteAutoBuilder(eventMap).fullAuto(twoPieceEngage);

        //score
        addCommands(new ScorePieceCommandGroup(turret, arm, claw, pivotHeightType, GamePieceType.CONE));
        //drive
        addCommands(fullAuto);
    }
}
