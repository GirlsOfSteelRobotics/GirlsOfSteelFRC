package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.AllianceFlipper;
import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.RobotExtrinsics;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.gos.lib.GetAllianceUtil;
import com.gos.lib.properties.GosBooleanProperty;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class FeedPiecesWithVision extends Command {
    private static final GosBooleanProperty USE_DEBUG_VELOCITY = new GosBooleanProperty(false, "Feeding: Use Debug Velocity", false);
    private static final GosDoubleProperty TUNING_X_VELOCITY = new GosDoubleProperty(false, "Feeding: Debug x-velocity", -2);
    private static final GosDoubleProperty TUNING_Y_VELOCITY = new GosDoubleProperty(false, "Feeding: Debug y-velocity", .5);

    private static final double BLUE_MIN_X_METERS = 10.2;
    private static final double RED_MAX_X_METERS = FieldConstants.FIELD_LENGTH - BLUE_MIN_X_METERS;

    private final CommandXboxController m_driverController;
    private final ArmPivotSubsystem m_armPivotSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;

    private boolean m_runIntake;


    private final InterpolatingDoubleTreeMap m_distanceToAngleTable = new InterpolatingDoubleTreeMap();
    private final InterpolatingDoubleTreeMap m_distanceToRPMTable = new InterpolatingDoubleTreeMap();
    private final InterpolatingDoubleTreeMap m_rpmToCurveOffsetTable = new InterpolatingDoubleTreeMap();

    public FeedPiecesWithVision(
        CommandXboxController driverController,
        ChassisSubsystem chassisSubsystem,
        ArmPivotSubsystem armPivotSubsystem,
        ShooterSubsystem shooterSubsystem,
        IntakeSubsystem intakeSubsystem) {
        this.m_driverController = driverController;
        this.m_armPivotSubsystem = armPivotSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;

        m_distanceToAngleTable.put(10.5, 31.0);
        m_distanceToRPMTable.put(10.5, 5200.0);
        m_rpmToCurveOffsetTable.put(5200.0, 17.0);

        m_distanceToAngleTable.put(9.2, 12.0);
        m_distanceToRPMTable.put(9.2, 4200.0);
        m_rpmToCurveOffsetTable.put(4200.0, 20.0);

        m_distanceToAngleTable.put(7.1, 12.0);
        m_distanceToRPMTable.put(7.1, 3800.0);
        m_rpmToCurveOffsetTable.put(3800.0, 30.0);

        m_distanceToAngleTable.put(6.36, 32.0);
        m_distanceToRPMTable.put(6.36, 3504.0);
        m_rpmToCurveOffsetTable.put(3504.0, 22.0);
    }

    @Override
    public void initialize() {
        m_runIntake = false;
    }

    @Override
    public void execute() {
        Pose2d aimingPoint = AllianceFlipper.maybeFlip(RobotExtrinsics.FULL_FIELD_FEEDING_AIMING_POINT);

        double leftY = -MathUtil.applyDeadband(m_driverController.getLeftY(), BaseTeleopSwerve.JOYSTICK_DEADBAND);
        double leftX = -MathUtil.applyDeadband(m_driverController.getLeftX(), BaseTeleopSwerve.JOYSTICK_DEADBAND);

        double chassisXVel;
        double chassisYVel;
        if (USE_DEBUG_VELOCITY.getValue()) {
            chassisXVel = TUNING_X_VELOCITY.getValue();
            chassisYVel = TUNING_Y_VELOCITY.getValue();
        } else {
            chassisXVel = leftY * ChassisSubsystem.MAX_TRANSLATION_SPEED;
            chassisYVel = leftX * ChassisSubsystem.MAX_TRANSLATION_SPEED;
        }

        Pose2d pose = m_chassisSubsystem.getFuturePose(.2);

        double distanceToFeed = m_chassisSubsystem.getDistanceToFeeder(pose);
        double rpm = m_distanceToRPMTable.get(distanceToFeed);
        double armAngle = m_distanceToAngleTable.get(distanceToFeed);
        double curveOffset = m_rpmToCurveOffsetTable.get(rpm);

        // double armAngle = ArmPivotSubsystem.ARM_FEEDER_ANGLE.getValue();
        // double RPM = ShooterSubsystem.SHOOT_NOTE_TO_ALLIANCE_RPM.getValue();
        // double curveOffset = ChassisSubsystem.SHOOTER_ARC_CORRECTION.getValue();

        m_chassisSubsystem.turnButtToFacePoint(
            pose,
            aimingPoint,
            chassisXVel,
            chassisYVel,
            curveOffset);
        m_armPivotSubsystem.moveArmToAngle(armAngle);
        m_shooterSubsystem.setPidRpm(rpm);

        boolean mechReady = m_armPivotSubsystem.isArmAtGoal() && m_shooterSubsystem.isShooterAtGoal() && m_chassisSubsystem.isAngleAtGoal();

        boolean distanceReady;
        if (GetAllianceUtil.isBlueAlliance()) {
            distanceReady = m_chassisSubsystem.getPose().getX() < BLUE_MIN_X_METERS;
        } else {
            distanceReady = m_chassisSubsystem.getPose().getX() > RED_MAX_X_METERS;
        }

        boolean readyToShoot = mechReady && distanceReady;
        if (readyToShoot) {
            m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 1);
            m_runIntake = true;
        }

        if (m_runIntake) {
            m_intakeSubsystem.intakeIn();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_driverController.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 0);

        m_armPivotSubsystem.stopArmMotor();
        m_shooterSubsystem.stopShooter();
        m_intakeSubsystem.intakeStop();
        m_chassisSubsystem.setChassisSpeed(new ChassisSpeeds());
    }
}
