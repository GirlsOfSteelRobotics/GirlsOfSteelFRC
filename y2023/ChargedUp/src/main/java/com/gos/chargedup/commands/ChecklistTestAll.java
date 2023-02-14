package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.function.DoubleSupplier;

public class ChecklistTestAll extends SequentialCommandGroup {

    public ChecklistTestAll(DoubleSupplier pressureSupplier, ChassisSubsystem chassis, ArmSubsystem arm, TurretSubsystem turret, IntakeSubsystem intake, ClawSubsystem claw) {
        setName("Self Test Checklist");

        //chassis
        addCommands(chassis.createIsLeftMotorMoving());
        addCommands(chassis.createIsRightMotorMoving());

        //arm
        addCommands(arm.createIsPivotMotorMoving());
        addCommands(arm.createIsArmInnerPneumaticMoving(pressureSupplier));
        addCommands(arm.createIsArmOuterPneumaticMoving(pressureSupplier));

        //turret
        addCommands(turret.createIsTurretMotorMoving());

        //intake
        addCommands(intake.createIsIntakeMotorMoving());
        addCommands(intake.createIsHopperMotorMoving());
        addCommands(intake.createIsIntakeLeftPneumaticMoving(pressureSupplier));
        addCommands(intake.createIsIntakeRightPneumaticMoving(pressureSupplier));

        //claw
        addCommands(claw.createIsClawPneumaticMoving(pressureSupplier));

    }

}
