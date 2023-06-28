package com.gos.chargedup.commands;

import com.gos.chargedup.AllianceFlipper;
import com.gos.chargedup.AutoAimNodePositions;
import com.gos.chargedup.ClawAlignedCheck;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.Supplier;

public class AutoAimChassisToNodeOnTheFly extends CommandBase {

    // TODO get values
    protected static final double SHIFT_Y_CHASSIS_POS = Units.inchesToMeters(.5);
    protected static final double SHIFT_X_CHASSIS_POS = Units.inchesToMeters(-2);

    // note to self: "TURRET"
    private static final Transform2d TURRET_TRANSFORM = new Transform2d(new Translation2d(SHIFT_X_CHASSIS_POS, SHIFT_Y_CHASSIS_POS), Rotation2d.fromDegrees(0));

    // private static final Field2d DEBUG_FIELD = new Field2d();
    // static {
    //     Shuffleboard.getTab("AutoAimTurretDebug").add(DEBUG_FIELD);
    // }

    protected final ArmPivotSubsystem m_armSubsystem;
    protected final ChassisSubsystemInterface m_chassisSubsystem;
    protected final ClawAlignedCheck m_clawAlignedCheck;
    protected final LEDManagerSubsystem m_ledManagerSubsystem;

    private final Supplier<AutoAimNodePositions> m_positionSupplier;

    public AutoAimChassisToNodeOnTheFly(Supplier<AutoAimNodePositions> positionSupplier, ArmPivotSubsystem armSubsystem, ChassisSubsystemInterface chassisSubsystem, LEDManagerSubsystem ledManagerSubsystem, ArmExtensionSubsystem armExtension) {
        m_armSubsystem = armSubsystem;
        m_chassisSubsystem = chassisSubsystem;
        m_ledManagerSubsystem = ledManagerSubsystem;
        m_clawAlignedCheck = new ClawAlignedCheck(m_chassisSubsystem, armExtension);

        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsystem, this.m_chassisSubsystem);

        m_positionSupplier = positionSupplier;
    }

    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void execute() {
        AutoAimNodePositions position = m_positionSupplier.get();
        if (position == null || position == AutoAimNodePositions.NONE) {
            return;
        }

        runAutoAim(position.getBaseLocation());
    }

    protected void runAutoAim(Translation2d baseNodeLocation) {
        Translation2d correctedTarget = AllianceFlipper.maybeFlip(baseNodeLocation);

        double closestYValue = m_chassisSubsystem.findingClosestNodeY(correctedTarget.getY());
        Translation2d nodePosAbs = new Translation2d(correctedTarget.getX(), closestYValue);

        // account for how the "turret" is off center
        Pose2d armRotationPoint = m_chassisSubsystem.getPose().transformBy(TURRET_TRANSFORM);

        double currentX = armRotationPoint.getX();
        double currentY = armRotationPoint.getY();

        double goalAngle = Math.toDegrees(Math.atan2((closestYValue - currentY), (correctedTarget.getX() - currentX)));

        if (m_chassisSubsystem.getPose().getRotation().getDegrees() < 0) {
            goalAngle -= 360;
        }

        m_chassisSubsystem.turnPID(goalAngle);

        if (m_clawAlignedCheck.isClawAtPoint(nodePosAbs, m_chassisSubsystem.getYaw())) {
            m_ledManagerSubsystem.setClawIsAligned(true);
        } else {
            m_ledManagerSubsystem.setClawIsAligned(false);
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassisSubsystem.stop();
    }
}
