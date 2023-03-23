package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.littletonrobotics.frc2023.FieldConstants;

public class AutoAimChassisToNodeOnTheFly extends BaseAutoAimChassisCommand {

    private AutoChassisCommands m_selectedPosition = AutoChassisCommands.NONE;

    private enum AutoChassisCommands {
        NONE,
        HIGH_LEFT, HIGH_MIDDLE, HIGH_RIGHT,
        MEDIUM_LEFT, MEDIUM_MIDDLE, MEDIUM_RIGHT,
        LOW_LEFT, LOW_MIDDLE, LOW_RIGHT
    }

    public AutoAimChassisToNodeOnTheFly(ArmPivotSubsystem armSubsystem, ChassisSubsystem chassisSubsystem, LEDManagerSubsystem ledManagerSubsystem, ArmExtensionSubsystem armExtension) {
        super(armSubsystem, chassisSubsystem, ledManagerSubsystem, armExtension);

        SmartDashboard.putData("Select Position", new AutoAimChassisToNodeOnTheFly.ChooseAimArmCommandSendable());

    }

    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void execute() {
        if (m_selectedPosition == null || m_selectedPosition == AutoAimChassisToNodeOnTheFly.AutoChassisCommands.NONE) {
            return;
        }

        Translation2d baseTargetLocation;
        double targetPitch;

        switch (m_selectedPosition) {
        case HIGH_LEFT:
            baseTargetLocation = FieldConstants.Grids.HIGH_TRANSLATIONS[0];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.HIGH, GamePieceType.CONE);
            break;
        case HIGH_MIDDLE:
            baseTargetLocation = FieldConstants.Grids.HIGH_TRANSLATIONS[1];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.HIGH, GamePieceType.CUBE);
            break;
        case HIGH_RIGHT:
            baseTargetLocation = FieldConstants.Grids.HIGH_TRANSLATIONS[2];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.HIGH, GamePieceType.CONE);
            break;
        case MEDIUM_LEFT:
            baseTargetLocation = FieldConstants.Grids.MID_TRANSLATIONS[0];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.MEDIUM, GamePieceType.CONE);
            break;
        case MEDIUM_MIDDLE:
            baseTargetLocation = FieldConstants.Grids.MID_TRANSLATIONS[1];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.MEDIUM, GamePieceType.CUBE);
            break;
        case MEDIUM_RIGHT:
            baseTargetLocation = FieldConstants.Grids.MID_TRANSLATIONS[2];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.MEDIUM, GamePieceType.CONE);
            break;
        case LOW_LEFT:
            baseTargetLocation = FieldConstants.Grids.LOW_TRANSLATIONS[0];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.LOW, GamePieceType.CONE);
            break;
        case LOW_MIDDLE:
            baseTargetLocation = FieldConstants.Grids.LOW_TRANSLATIONS[1];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.LOW, GamePieceType.CUBE);
            break;
        default:
            baseTargetLocation = FieldConstants.Grids.LOW_TRANSLATIONS[2];
            targetPitch = m_armSubsystem.getArmAngleForScoring(AutoPivotHeight.LOW, GamePieceType.CONE);
            break;
        }

        runAutoAim(baseTargetLocation, targetPitch);
    }

    private class ChooseAimArmCommandSendable implements Sendable {

        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType("ScoringPosition");

            builder.addDoubleProperty(
                "SelectedPosition", null, this::handleScoringPosition);
            builder.addStringProperty("AsString", () -> m_selectedPosition.toString(), null);
        }

        private void handleScoringPosition(double d) {
            try {
                m_selectedPosition = AutoAimChassisToNodeOnTheFly.AutoChassisCommands.values()[(int) d];
            }
            catch (Exception ex) { // NOPMD
                m_selectedPosition = AutoChassisCommands.NONE;
                ex.printStackTrace(); // NOPMD
            }
        }
    }

}
