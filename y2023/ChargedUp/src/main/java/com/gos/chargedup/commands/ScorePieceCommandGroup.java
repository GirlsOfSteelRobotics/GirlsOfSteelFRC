package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ScorePieceCommandGroup extends SequentialCommandGroup {

    //general question because I'll forget: Can we reuse the same or very similar code in teleop? like we link it to a button that just scores
    public ScorePieceCommandGroup(TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        //assuming robot is in correct position to score (intake facing nodes)
        //arm to angle, arm extend, drop piece
        addCommands((arm.commandMoveArmToPieceScorePosition(pivotHeightType, gamePieceType))
            .alongWith(turret.commandTurretPID(180)));
        addCommands(arm.commandFullExtend());

        //check that this function works:
        addCommands(claw.createMoveClawIntakeOpenCommand());

        //piece dropped on node by now, reset arm back now:
        addCommands(arm.commandFullRetract());
    }
}
