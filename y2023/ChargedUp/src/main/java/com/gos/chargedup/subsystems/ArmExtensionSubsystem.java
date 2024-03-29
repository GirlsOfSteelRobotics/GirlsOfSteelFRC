package com.gos.chargedup.subsystems;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.lib.checklists.DoubleSolenoidMovesChecklist;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class ArmExtensionSubsystem extends SubsystemBase {
    private static final DoubleSolenoid.Value TOP_PISTON_EXTENDED = DoubleSolenoid.Value.kReverse;
    private static final DoubleSolenoid.Value TOP_PISTON_RETRACTED = DoubleSolenoid.Value.kForward;

    private static final DoubleSolenoid.Value BOTTOM_PISTON_EXTENDED = DoubleSolenoid.Value.kReverse;
    private static final DoubleSolenoid.Value BOTTOM_PISTON_RETRACTED = DoubleSolenoid.Value.kForward;

    public enum ArmExtension {
        FULL_RETRACT,
        MIDDLE_RETRACT,
        FULL_EXTEND
    }

    private static final double PNEUMATICS_WAIT = 1.3;

    //Todo: Get the actual values for the lengths for the arm
    private static final double ARM_RETRACTED_LENGTH = 1.0;

    private static final double ARM_MIDDLE_LENGTH = 1.0;

    private static final double ARM_EXTENDED_LENGTH = 1.0;

    private final DoubleSolenoid m_topPiston;
    private final DoubleSolenoid m_bottomPiston;

    private ArmExtension m_armExtension;

    public ArmExtensionSubsystem() {
        m_topPiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ARM_TOP_PISTON_OUT, Constants.ARM_TOP_PISTON_IN);
        m_bottomPiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ARM_BOTTOM_PISTON_FORWARD, Constants.ARM_BOTTOM_PISTON_REVERSE);
        fullRetract();
    }

    public ArmExtension getArmExtension() {
        return m_armExtension;
    }

    public boolean isMiddleRetract() {
        return m_armExtension == ArmExtension.MIDDLE_RETRACT;
    }

    public boolean isFullRetract() {
        return m_armExtension == ArmExtension.FULL_RETRACT;
    }

    public boolean isFullExtend() {
        return m_armExtension == ArmExtension.FULL_EXTEND;
    }

    public final void fullRetract() {
        m_topPiston.set(TOP_PISTON_EXTENDED);
        m_bottomPiston.set(BOTTOM_PISTON_RETRACTED);
        m_armExtension = ArmExtension.FULL_RETRACT;
    }

    public void middleRetract() {
        m_topPiston.set(TOP_PISTON_RETRACTED);
        m_bottomPiston.set(BOTTOM_PISTON_RETRACTED);
        m_armExtension = ArmExtension.MIDDLE_RETRACT;
    }

    public void out() {
        m_topPiston.set(TOP_PISTON_RETRACTED);
        m_bottomPiston.set(BOTTOM_PISTON_EXTENDED);
        m_armExtension = ArmExtension.FULL_EXTEND;
    }

    public double getArmLengthMeters() {
        switch (m_armExtension) {
        case FULL_EXTEND:
            return ARM_EXTENDED_LENGTH;
        case MIDDLE_RETRACT:
            return ARM_MIDDLE_LENGTH;
        case FULL_RETRACT:
        default:
            return ARM_RETRACTED_LENGTH;
        }
    }

    public boolean isBottomPistonIn() {
        return m_bottomPiston.get() == BOTTOM_PISTON_EXTENDED;
    }

    public boolean isTopPistonIn() {
        return m_topPiston.get() == TOP_PISTON_RETRACTED;
    }

    public void setBottomPistonExtended() {
        m_bottomPiston.set(BOTTOM_PISTON_EXTENDED);
    }

    public void setBottomPistonRetracted() {
        m_bottomPiston.set(BOTTOM_PISTON_RETRACTED);
    }

    public void setTopPistonExtended() {
        m_topPiston.set(TOP_PISTON_EXTENDED);
    }

    public void setTopPistonRetracted() {
        m_topPiston.set(TOP_PISTON_RETRACTED);
    }

    ////////////////
    // Checklists
    ////////////////
    public Command createIsArmTopPneumaticMoving(DoubleSupplier pressureSupplier) {
        return new DoubleSolenoidMovesChecklist(this, pressureSupplier, m_topPiston, "Claw: Left Piston");
    }

    public Command createIsArmBottomPneumaticMoving(DoubleSupplier pressureSupplier) {
        return new DoubleSolenoidMovesChecklist(this, pressureSupplier, m_bottomPiston, "Arm: Bottom Piston");
    }

    ///////////////////////
    // Command Factories
    ///////////////////////
    public Command createExtendBottomPistonCommand() {
        return runOnce(this::setBottomPistonExtended).withName("Arm Piston: Bottom Extended");
    }

    public Command createRetractBottomPistonCommand() {
        return runOnce(this::setBottomPistonRetracted).withName("Arm Piston: Bottom Retracted");
    }

    public Command createExtendTopPistonCommand() {
        return runOnce(this::setTopPistonExtended).withName("Arm Piston: Top Extended");
    }

    public Command createRetractTopPistonCommand() {
        return runOnce(this::setTopPistonRetracted).withName("Arm Piston: Top Retracted");
    }

    public Command createFullRetractCommand() {
        return run(this::fullRetract).unless(this::isFullRetract).withTimeout(PNEUMATICS_WAIT).withName("ArmPistonsFullRetract");
    }

    public Command createMiddleExtensionCommand() {
        return run(this::middleRetract).unless(this::isMiddleRetract).withTimeout(PNEUMATICS_WAIT).withName("ArmPistonsMiddleRetract");
    }

    public Command createFullExtensionCommand() {
        return run(this::out).unless(this::isFullExtend).withTimeout(PNEUMATICS_WAIT).withName("ArmPistonsOut");
    }

    public Command createArmToSpecifiedHeightCommand(AutoPivotHeight height, GamePieceType gamePiece) {
        if (height == AutoPivotHeight.HIGH && gamePiece == GamePieceType.CONE) {
            return createFullExtensionCommand();
        }
        else if (height == AutoPivotHeight.HIGH && gamePiece == GamePieceType.CUBE) {
            return createMiddleExtensionCommand();
        }
        else if (height == AutoPivotHeight.MEDIUM && gamePiece == GamePieceType.CONE) {
            return createMiddleExtensionCommand();
        }
        else if (height == AutoPivotHeight.MEDIUM && gamePiece == GamePieceType.CUBE) {
            return createFullRetractCommand();
        }
        else {
            return createFullRetractCommand();
        }
    }
}
