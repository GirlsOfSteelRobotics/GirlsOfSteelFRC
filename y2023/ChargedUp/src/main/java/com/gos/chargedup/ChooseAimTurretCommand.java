package com.gos.chargedup;

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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.littletonrobotics.frc2023.FieldConstants;


public class ChooseAimTurretCommand extends CommandBase {

    private static final Field2d DEBUG_FIELD = new Field2d();

    // TODO get values
    private static final double SHIFT_Y_CHASSIS_POS = Units.inchesToMeters(.5);
    private static final double SHIFT_X_CHASSIS_POS = Units.inchesToMeters(-2);
    private static final Transform2d TURRET_TRANSFORM = new Transform2d(new Translation2d(SHIFT_X_CHASSIS_POS, SHIFT_Y_CHASSIS_POS), Rotation2d.fromDegrees(0));

    static {
        Shuffleboard.getTab("Debug").add(DEBUG_FIELD);
    }

    private final ArmPivotSubsystem m_armSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final TurretSubsystem m_turretSubsystem;
    //private final double m_targetPitch;

    private double m_currentX;

    private double m_currentY;

    private final ClawAlignedCheck m_clawAlignedCheck;

    private final LEDManagerSubsystem m_ledManagerSubsystem;

    private final ArmExtensionSubsystem m_armExtension;

    private final SendableChooser<AutoTurretCommands> m_sendable;

    public ChooseAimTurretCommand(ArmPivotSubsystem armSubsystem, ArmExtensionSubsystem armExtension, ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, LEDManagerSubsystem ledManagerSubsystem) {
        m_sendable = new SendableChooser();

        for(AutoTurretCommands turretPos : AutoTurretCommands.values()) {
            m_sendable.addOption(turretPos.name(), turretPos);
        }

        SmartDashboard.putData("Choose Turret Command", m_sendable);

        this.m_armSubsystem = armSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_turretSubsystem = turretSubsystem;
        m_armExtension = armExtension;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsystem, this.m_turretSubsystem);

        m_clawAlignedCheck = new ClawAlignedCheck(m_chassisSubsystem, m_armExtension);
        m_ledManagerSubsystem = ledManagerSubsystem;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        AutoTurretCommands nodePos = m_sendable.getSelected();
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

        Translation2d correctedTarget = AllianceFlipper.maybeFlip(baseTargetLocation);

        Pose2d turretRotationPoint = m_chassisSubsystem.getPose().transformBy(TURRET_TRANSFORM);

        m_currentX = turretRotationPoint.getX();
        m_currentY = turretRotationPoint.getY();

        double closestYvalue = m_chassisSubsystem.findingClosestNodeY(correctedTarget.getY());
        Translation2d nodePosAbs = new Translation2d(correctedTarget.getX(), closestYvalue);

        double currentAngle = m_chassisSubsystem.getPose().getRotation().getDegrees();

        double targetAngle = Math.toDegrees(Math.atan2((closestYvalue) - m_currentY, correctedTarget.getX() - m_currentX));

        double turretAngle = currentAngle - targetAngle;

        DEBUG_FIELD.setRobotPose(m_chassisSubsystem.getPose());
        DEBUG_FIELD.getObject("AimGoal").setPose(new Pose2d(correctedTarget.getX(), closestYvalue, Rotation2d.fromDegrees(0)));
        DEBUG_FIELD.getObject("TurretRotationPoint").setPose(turretRotationPoint);

        if (turretAngle > 180) {
            turretAngle -= 360;
        }
        else if (turretAngle < -180) {
            turretAngle += 360;
        }

        m_turretSubsystem.moveTurretToAngleWithPID(turretAngle);

        m_armSubsystem.pivotArmToAngle(targetPitch);


        if (m_clawAlignedCheck.isClawAtPoint(nodePosAbs, turretAbsoluteAngle())) {
            System.out.println("Is claw aligned: " + m_clawAlignedCheck.isClawAtPoint(nodePosAbs, turretAbsoluteAngle()));
            m_ledManagerSubsystem.setClawIsAligned(true);
        }
        else {
            m_ledManagerSubsystem.setClawIsAligned(false);
        }

    }

    private double turretAbsoluteAngle() {
        return m_chassisSubsystem.getPose().getRotation().getRadians() + Math.toRadians(m_turretSubsystem.getTurretAngleDeg());
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_turretSubsystem.stopTurret();
        m_armSubsystem.pivotArmStop();
    }
}
