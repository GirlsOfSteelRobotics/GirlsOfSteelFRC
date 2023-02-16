// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup.autonomous;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.AutoTestHeightsCommandGroup;
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

    public AutonomousFactory(ChassisSubsystem chassis, TurretSubsystem turret, ArmSubsystem arm, ClawSubsystem claw) {
        m_autonomousModes = new SendableChooser<>();

        //Two scoring nodes (high), no engaging (nodes 0,1; nodes 7,8)
        CommandBase twoPieceNodes0and1 = new TWOPieceNodesCommandGroup(chassis, turret, arm, claw, "TWOPieceNodes0And1", AutoPivotHeight.HIGH);
        m_autonomousModes.setDefaultOption("Two Piece Nodes 0 and 1", twoPieceNodes0and1);

        CommandBase twoPieceNodes7and8 = new TWOPieceNodesCommandGroup(chassis, turret, arm, claw, "TWOPieceNodes7And8", AutoPivotHeight.HIGH);
        m_autonomousModes.addOption("Two Piece Nodes 7 and 8", twoPieceNodes7and8);

        //One scoring node (high), engage at end (nodes 3, 4, 5)
        CommandBase oneNodeAndEngage3 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage3", AutoPivotHeight.HIGH, GamePieceType.CONE);
        m_autonomousModes.addOption("One Piece Node and Engage 3", oneNodeAndEngage3);

        CommandBase oneNodeAndEngage4 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage4", AutoPivotHeight.HIGH, GamePieceType.CUBE);
        m_autonomousModes.addOption("One Piece Node and Engage 4", oneNodeAndEngage4);

        CommandBase oneNodeAndEngage5 = new OnePieceAndEngageCommandGroup(chassis, turret, arm, claw, "ONEPieceDockandEngage5", AutoPivotHeight.HIGH, GamePieceType.CONE);
        m_autonomousModes.addOption("One Piece Node and Engage 5", oneNodeAndEngage5);

        //score wherever the robot is (no chassis parameter)
        CommandBase scoreConeAtCurrentPos = new ScorePieceCommandGroup(turret, arm, claw, AutoPivotHeight.HIGH, GamePieceType.CONE);
        m_autonomousModes.addOption("Score Cone High at Current Position's node", scoreConeAtCurrentPos);

        CommandBase scoreCubeAtCurrentPos = new ScorePieceCommandGroup(turret, arm, claw, AutoPivotHeight.HIGH, GamePieceType.CUBE);
        m_autonomousModes.addOption("Score Cube High at Current Position's node", scoreCubeAtCurrentPos);

        //just leave the community (by the player station and by the end)
        CommandBase onlyLeaveCommunityEnd = new OnlyLeaveCommunityCommandGroup(chassis, "EndLeaveCommunity");
        m_autonomousModes.addOption("Leave community zone at far end", onlyLeaveCommunityEnd);

        CommandBase onlyLeaveCommunityPlayerStation = new OnlyLeaveCommunityCommandGroup(chassis, "PlayerStationLeaveCommunity");
        m_autonomousModes.addOption("Leave community zone at player station", onlyLeaveCommunityPlayerStation);

        //just dock and engage the robot from the middle of the charging station
        CommandBase onlyDockAndEngage = new OnlyDockAndEngageCommandGroup(chassis);
        m_autonomousModes.addOption("Only Dock & Engage", onlyDockAndEngage);

        CommandBase testConeHeights = new AutoTestHeightsCommandGroup(turret, arm, claw, GamePieceType.CONE);
        m_autonomousModes.addOption("Test Cone Heights", testConeHeights);

        CommandBase testCubeHeights = new AutoTestHeightsCommandGroup(turret, arm, claw, GamePieceType.CUBE);
        m_autonomousModes.addOption("Test Cube Heights", testCubeHeights);

        /* for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase testConeHeights = new ScorePieceCommandGroup(turret, arm, claw, height, GamePieceType.CONE);
            m_autonomousModes.addOption("Test Cone Heights " + height, testConeHeights);
        }

        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            CommandBase testCubeHeights = new ScorePieceCommandGroup(turret, arm, claw, height, GamePieceType.CUBE);
            m_autonomousModes.addOption("Test Cube Heights " + height, testCubeHeights);
        } */

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
