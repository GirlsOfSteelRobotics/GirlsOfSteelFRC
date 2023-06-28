package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.gos.chargedup.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.function.DoubleSupplier;

public class ChecklistTestAll extends SequentialCommandGroup {

    public ChecklistTestAll(DoubleSupplier pressureSupplier, ChassisSubsystemInterface chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw) {
        setName("Self Test Checklist");

        //chassis
        addCommands(chassis.selfTestMotors());

        //arm
        addCommands(armPivot.createIsPivotMotorMoving());
        addCommands(armExtension.createIsArmBottomPneumaticMoving(pressureSupplier));
        addCommands(armExtension.createIsArmTopPneumaticMoving(pressureSupplier));

        //turret
        //addCommands(turret.createIsTurretMotorMoving());

        //claw
        addCommands(claw.createIsClawMotorMoving());

    }

}
