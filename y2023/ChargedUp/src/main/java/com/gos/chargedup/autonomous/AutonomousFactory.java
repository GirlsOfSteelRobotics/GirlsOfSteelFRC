// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup.autonomous;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import  com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public final class AutonomousFactory {

    private final SendableChooser<Command> m_autonomousModes;

    @SuppressWarnings("PMD.NPathComplexity")
    public AutonomousFactory(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw) {
        m_autonomousModes = new SendableChooser<>();

        //Two scoring nodes (each height), no engaging (nodes 0,1; nodes 7,8)
        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase twoPieceNodes0and1 = new TWOPieceNodesCommandGroup(chassis, turret, arm, claw, "TWOPieceNodes0And1", AutoPivotHeight.HIGH);
            m_autonomousModes.setDefaultOption("Two Piece Nodes 0 and 1: " + height, twoPieceNodes0and1);
        }

        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase twoPieceNodes7and8 = new TWOPieceNodesCommandGroup(chassis, turret, arm, claw, "TWOPieceNodes7And8", AutoPivotHeight.HIGH);
            m_autonomousModes.addOption("Two Piece Nodes 7 and 8: " + height, twoPieceNodes7and8);
        }

        //two piece AND engage
        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase twoPieceEngage = new TwoPieceAndEngageCommandGroup(chassis, turret, arm, claw, "TWOPieceEngage", AutoPivotHeight.HIGH);
            m_autonomousModes.addOption("Two Piece & Engage: " + height, twoPieceEngage);
        }

        //One scoring node (high), engage at end (nodes 3, 4, 5)
        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase oneNodeAndEngage3 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage3", AutoPivotHeight.HIGH, GamePieceType.CONE);
            m_autonomousModes.addOption("One Piece Node and Engage 3: " + height, oneNodeAndEngage3);
        }

        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase oneNodeAndEngage4 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage4", AutoPivotHeight.HIGH, GamePieceType.CUBE);
            m_autonomousModes.addOption("One Piece Node and Engage 4: " + height, oneNodeAndEngage4);
        }

        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase oneNodeAndEngage5 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage5", AutoPivotHeight.HIGH, GamePieceType.CONE);
            m_autonomousModes.addOption("One Piece Node and Engage 5: " + height, oneNodeAndEngage5);
        }

        //score wherever the robot is (no chassis parameter)
        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase scoreConeAtCurrentPos = new ScorePieceCommandGroup(turret, arm, claw, AutoPivotHeight.HIGH, GamePieceType.CONE);
            m_autonomousModes.addOption("Score Cone at Current Position's node: " + height, scoreConeAtCurrentPos);
        }

        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase scoreCubeAtCurrentPos = new ScorePieceCommandGroup(turret, arm, claw, AutoPivotHeight.HIGH, GamePieceType.CUBE);
            m_autonomousModes.addOption("Score Cube at Current Position's node: " + height, scoreCubeAtCurrentPos);
        }

        //just leave the community (by the player station and by the end)
        CommandBase onlyLeaveCommunityEnd = new OnlyLeaveCommunityCommandGroup(chassis, "EndLeaveCommunity");
        m_autonomousModes.addOption("Leave community zone at far end", onlyLeaveCommunityEnd);


        CommandBase onlyLeaveCommunityPlayerStation = new OnlyLeaveCommunityCommandGroup(chassis, "PlayerStationLeaveCommunity");
        m_autonomousModes.addOption("Leave community zone at player station", onlyLeaveCommunityPlayerStation);

        //just dock and engage the robot from the middle of the charging station
        CommandBase onlyDockAndEngage = new OnlyDockAndEngageCommandGroup(chassis);
        m_autonomousModes.addOption("Only Dock & Engage", onlyDockAndEngage);

        //ToDo: Two scoring nodes (nodes 3,4; nodes 4,5) and engage

        //Auto drive
        m_autonomousModes.addOption("Autonomous drive", new AutonomousDriveTimeCommand(chassis));
        //Smart dashboard dropdown
        SmartDashboard.putData(m_autonomousModes);
    }

    public Command getAutonomousCommand() {
        return m_autonomousModes.getSelected();
    }

}
