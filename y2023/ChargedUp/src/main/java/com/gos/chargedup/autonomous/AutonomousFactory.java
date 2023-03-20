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
import java.util.HashMap;
import java.util.Map;

public final class AutonomousFactory {

    private final SendableChooser<AutoPivotHeight> m_armHeight;

    private final SendableChooser<AutonMode> m_chooseAutoOption;

    public enum AutonMode {
        ONLY_LEAVE_COMMUNITY_END,
        ONLY_LEAVE_COMMUNITY_PLAYER_STATION,
        ONLY_DOCK_AND_ENGAGE,
        SCORE_CUBE_AT_CURRENT_POS,

        ONE_NODE_AND_LEAVE_COMMUNITY_1,
        ONE_NODE_AND_LEAVE_COMMUNITY_7,

        SCORE_CONE_AT_CURRENT_POS,

        ONE_NODE_AND_ENGAGE_3,
        ONE_NODE_AND_ENGAGE_4,
        ONE_NODE_AND_ENGAGE_5,
        ONE_NODE_AND_LEAVE_AND_ENGAGE_4,

        TWO_PIECE_NODE_0_AND_1,
        TWO_PIECE_NODE_7_AND_8,

        TWO_PIECE_ENGAGE
    }

    private final Map<AutoPivotHeight, Map<AutonMode, Command>> m_autoOptions = new HashMap<>();

    @SuppressWarnings({"PMD.NPathComplexity", "PMD.CyclomaticComplexity"})
    public AutonomousFactory(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw) {
        m_armHeight = new SendableChooser<>();
        m_chooseAutoOption = new SendableChooser<>();

        // Initialize map
        for (AutoPivotHeight height : AutoPivotHeight.values()) {
            m_autoOptions.put(height, new HashMap<>());
            m_autoOptions.get(height).put(AutonMode.TWO_PIECE_NODE_0_AND_1, new TWOPieceNodesCommandGroup(chassis, armPivot, armExtension, claw, "TWOPieceNodes0And1", height));
            m_autoOptions.get(height).put(AutonMode.TWO_PIECE_NODE_7_AND_8, new TWOPieceNodesCommandGroup(chassis, armPivot, armExtension, claw, "TWOPieceNodes7And8", height));
            m_autoOptions.get(height).put(AutonMode.TWO_PIECE_ENGAGE, new TwoPieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "TWOPieceEngage", height));

            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_1, new OnePieceAndLeaveCommunityCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceAndLeaveCommunity1", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_7, new OnePieceAndLeaveCommunityCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceAndLeaveCommunity7", height, GamePieceType.CUBE));


            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_ENGAGE_3, new OnePieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceDockandEngage3", height, GamePieceType.CONE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_ENGAGE_4, new OnePieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceDockandEngage4", height, GamePieceType.CUBE));
            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_ENGAGE_5, new OnePieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "ONEPieceDockandEngage5", height, GamePieceType.CONE));

            m_autoOptions.get(height).put(AutonMode.ONE_NODE_AND_LEAVE_AND_ENGAGE_4, new OnePieceAndEngageCommandGroup(chassis, armPivot, armExtension, claw, "DockandEngage_Community4", height, GamePieceType.CUBE));

            m_autoOptions.get(height).put(AutonMode.SCORE_CONE_AT_CURRENT_POS, new ScorePieceCommandGroup(armPivot, armExtension, claw, height, GamePieceType.CONE));
            m_autoOptions.get(height).put(AutonMode.SCORE_CUBE_AT_CURRENT_POS, new ScorePieceCommandGroup(armPivot, armExtension, claw, height, GamePieceType.CUBE));

            m_autoOptions.get(height).put(AutonMode.ONLY_LEAVE_COMMUNITY_END, new OnlyLeaveCommunityCommandGroup(chassis, "EndLeaveCommunity"));
            m_autoOptions.get(height).put(AutonMode.ONLY_LEAVE_COMMUNITY_PLAYER_STATION, new OnlyLeaveCommunityCommandGroup(chassis, "PlayerStationLeaveCommunity"));
            m_autoOptions.get(height).put(AutonMode.ONLY_DOCK_AND_ENGAGE, new OnlyDockAndEngageCommandGroup(chassis));
        }

        for (AutonMode auto: AutonMode.values()) {
            if (auto == AutonMode.ONE_NODE_AND_ENGAGE_4) {
                m_chooseAutoOption.setDefaultOption(auto.toString(), auto);
            } else {
                m_chooseAutoOption.addOption(auto.toString(), auto);
            }
        }
        SmartDashboard.putData("Auto Mode Select", m_chooseAutoOption);

        m_armHeight.addOption("Low", AutoPivotHeight.LOW);
        m_armHeight.setDefaultOption("Medium", AutoPivotHeight.MEDIUM);
        m_armHeight.addOption("High", AutoPivotHeight.HIGH);
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
