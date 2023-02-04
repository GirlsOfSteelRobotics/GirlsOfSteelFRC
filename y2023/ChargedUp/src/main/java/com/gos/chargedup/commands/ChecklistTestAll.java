package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ChecklistTestAll extends SequentialCommandGroup {

    public ChecklistTestAll(ChassisSubsystem chassis, ArmSubsystem arm, TurretSubsystem turret, IntakeSubsystem intake) {
        addCommands(chassis.createIsLeftMotorMoving());
        addCommands(chassis.createIsRightMotorMoving());
        addCommands(arm.createIsPivotMotorMoving());
        addCommands(turret.createIsTurretMotorMoving());
        addCommands(intake.createIsIntakeMotorMoving());
        addCommands(intake.createIsHopperMotorMoving());

    }

}
