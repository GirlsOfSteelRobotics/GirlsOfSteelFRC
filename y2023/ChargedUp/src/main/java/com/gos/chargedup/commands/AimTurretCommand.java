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
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class AimTurretCommand extends CommandBase {
    private static final Field2d DEBUG_FIELD = new Field2d();

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

    private Transform2d m_turretPos;

    private final ClawAlignedCheck m_clawAlignedCheck;

    private static final double SHIFT_Y_CHASSIS_POS = 1.95; //inches below center - def on blossom, not sure of bubbles
    private static final double SHIFT_X_CHASSIS_POS = .5; //inches left of center

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

        double closestYValue = m_chassisSubsystem.findingClosestNodeY(correctedTarget.getY());
        Translation2d nodePosAbs = new Translation2d(correctedTarget.getX(), closestYValue);

        double currentAngle = m_chassisSubsystem.getPose().getRotation().getDegrees();

        m_currentX = m_chassisSubsystem.getPose().getX();
        m_currentY = m_chassisSubsystem.getPose().getY();

        m_turretPos = new Transform2d(new Translation2d(m_currentX - SHIFT_X_CHASSIS_POS, m_currentY - SHIFT_Y_CHASSIS_POS), new Rotation2d(0, 0));

        double targetAngle = Math.toDegrees(Math.atan2((closestYValue) - m_turretPos.getY(), correctedTarget.getX() - m_turretPos.getX()));
        //double targetAngle = Math.toDegrees(Math.atan2((closestYValue) - m_currentX, correctedTarget.getX() - m_currentY));

        double turretAngle = currentAngle - targetAngle;

        DEBUG_FIELD.setRobotPose(m_chassisSubsystem.getPose());
        DEBUG_FIELD.getObject("AimGoal").setPose(new Pose2d(correctedTarget.getX(), closestYValue, Rotation2d.fromDegrees(0)));

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
