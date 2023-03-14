package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.littletonrobotics.frc2023.FieldConstants;


public class AutoAimTurretToNodeOnTheFly extends BaseAutoAimTurretCommand {
    private AutoTurretCommands m_selectedPosition;

    private enum AutoTurretCommands {
        HIGH_LEFT, HIGH_MIDDLE, HIGH_RIGHT,
        MEDIUM_LEFT, MEDIUM_MIDDLE, MEDIUM_RIGHT,
        LOW_LEFT, LOW_MIDDLE, LOW_RIGHT
    }

    public AutoAimTurretToNodeOnTheFly(ArmPivotSubsystem armSubsystem, ArmExtensionSubsystem armExtension, ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, LEDManagerSubsystem ledManagerSubsystem) {
        super(armSubsystem, chassisSubsystem, turretSubsystem, ledManagerSubsystem, armExtension);

        SmartDashboard.putData("Select Position", new ChooseAimTurretCommandSendable());
    }

    private class ChooseAimTurretCommandSendable implements Sendable {

        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType("ScoringPositionxx");

            builder.addDoubleProperty(
                "ScoringPositionyy", null, this::handleScoringPosition);
            builder.addIntegerProperty(
                "ScoringPositioninnnitt", null, (x) -> System.out.println(x));

        }

        private void handleScoringPosition(double d) {
            try {
                System.out.println("D!" + d);
//                m_selectedPosition = AutoTurretCommands.values()[(int)d];
            } catch (Exception ex) {
//                m_selectedPosition = null;
            }
        }
    }

    @Override
    public void execute() {
        AutoTurretCommands nodePos = m_selectedPosition;
        Translation2d baseTargetLocation;
        double targetPitch;

        if (nodePos == null) {
            return;
        }

        switch (nodePos) {
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
}
