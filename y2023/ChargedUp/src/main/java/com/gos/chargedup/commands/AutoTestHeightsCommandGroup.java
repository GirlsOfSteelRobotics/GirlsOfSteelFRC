package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoTestHeightsCommandGroup extends SequentialCommandGroup {
    public AutoTestHeightsCommandGroup(TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, GamePieceType gamePieceType) {

        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            addCommands(new ScorePieceCommandGroup(turret, arm, claw, height, gamePieceType));
            System.out.println("Testing " + height + " " + gamePieceType); //idk how to get this statement shown elsewhere help :(
        }

    }
}
