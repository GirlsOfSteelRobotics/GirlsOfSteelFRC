// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup.autonomous;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;


public final class AutonomousFactory {
    private final SendableChooser<Command> m_autonomousModes;
    public final CommandBase m_onlyDockAndEngage;
    public final CommandBase m_onlyLeaveCommunity;
    //    public final CommandBase m_dockEngageAndCommunity;
    public final CommandBase m_oneNodeAndDock;
    //    public final CommandBase m_onePieceAndDockEngage;
    public final CommandBase m_manyPieceAuto;
    public final CommandBase m_twoPieceAuto;

    public AutonomousFactory(ChassisSubsystem chassis) {
        m_autonomousModes = new SendableChooser<>();

        m_onlyDockAndEngage = new OnlyDockAndEngageCommandGroup(chassis);
        m_autonomousModes.addOption("Only Dock & Engage", m_onlyDockAndEngage);

        m_onlyLeaveCommunity = new OnlyLeaveCommunityCommandGroup(chassis);
        m_autonomousModes.addOption("Only Leave Community", m_onlyLeaveCommunity);

        //        m_dockEngageAndCommunity = new DockEngageAndCommunityCommandGroup(chassis);
        //        m_autonomousModes.addOption("Dock, Engage, & Community", m_dockEngageAndCommunity);

        m_oneNodeAndDock = new OneNodeAndDockCommandGroup(chassis);
        m_autonomousModes.addOption("One Node & Dock", m_oneNodeAndDock);

        //        m_onePieceAndDockEngage = new OnePieceAndDockEngageCommandGroup(chassis);
        //        m_autonomousModes.addOption("One Piece, Dock & Engage", m_onePieceAndDockEngage);

        m_manyPieceAuto = new ManyPieceAutoCommandGroup(chassis);
        m_autonomousModes.addOption("Many Piece", m_manyPieceAuto);

        m_twoPieceAuto = new TwoPieceAutoCommandGroup(chassis);
        m_autonomousModes.addOption("Two Piece", m_twoPieceAuto);

        m_autonomousModes.addOption("Autonomous drive", new AutonomousDriveTimeCommand(chassis));
        SmartDashboard.putData(m_autonomousModes);

    }

    public Command getAutonomousCommand() {
        return m_autonomousModes.getSelected();
    }


}
