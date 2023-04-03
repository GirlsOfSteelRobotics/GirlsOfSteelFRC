package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ScorePieceCommandGroup extends SequentialCommandGroup {

    //general question because I'll forget: Can we reuse the same or very similar code in teleop? like we link it to a button that just scores
    public ScorePieceCommandGroup(ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        //assuming robot is in correct position to score (intake facing nodes)
        //arm to angle, arm extend, drop piece
        addCommands(new InstantCommand(claw::holdPiece));
        addCommands(armPivot.commandMoveArmToPieceScorePositionAndHold(pivotHeightType, gamePieceType));
        addCommands(armExtension.createArmToSpecifiedHeight(pivotHeightType, gamePieceType));

        if (gamePieceType == GamePieceType.CONE && pivotHeightType != AutoPivotHeight.LOW) {
            addCommands(armPivot.commandMoveArmToPieceScorePositionDifferenceAndHold(pivotHeightType, gamePieceType, -6));
        }

        //check that this function works:
        addCommands(claw.createMoveClawIntakeOutWithTimeoutCommand());
    }
}
