package com.gos.chargedup.commands;

import com.gos.chargedup.AllianceFlipper;
import com.gos.chargedup.ClawAlignedCheck;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class BaseAutoAimTurretCommand extends CommandBase {
    // TODO get values
    protected static final double SHIFT_Y_CHASSIS_POS = Units.inchesToMeters(.5);
    protected static final double SHIFT_X_CHASSIS_POS = Units.inchesToMeters(-2);
    private static final Transform2d TURRET_TRANSFORM = new Transform2d(new Translation2d(SHIFT_X_CHASSIS_POS, SHIFT_Y_CHASSIS_POS), Rotation2d.fromDegrees(0));

    private static final Field2d DEBUG_FIELD = new Field2d();

    protected final ArmPivotSubsystem m_armSubsystem;
    protected final ChassisSubsystem m_chassisSubsystem;
    protected final TurretSubsystem m_turretSubsystem;
    protected final ClawAlignedCheck m_clawAlignedCheck;
    protected final LEDManagerSubsystem m_ledManagerSubsystem;

    static {
        Shuffleboard.getTab("AutoAimTurretDebug").add(DEBUG_FIELD);
    }

    public BaseAutoAimTurretCommand(ArmPivotSubsystem armSubsystem, ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, LEDManagerSubsystem ledManagerSubsystem, ArmExtensionSubsystem armExtension) {
        m_armSubsystem = armSubsystem;
        m_chassisSubsystem = chassisSubsystem;
        m_turretSubsystem = turretSubsystem;
        m_ledManagerSubsystem = ledManagerSubsystem;
        m_clawAlignedCheck = new ClawAlignedCheck(m_chassisSubsystem, armExtension);

        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsystem, this.m_turretSubsystem);
    }

    protected void runAutoAim(Translation2d baseNodeLocation, double targetPitch) {

        Translation2d correctedTarget = AllianceFlipper.maybeFlip(baseNodeLocation);
        Pose2d turretRotationPoint = m_chassisSubsystem.getPose().transformBy(TURRET_TRANSFORM);

        double currentX = turretRotationPoint.getX();
        double currentY = turretRotationPoint.getY();

        double closestYvalue = m_chassisSubsystem.findingClosestNodeY(correctedTarget.getY());
        Translation2d nodePosAbs = new Translation2d(correctedTarget.getX(), closestYvalue);

        double currentAngle = m_chassisSubsystem.getPose().getRotation().getDegrees();

        double targetAngle = Math.toDegrees(Math.atan2((closestYvalue) - currentY, correctedTarget.getX() - currentX));

        double turretAngle = currentAngle - targetAngle;

        DEBUG_FIELD.setRobotPose(m_chassisSubsystem.getPose());
        DEBUG_FIELD.getObject("AimGoal").setPose(new Pose2d(correctedTarget.getX(), closestYvalue, Rotation2d.fromDegrees(0)));
        DEBUG_FIELD.getObject("TurretRotationPoint").setPose(turretRotationPoint);

        if (turretAngle > 180) {
            turretAngle -= 360;
        } else if (turretAngle < -180) {
            turretAngle += 360;
        }

        m_turretSubsystem.moveTurretToAngleWithPID(turretAngle);

        m_armSubsystem.pivotArmToAngle(targetPitch);

        if (m_clawAlignedCheck.isClawAtPoint(nodePosAbs, turretAbsoluteAngle())) {
            System.out.println("Is claw aligned: " + m_clawAlignedCheck.isClawAtPoint(nodePosAbs, turretAbsoluteAngle()));
            m_ledManagerSubsystem.setClawIsAligned(true);
        } else {
            m_ledManagerSubsystem.setClawIsAligned(false);
        }
    }

    private double turretAbsoluteAngle() {
        return m_chassisSubsystem.getPose().getRotation().getRadians() + Math.toRadians(m_turretSubsystem.getTurretAngleDeg());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_turretSubsystem.stopTurret();
        m_armSubsystem.pivotArmStop();
    }
}
