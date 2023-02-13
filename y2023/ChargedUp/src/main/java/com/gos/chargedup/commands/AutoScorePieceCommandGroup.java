package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoScorePieceCommandGroup extends SequentialCommandGroup {

    //general question because I'll forget: Can we reuse the same or very similar code in teleop? like we link it to a button that just scores
    public AutoScorePieceCommandGroup(TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw, double angle) {
        //assuming robot is in correct position to score (intake facing nodes)
        //turret 180, arm to angle, arm extend, drop piece
        addCommands(turret.commandTurretPID(180));
        addCommands(arm.commandPivotArmToAngle(angle));
        addCommands(arm.commandFullExtend());
        //check that this function works:
        addCommands(claw.createMoveClawIntakeOutCommand());
        //piece dropped by now, reset arm back now:

        addCommands(arm.commandFullRetract());
        addCommands(arm.commandPivotArmToAngle(ArmSubsystem.MIN_ANGLE_DEG));
        addCommands(turret.commandTurretPID(0));



    }
}
