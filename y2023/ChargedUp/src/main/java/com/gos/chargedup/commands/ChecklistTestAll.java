package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ChecklistTestAll extends SequentialCommandGroup {

    public ChecklistTestAll(PneumaticHub pneumaticHub, ChassisSubsystem chassis, ArmSubsystem arm, TurretSubsystem turret, IntakeSubsystem intake, ClawSubsystem claw) {

        //chassis
        addCommands(chassis.createIsLeftMotorMoving());
        addCommands(chassis.createIsRightMotorMoving());

        //arm
        addCommands(arm.createIsPivotMotorMoving());
        addCommands(arm.createIsArmInnerPneumaticMoving(pneumaticHub));
        addCommands(arm.createIsArmOuterPneumaticMoving(pneumaticHub));

        //turret
        addCommands(turret.createIsTurretMotorMoving());

        //intake
        addCommands(intake.createIsIntakeMotorMoving());
        addCommands(intake.createIsHopperMotorMoving());
        addCommands(intake.createIsIntakeLeftPneumaticMoving(pneumaticHub));
        addCommands(intake.createIsIntakeRightPneumaticMoving(pneumaticHub));

        //claw
        addCommands(claw.createIsLeftClawPneumaticMoving(pneumaticHub));
        addCommands(claw.createIsRightClawPneumaticMoving(pneumaticHub));

    }

}
