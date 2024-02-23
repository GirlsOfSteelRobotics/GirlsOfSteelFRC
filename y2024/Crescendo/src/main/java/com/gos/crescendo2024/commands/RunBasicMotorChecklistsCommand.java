package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.HangerSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RunBasicMotorChecklistsCommand extends SequentialCommandGroup {

    public RunBasicMotorChecklistsCommand(HangerSubsystem hangerSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        setName("Run Checklists");

        addCommands(hangerSubsystem.createMoveHangerChecklist());
        addCommands(hangerSubsystem.createMoveHangerSecondaryChecklist());

        addCommands(shooterSubsystem.createMoveLeaderShooterChecklist());
        addCommands(shooterSubsystem.createMoveFollowerShooterChecklist());

        addCommands(intakeSubsystem.createMoveIntakeChecklist());
    }

}
