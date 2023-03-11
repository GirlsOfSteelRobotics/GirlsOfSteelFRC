package com.gos.chargedup.commands;

import com.gos.chargedup.AllianceFlipper;
import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.ClawAlignedCheck;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.LEDManagerSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class AimTurretCommand extends CommandBase {
    private static final Field2d DEBUG_FIELD = new Field2d();

    // TODO get values
    private static final double SHIFT_Y_CHASSIS_POS = Units.inchesToMeters(.5);
    private static final double SHIFT_X_CHASSIS_POS = Units.inchesToMeters(-6);
    private static final Transform2d TURRET_TRANSFORM = new Transform2d(new Translation2d(SHIFT_X_CHASSIS_POS, SHIFT_Y_CHASSIS_POS), Rotation2d.fromDegrees(0));

    static {
        Shuffleboard.getTab("Debug").add(DEBUG_FIELD);
    }

    private final ArmPivotSubsystem m_armSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final TurretSubsystem m_turretSubsystem;
    private final Translation2d m_baseTargetLocation;
    private final double m_targetPitch;

    private double m_currentX;

    private double m_currentY;

    private final ClawAlignedCheck m_clawAlignedCheck;

    private final LEDManagerSubsystem m_ledManagerSubsystem;

    private final ArmExtensionSubsystem m_armExtension;


    public AimTurretCommand(ArmPivotSubsystem armSubsystem, ArmExtensionSubsystem armExtension, ChassisSubsystem chassisSubsystem, TurretSubsystem turretSubsystem, Translation2d targetPos, String position, GamePieceType gamePiece, AutoPivotHeight height, LEDManagerSubsystem ledManagerSubsystem) {
        setName("Score " + gamePiece + " " + position + " " + height);
        this.m_armSubsystem = armSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_turretSubsystem = turretSubsystem;
        m_armExtension = armExtension;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsystem, this.m_turretSubsystem);

        m_baseTargetLocation = targetPos;

        m_targetPitch = armSubsystem.getArmAngleForScoring(height, gamePiece);

        m_clawAlignedCheck = new ClawAlignedCheck(m_chassisSubsystem, m_armExtension);
        m_ledManagerSubsystem = ledManagerSubsystem;

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        Translation2d correctedTarget = AllianceFlipper.maybeFlip(m_baseTargetLocation);

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

        m_armSubsystem.pivotArmToAngle(m_targetPitch);


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
