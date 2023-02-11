package com.gos.chargedup.autonomous;


import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ScoreHighAtCurrentPosCommandGroup extends SequentialCommandGroup {
    public ScoreHighAtCurrentPosCommandGroup(TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw) {
        addCommands(new ScorePieceCommandGroup(turret, arm, claw));
    }
}