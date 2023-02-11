// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup.autonomous;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import  com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;


public final class AutonomousFactory {
    //Unused auto paths: should not be used beyond testing purposes (and definitely not at competitions)
    // public final CommandBase m_dockEngageAndCommunity;
    //public final CommandBase m_oneNodeAndDock;

    //public final CommandBase m_onePieceAndDockEngage;
    //public final CommandBase m_manyPieceAuto;
    //public final CommandBase m_twoPieceAuto;

    private final SendableChooser<Command> m_autonomousModes;

    public final CommandBase m_onlyDockAndEngage;

    public final CommandBase m_twoPieceNodes0and1;

    public final CommandBase m_twoPieceNodes7and8;

    public final CommandBase m_oneNodeAndEngage3;

    public final CommandBase m_oneNodeAndEngage4;

    public final CommandBase m_oneNodeAndEngage5;

    public final CommandBase m_scoreAtCurrentPos;

    public final CommandBase m_onlyLeaveCommunityEnd;

    public final CommandBase m_onlyLeaveCommunityPlayerStation;

    public AutonomousFactory(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw) {
        m_autonomousModes = new SendableChooser<>();

        //Unused auto codes to be deleted as soon as we're ready:
        //m_dockEngageAndCommunity = new DockEngageAndCommunityCommandGroup(chassis);
        //m_autonomousModes.addOption("Dock, Engage, & Community", m_dockEngageAndCommunity);

        //m_oneNodeAndDock = new OneNodeAndDockCommandGroup(chassis);
        //m_autonomousModes.addOption("One Node & Dock", m_oneNodeAndDock);

        //m_onePieceAndDockEngage = new OnePieceAndDockEngageCommandGroup(chassis);
        //m_autonomousModes.addOption("One Piece, Dock & Engage", m_onePieceAndDockEngage);

        //m_manyPieceAuto = new ManyPieceAutoCommandGroup(chassis);
        //m_autonomousModes.addOption("Many Piece", m_manyPieceAuto);

        //m_twoPieceAuto = new TwoPieceAutoCommandGroup(chassis);
        //m_autonomousModes.addOption("Two Piece", m_twoPieceAuto);

        //Two scoring nodes (high), no engaging (nodes 0,1; nodes 7,8)
        m_twoPieceNodes0and1 = new TWOPieceNodesCommandGroup(chassis, turret, arm, claw, "TWOPieceNodes0And1");
        m_autonomousModes.setDefaultOption("Two Piece Nodes 0 and 1", m_twoPieceNodes0and1);

        m_twoPieceNodes7and8 = new TWOPieceNodesCommandGroup(chassis, turret, arm, claw, "TWOPieceNodes7And8");
        m_autonomousModes.addOption("Two Piece Nodes 7 and 8", m_twoPieceNodes7and8);

        //One scoring node (high), engage at end (nodes 3, 4, 5)
        m_oneNodeAndEngage3 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage3");
        m_autonomousModes.addOption("One Piece Node and Engage 3", m_oneNodeAndEngage3);

        m_oneNodeAndEngage4 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage4");
        m_autonomousModes.addOption("One Piece Node and Engage 4", m_oneNodeAndEngage4);

        m_oneNodeAndEngage5 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage5");
        m_autonomousModes.addOption("One Piece Node and Engage 5", m_oneNodeAndEngage5);

        //score wherever the robot is (no chassis parameter)
        m_scoreAtCurrentPos = new ScoreHighAtCurrentPosCommandGroup(turret, arm, claw);
        m_autonomousModes.addOption("Score High at Current Position's node", m_scoreAtCurrentPos);

        //just leave the community (by the player station and by the end)
        m_onlyLeaveCommunityEnd = new OnlyLeaveCommunityCommandGroup(chassis, "EndLeaveCommunity");
        m_autonomousModes.addOption("Leave community zone at far end", m_onlyLeaveCommunityEnd);

        m_onlyLeaveCommunityPlayerStation = new OnlyLeaveCommunityCommandGroup(chassis, "PlayerStationLeaveCommunity");
        m_autonomousModes.addOption("Leave community zone at player station", m_onlyLeaveCommunityPlayerStation);

        //just dock and engage the robot from the middle of the charging station
        m_onlyDockAndEngage = new OnlyDockAndEngageCommandGroup(chassis);
        m_autonomousModes.addOption("Only Dock & Engage", m_onlyDockAndEngage);

        //To be finished: Two scoring nodes (nodes 3,4; nodes 4,5) and engage

        //Auto drive
        m_autonomousModes.addOption("Autonomous drive", new AutonomousDriveTimeCommand(chassis));
        //Smart dashboard dropdown
        SmartDashboard.putData(m_autonomousModes);
    }

    public Command getAutonomousCommand() {
        return m_autonomousModes.getSelected();
    }

}
