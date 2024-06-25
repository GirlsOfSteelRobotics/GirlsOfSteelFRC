package com.gos.chargedup;

import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import org.littletonrobotics.frc2023.FieldConstants;

public enum AutoAimNodePositions {
    NONE(new Translation2d(), 0),

    HIGH_LEFT(FieldConstants.Grids.HIGH_TRANSLATIONS[0], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.HIGH, GamePieceType.CONE)),
    HIGH_MIDDLE(FieldConstants.Grids.HIGH_TRANSLATIONS[1], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.HIGH, GamePieceType.CUBE)),
    HIGH_RIGHT(FieldConstants.Grids.HIGH_TRANSLATIONS[2], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.HIGH, GamePieceType.CONE)),

    MEDIUM_LEFT(FieldConstants.Grids.MID_TRANSLATIONS[0], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.MEDIUM, GamePieceType.CONE)),
    MEDIUM_MIDDLE(FieldConstants.Grids.MID_TRANSLATIONS[1], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.MEDIUM, GamePieceType.CUBE)),
    MEDIUM_RIGHT(FieldConstants.Grids.MID_TRANSLATIONS[2], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.MEDIUM, GamePieceType.CONE)),

    LOW_LEFT(FieldConstants.Grids.LOW_TRANSLATIONS[0], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.LOW, GamePieceType.CONE)),
    LOW_MIDDLE(FieldConstants.Grids.LOW_TRANSLATIONS[1], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.LOW, GamePieceType.CUBE)),
    LOW_RIGHT(FieldConstants.Grids.LOW_TRANSLATIONS[2], ArmPivotSubsystem.getArmAngleForScoring(AutoPivotHeight.LOW, GamePieceType.CONE));

    final Translation2d m_baseTargetLocation;
    final double m_targetPitch;

    AutoAimNodePositions(Translation2d baseTargetLocation, double targetPitch) {
        m_baseTargetLocation = baseTargetLocation;
        m_targetPitch = targetPitch;
    }

    public Translation2d getBaseLocation() {
        return m_baseTargetLocation;
    }

    public double getTargetPitch() {
        return m_targetPitch;
    }
}
