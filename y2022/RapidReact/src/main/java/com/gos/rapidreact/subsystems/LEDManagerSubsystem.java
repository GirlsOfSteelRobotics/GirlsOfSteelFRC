package com.gos.rapidreact.subsystems;

import com.gos.rapidreact.Constants;
import com.gos.rapidreact.auto_modes.AutoModeFactory;
import com.gos.rapidreact.led.LEDAngleToTargetOverOrUnder;
import com.gos.rapidreact.led.LEDFlash;
import com.gos.rapidreact.led.LEDRainbow;
import com.gos.rapidreact.led.mirrored.MirroredLEDBoolean;
import com.gos.rapidreact.led.mirrored.MirroredLEDColorLookup;
import com.gos.rapidreact.led.mirrored.MirroredLEDDistanceToTarget;
import com.gos.rapidreact.led.mirrored.MirroredLEDFlash;
import com.gos.rapidreact.led.mirrored.MirroredLEDRainbow;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

@SuppressWarnings("PMD.TooManyFields")
public class LEDManagerSubsystem extends SubsystemBase {
    private final IntakeLimelightSubsystem m_intakeLimelight;
    //private final ShooterLimelightSubsystem m_shooterLimelight;
    //private final CollectorSubsystem m_collector;
    private final ShooterSubsystem m_shooter;
    private final VerticalConveyorSubsystem m_verticalConveyor;
    private final CollectorSubsystem m_collector;

    private static final int MAX_INDEX_LED = 60;
    private static final int MIDDLE_INDEX_LED = MAX_INDEX_LED / 2 - 1;
    private static final int PORT = Constants.LED;
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;
    private final MirroredLEDBoolean m_intakeLimitSwitch;
    private final MirroredLEDBoolean m_lowerConveyorIndex;
    private final MirroredLEDBoolean m_upperConveyorIndex;
    private final MirroredLEDFlash m_goToCargoLeft;
    private final MirroredLEDBoolean m_shooterAtSpeed;

    private final LEDAngleToTargetOverOrUnder m_angleToCargoLeft;
    private final LEDAngleToTargetOverOrUnder m_angleToCargoRight;

    private final LEDFlash m_readyToHang;
    private final LEDRainbow m_rainbowFullStrip;

    private final LEDAngleToTargetOverOrUnder m_autoCheckAngleLeft;
    private final LEDAngleToTargetOverOrUnder m_autoCheckAngleRight;
    private final MirroredLEDRainbow m_autoCorrectAngle;

    private final MirroredLEDDistanceToTarget m_autoCheckDistance;
    private final MirroredLEDRainbow m_autoCorrectDistance;
    private final MirroredLEDColorLookup m_autoMode;

    private final MirroredLEDBoolean m_autoPivotAtAngle; //should be at 90
    private final MirroredLEDBoolean m_autoUpperIndexSensor;


    private final AutoModeFactory m_autoModeFactory;

    public LEDManagerSubsystem(IntakeLimelightSubsystem intakeLimelightSubsystem, ShooterSubsystem shooterSubsystem, CollectorSubsystem collectorSubsystem, VerticalConveyorSubsystem verticalConveyorSubsystem, AutoModeFactory autoModeFactory) {
        m_intakeLimelight = intakeLimelightSubsystem;
        //m_shooterLimelight = shooterLimelightSubsystem;
        //m_collector = collector;
        m_shooter = shooterSubsystem;
        m_verticalConveyor = verticalConveyorSubsystem;
        m_collector = collectorSubsystem;

        m_autoModeFactory = autoModeFactory;

        m_led = new AddressableLED(PORT);
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        // m_intakeIndexLeft = new LEDBoolean(m_buffer, 0, 2, Color.kOrange, Color.kBlack);
        m_intakeLimitSwitch = new MirroredLEDBoolean(m_buffer, 0, 4, Color.kOrange, Color.kBlack);
        m_lowerConveyorIndex = new MirroredLEDBoolean(m_buffer, 5, 4, Color.kBlue, Color.kBlack);
        m_upperConveyorIndex = new MirroredLEDBoolean(m_buffer, 10, 4, Color.kPurple, Color.kBlack);
        m_shooterAtSpeed = new MirroredLEDBoolean(m_buffer, 15, 4, Color.kCoral, Color.kBlack);
        m_goToCargoLeft = new MirroredLEDFlash(m_buffer, 20, 4, 1.0, Color.kPurple);
        // m_allowableDistancetoHubLeft = new LEDBoolean(m_buffer, 14, 16, Color.kWhite, Color.kBlack);
        // m_readyToShootLeft = new LEDFlash(m_buffer, 1, Color.kGreen, 21, 23);

        m_angleToCargoLeft = new LEDAngleToTargetOverOrUnder(m_buffer, 25, 30, Color.kRed, 15.0);
        m_angleToCargoRight = new LEDAngleToTargetOverOrUnder(m_buffer, MIDDLE_INDEX_LED + 25, MIDDLE_INDEX_LED + 30, Color.kRed, 15.0);

        m_readyToHang = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.25, Color.kGreen);
        m_rainbowFullStrip = new LEDRainbow(m_buffer, 0, MAX_INDEX_LED);

