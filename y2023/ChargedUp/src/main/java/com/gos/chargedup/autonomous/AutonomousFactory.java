// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup.autonomous;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;
import java.util.Map;

public final class AutonomousFactory {
    private static final AutonMode DEFAULT_MODE = AutonMode.ONE_NODE_AND_ENGAGE_4;
    private static final AutoPivotHeight DEFAULT_HEIGHT = AutoPivotHeight.MEDIUM;

    private final SendableChooser<AutoPivotHeight> m_armHeight;

    private final SendableChooser<AutonMode> m_chooseAutoOption;

    public enum AutonMode {
        DO_NOTHING,

        // 0 Piece, exit side
        ONLY_LEAVE_COMMUNITY_0,
        ONLY_LEAVE_COMMUNITY_8,

        // 0 Piece, dock and engage
        ONLY_DOCK_AND_ENGAGE_4,

        // 1 Piece, no driving
        SCORE_CUBE_AT_CURRENT_POS,
        SCORE_CONE_AT_CURRENT_POS,

        // 1 Piece, exit closest side
        ONE_NODE_AND_LEAVE_COMMUNITY_0,
        ONE_NODE_AND_LEAVE_COMMUNITY_1,
        ONE_NODE_AND_LEAVE_COMMUNITY_2,
        ONE_NODE_AND_LEAVE_COMMUNITY_3,
        ONE_NODE_AND_LEAVE_COMMUNITY_5,
        ONE_NODE_AND_LEAVE_COMMUNITY_6,
        ONE_NODE_AND_LEAVE_COMMUNITY_7,
        ONE_NODE_AND_LEAVE_COMMUNITY_8,

        // One piece + dock and engage
        ONE_NODE_AND_ENGAGE_3,
        ONE_NODE_AND_ENGAGE_4,
        ONE_NODE_AND_ENGAGE_5,

        // Score, Drive over bridge and out of community, then engage
        ONE_NODE_LEAVE_AND_ENGAGE_4,

        // Two piece, driving through the closest side
        TWO_PIECE_NODE_0_AND_1,
        TWO_PIECE_NODE_7_AND_8,

        // Leave community, get second piece, and engage
        TWO_PIECE_LEAVE_COMMUNITY_ENGAGE,
    }

    private final Map<AutoPivotHeight, Map<AutonMode, Command>> m_autoOptions = new HashMap<>();

    @SuppressWarnings({"PMD.NPathComplexity", "PMD.CyclomaticComplexity"})
    public AutonomousFactory(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw) {
        m_armHeight = new SendableChooser<>();
        m_chooseAutoOption = new SendableChooser<>();

        // Initialize map
        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            m_autoOptions.put(height, new HashMap<>());

            m_autoOptions.get(height).put(AutonMode.DO_NOTHING, new SequentialCommandGroup());

            // Score, no driving
            m_autoOptions.get(height).put(AutonMode.SCORE_CONE_AT_CURRENT_POS, new ScorePieceCommandGroup(armPivot, armExtension, claw, height, GamePieceType.CONE));
            m_autoOptions.get(height).put(AutonMode.SCORE_CUBE_AT_CURRENT_POS, new ScorePieceCommandGroup(armPivot, armExtension, claw, height, GamePieceType.CUBE));

            // Only driving
            m_autoOptions.get(height).put(AutonMode.ONLY_LEAVE_COMMUNITY_0, new OnlyLeaveCommunityCommandGroup(chassis, "OnePieceLeaveCommunity0"));
            m_autoOptions.get(height).put(AutonMode.ONLY_LEAVE_COMMUNITY_8, new OnlyLeaveCommunityCommandGroup(chassis, "OnePieceLeaveCommunity8"));

            m_autoOptions.get(height).put(AutonMode.ONLY_DOCK_AND_ENGAGE_4, new OnlyDockAndEngageCommandGroup(chassis, "NoPieceDockAndEngage"));

            // Score Piece at Node and Leave Community zone on the nearest side
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_0, new OnePieceAndLeaveCommunityCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity0", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_1, new OnePieceAndLeaveCommunityCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity1", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_2, new OnePieceAndLeaveCommunityWithTurnCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity2", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_3, new OnePieceAndLeaveCommunityWithTurnCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity3", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_5, new OnePieceAndLeaveCommunityWithTurnCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity5", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_6, new OnePieceAndLeaveCommunityWithTurnCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity6", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_7, new OnePieceAndLeaveCommunityCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity7", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_8, new OnePieceAndLeaveCommunityCommandGroup(chassis, armPivot, armExtension, claw, "OnePieceLeaveCommunity8", height, GamePieceType.CUBE));

            // 1 Piece + engage
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_ENGAGE_3, new OnePieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceDockandEngage3", height, GamePieceType.CONE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_ENGAGE_4, new OnePieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceDockandEngage4", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_ENGAGE_5, new OnePieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceDockandEngage5", height, GamePieceType.CONE));

            // Score Game Piece, drive over bridge out of the community zone, then engage
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_LEAVE_AND_ENGAGE_4, new OnePieceLeaveAndEngageFullCommandGroup(chassis, armPivot, armExtension, claw, height, GamePieceType.CUBE, "OnePieceLeaveAndEngage4"));

            //MVR Path Option 4: Two piece auto without the turret
            m_autoOptions.get(height).put(AutonMode.TWO_PIECE_NODE_0_AND_1, new TWOPieceNodesCommandGroup(chassis, armPivot, armExtension, claw, height, "TwoPieceNodes0And1Part1", "TwoPieceNodes0And1Part2", "TwoPieceNodes0And1Part3"));
            m_autoOptions.get(height).put(AutonMode.TWO_PIECE_NODE_7_AND_8, new TWOPieceNodesCommandGroup(chassis, armPivot, armExtension, claw, height, "TwoPieceNodes7And8Part1", "TwoPieceNodes7And8Part2", "TwoPieceNodes7And8Part3"));

            //MVR Path Option 3: Leave community, get second piece, and engage
            m_autoOptions.get(height).put(AutonMode.TWO_PIECE_LEAVE_COMMUNITY_ENGAGE, new TwoPieceLeaveCommunityAndEngageCommandGroup(chassis, armPivot, armExtension, claw, height, GamePieceType.CUBE));
        }

        for (AutonMode auto: AutonMode.values()) {
            if (auto == DEFAULT_MODE) {
                m_chooseAutoOption.setDefaultOption(auto.toString(), auto);
            } else {
                m_chooseAutoOption.addOption(auto.toString(), auto);
            }
        }
        SmartDashboard.putData("Auto Mode Select", m_chooseAutoOption);

        for (AutoPivotHeight height: AutoPivotHeight.values()) {
            if (height == DEFAULT_HEIGHT) {
                m_armHeight.setDefaultOption(height.toString(), height);
            } else {
                m_armHeight.addOption(height.toString(), height);
            }
        }
        SmartDashboard.putData("Height Options", m_armHeight);
    }

    public Command getAutonomousCommand() {
        AutoPivotHeight height = m_armHeight.getSelected();
        AutonMode mode = m_chooseAutoOption.getSelected();
        return m_autoOptions.get(height).get(mode);
    }

    public AutonMode getSelectedAuto() {
        return m_chooseAutoOption.getSelected();
    }

    public AutoPivotHeight getSelectedHeight() {
        return m_armHeight.getSelected();
    }

}
