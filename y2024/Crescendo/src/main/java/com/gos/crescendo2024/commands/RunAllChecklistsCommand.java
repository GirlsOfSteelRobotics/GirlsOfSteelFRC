package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.HangerSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.function.DoubleSupplier;


public class RunAllChecklistsCommand extends SequentialCommandGroup {

    public RunAllChecklistsCommand(ArmPivotSubsystem armPivotSubsystem, HangerSubsystem hangerSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        setName("Run Checklists");

        addCommands(armPivotSubsystem.createMoveArmPivotChecklist(new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return 1;
            }
        }));

        addCommands(hangerSubsystem.createMoveHangerChecklist());

        addCommands(intakeSubsystem.createMoveIntakeChecklist());

        addCommands(shooterSubsystem.createMoveLeaderShooterChecklist());
        addCommands(shooterSubsystem.createMoveFollowerShooterChecklist());

    }

}