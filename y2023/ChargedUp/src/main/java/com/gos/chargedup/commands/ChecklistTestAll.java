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
        addCommands(arm.createIsArmBottomPneumaticMoving(pressureSupplier));
        addCommands(arm.createIsArmTopPneumaticMoving(pressureSupplier));

        //turret
        addCommands(turret.createIsTurretMotorMoving());

        //intake
        addCommands(intake.createIsIntakeMotorMoving());
        addCommands(intake.createIsIntakePneumaticMoving(pressureSupplier));

        //claw
        addCommands(claw.createIsClawMotorMoving());

    }

}
