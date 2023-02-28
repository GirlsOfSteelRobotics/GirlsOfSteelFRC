package com.gos.chargedup.commands;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TurretArmToIntakeCommandGroup extends SequentialCommandGroup {
    public TurretArmToIntakeCommandGroup(ArmSubsystem arm, TurretSubsystem turret) {
        addCommands(turret.commandTurretPID(0.0));
        addCommands(arm.commandPivotArmToAngleHold(ArmSubsystem.MIN_ANGLE_DEG));
    }
}
