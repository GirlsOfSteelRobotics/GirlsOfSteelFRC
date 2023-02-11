package com.gos.chargedup.commands;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TurretArmToIntakeCommandGroup extends SequentialCommandGroup {
    public TurretArmToIntakeCommandGroup(ArmSubsystem arm, IntakeSubsystem intake, TurretSubsystem turret, ClawSubsystem claw) {
        addCommands(intake.createExtendSolenoidCommand());
        addCommands(turretPID(0.0));
        addCommands(arm.commandPivotArmDown());
        addCommands(intake.createRetractSolenoidCommand());
    }
}