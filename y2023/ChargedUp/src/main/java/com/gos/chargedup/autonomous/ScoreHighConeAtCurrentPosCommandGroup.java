package com.gos.chargedup.autonomous;


import com.gos.chargedup.commands.AutoScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ScoreHighConeAtCurrentPosCommandGroup extends SequentialCommandGroup {
    public ScoreHighConeAtCurrentPosCommandGroup(TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw) {
        addCommands(new AutoScorePieceCommandGroup(turret, arm, claw, ArmSubsystem.ARM_CONE_HIGH_DEG));
    }
}
