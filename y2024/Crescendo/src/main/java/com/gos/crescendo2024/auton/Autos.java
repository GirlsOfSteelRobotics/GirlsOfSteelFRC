// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.gos.crescendo2024.commands.CombinedCommands;
import com.gos.crescendo2024.commands.SpeakerAimAndShootCommand;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public final class Autos {
    private final SendableChooser<Command> m_autonChooser;
    private final ChassisSubsystem m_chassis;
    private final ArmPivotSubsystem m_armPivot;
    private final IntakeSubsystem m_intake;
    private final ShooterSubsystem m_shooter;

    public Autos(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, IntakeSubsystem intake, ShooterSubsystem shooter) {
        m_chassis = chassis;
        m_armPivot = armPivot;
        m_intake = intake;
        m_shooter = shooter;

        NamedCommands.registerCommand("AimAndShootIntoSpeaker", new SpeakerAimAndShootCommand(m_armPivot, m_chassis, m_intake, m_shooter));
        NamedCommands.registerCommand("IntakePiece", CombinedCommands.intakePieceCommand(m_armPivot, m_intake).withTimeout(1));
        NamedCommands.registerCommand("MoveArmToSpeakerAngle", m_armPivot.createMoveArmToDefaultSpeakerAngleCommand());
        NamedCommands.registerCommand("ShooterDefaultRpm", m_shooter.createRunDefaultRpmCommand());

        m_autonChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", m_autonChooser);
    }

    public Command getSelectedAutonomous() {
        return m_autonChooser.getSelected();
    }
}