        m_autoCheckAngleLeft = new LEDAngleToTargetOverOrUnder(m_buffer, 25, 30, Color.kRed, 5);
        m_autoCheckAngleRight = new LEDAngleToTargetOverOrUnder(m_buffer, MIDDLE_INDEX_LED + 25, MIDDLE_INDEX_LED + 30, Color.kRed, 5.0);
        m_autoCorrectAngle = new MirroredLEDRainbow(m_buffer, 30, 4);

        m_autoCheckDistance = new MirroredLEDDistanceToTarget(m_buffer, 20, 4, Color.kWhite, Units.feetToMeters(1));
        m_autoCorrectDistance = new MirroredLEDRainbow(m_buffer, 20, 4);

        m_autoPivotAtAngle = new MirroredLEDBoolean(m_buffer, 25, 4, Color.kPapayaWhip, Color.kBlack);

        Map<Integer, Color8Bit> autonColorMap = Map.of(
            0, new Color8Bit(Color.kRed),
            1, new Color8Bit(Color.kOrange),
            2, new Color8Bit(Color.kYellow),
            3, new Color8Bit(Color.kGreen),
            4, new Color8Bit(Color.kBlue),
            5, new Color8Bit(Color.kPurple)
        );
        m_autoMode = new MirroredLEDColorLookup(m_buffer, 0, 20, autonColorMap);

        m_autoUpperIndexSensor = new MirroredLEDBoolean(m_buffer, 20, 25, Color.kFuchsia, Color.kBlack);

        m_led.setLength(m_buffer.getLength());

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
    }

    public void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }
    }


    @SuppressWarnings({"PMD"})
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Clock Time", DriverStation.getMatchTime());
        clear();

        if (DriverStation.isDisabled()) {
            disabledPatterns();
        }
        else {
            enabledPatterns();
        }

        m_led.setData(m_buffer);
    }

    private void disabledPatterns() {
        int autoMode = m_autoModeFactory.autoModeLightSignal();

        if (m_autoMode.hasKey(autoMode)) {
            m_autoMode.setKey(autoMode);
        }
        else {
            m_rainbowFullStrip.rainbow();
        }

        m_autoPivotAtAngle.checkBoolean(m_collector.getIntakeRightAngleDegrees() > 89);
        m_autoUpperIndexSensor.checkBoolean(m_verticalConveyor.getUpperIndexSensor());
    }

    private void enabledPatterns() {

        //m_intakeIndexLeft.checkBoolean(m_collector.getIndexSensor());
        //m_intakeIndexRight.checkBoolean(m_collector.getIndexSensor());

        m_intakeLimitSwitch.checkBoolean(m_collector.limitSwitchPressed());

        m_lowerConveyorIndex.checkBoolean(m_verticalConveyor.getLowerIndexSensor());

        m_upperConveyorIndex.checkBoolean(m_verticalConveyor.getUpperIndexSensor());

        if (m_intakeLimelight.distanceToCargo() < 3) { //3 meters
            m_goToCargoLeft.flash();
        }

        // m_allowableDistancetoHubLeft.checkBoolean(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot
        // m_allowableDistancetoHubRight.checkBoolean(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot

        m_shooterAtSpeed.checkBoolean(m_shooter.isShooterAtSpeed());

        //if (m_shooterLimelight.getDistanceToHub() < 5 && m_shooter.isShooterAtSpeed() && m_shooterLimelight.angleError() < 4) {
        // m_readyToShootLeft.flash();
        //  m_readyToShootRight.flash();
        //}

        if (m_intakeLimelight.getAngle() > 0 && m_intakeLimelight.isVisible()) {
            m_angleToCargoLeft.angleToTarget(m_intakeLimelight.getAngle());
        }

        if (m_intakeLimelight.getAngle() < 0 && m_intakeLimelight.isVisible()) {
            m_angleToCargoRight.angleToTarget(m_intakeLimelight.getAngle());
        }

        if (RobotBase.isReal()) {
            if (DriverStation.getMatchTime() < 25) {
                m_readyToHang.flash();
            }

            if (DriverStation.getMatchTime() < 10) {
                m_rainbowFullStrip.rainbow();
            }
        }
    }

    private void autoCorrectAngleAndDistance() {
        if (m_intakeLimelight.isVisible()) {
            double distanceToCargoAuto;
            double angleToCargoAuto;

            double distAllowableError = Units.inchesToMeters(3);
            double angleAllowableError = Units.degreesToRadians(2);

            distanceToCargoAuto = m_intakeLimelight.distanceToCargo();
            angleToCargoAuto = m_intakeLimelight.getAngle();

            if (distAllowableError < Math.abs(distanceToCargoAuto)) {
                m_autoCheckDistance.distanceToTarget(distanceToCargoAuto);
            }

            if (angleAllowableError < Math.abs(angleToCargoAuto)) {
                m_autoCheckAngleLeft.angleToTarget(angleToCargoAuto);
                m_autoCheckAngleRight.angleToTarget(angleToCargoAuto);
            }

            if (distAllowableError > Math.abs(distanceToCargoAuto)) {
                m_autoCorrectDistance.rainbow();
            }

            if (angleAllowableError > Math.abs(angleToCargoAuto)) {
                m_autoCorrectAngle.rainbow();
            }
        }
    }

}
