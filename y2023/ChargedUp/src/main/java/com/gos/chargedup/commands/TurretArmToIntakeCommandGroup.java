package com.gos.chargedup.commands;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TurretArmToIntakeCommandGroup extends SequentialCommandGroup {
    public TurretArmToIntakeCommandGroup(ArmSubsystem arm, TurretSubsystem turret, ClawSubsystem claw) {
        addCommands(arm.commandFullRetract());
        addCommands(turret.);
        addCommands(arm.commandPivotArmDown());
        addCommands(claw.);
        addCommands(arm.commandMiddleRetract());
    }
}