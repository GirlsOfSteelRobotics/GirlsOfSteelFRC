package com.gos.rapidreact.subsystems;

import com.gos.rapidreact.Constants;
import com.gos.rapidreact.auto_modes.AutoModeFactory;
import com.gos.rapidreact.led.LEDAngleToTargetOverAndUnder;
import com.gos.rapidreact.led.LEDFlash;
import com.gos.rapidreact.led.LEDPattern;
import com.gos.rapidreact.led.LEDRainbow;
import com.gos.rapidreact.led.mirrored.MirroredLEDBoolean;
import com.gos.rapidreact.led.mirrored.MirroredLEDMovingPixel;
import com.gos.rapidreact.led.mirrored.MirroredLEDPatternLookup;
import com.gos.rapidreact.led.mirrored.MirroredLEDFlash;
import com.gos.rapidreact.led.mirrored.MirroredLEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

@SuppressWarnings({"PMD.TooManyFields", "PMD.UnusedPrivateMethod", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
public class LEDManagerSubsystem extends SubsystemBase {

    // Subsystems
    private final ShooterLimelightSubsystem m_shooterLimelight;
    //private final ShooterLimelightSubsystem m_shooterLimelight;
    //private final CollectorSubsystem m_collector;
    private final ShooterSubsystem m_shooter;
    private final VerticalConveyorSubsystem m_verticalConveyor;
    private final CollectorSubsystem m_collector;

    private static final int MAX_INDEX_LED = 60;
    private static final int PORT = Constants.LED;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    // Patterns
    private final MirroredLEDBoolean m_shooterAtSpeed;
    private final MirroredLEDBoolean m_noLimelight; //show if don't see limelight
    private final MirroredLEDBoolean m_correctShootingDistance;
    private final MirroredLEDFlash m_readyToShoot;

    private final LEDAngleToTargetOverAndUnder m_angleToHub;
    private final MirroredLEDBoolean m_angleToHubReady;

    private final LEDFlash m_readyToHang;
    private final LEDRainbow m_rainbowFullStrip;

    private final MirroredLEDPatternLookup<AutoModeFactory.AutonMode> m_autoMode;

    private final MirroredLEDBoolean m_autoPivotAtAngle; //should be at 90
    private final MirroredLEDBoolean m_autoUpperIndexSensor;


    private final AutoModeFactory m_autoModeFactory;

    public LEDManagerSubsystem(ShooterLimelightSubsystem shooterLimelightSubsystem, ShooterSubsystem shooterSubsystem, CollectorSubsystem collectorSubsystem, VerticalConveyorSubsystem verticalConveyorSubsystem, AutoModeFactory autoModeFactory) {
        m_shooterLimelight = shooterLimelightSubsystem;
        //m_shooterLimelight = shooterLimelightSubsystem;
        //m_collector = collector;
        m_shooter = shooterSubsystem;
        m_verticalConveyor = verticalConveyorSubsystem;
        m_collector = collectorSubsystem;

        m_autoModeFactory = autoModeFactory;

        m_led = new AddressableLED(PORT);
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        // m_intakeIndexLeft = new LEDBoolean(m_buffer, 0, 2, Color.kOrange, Color.kBlack);
        // m_intakeLimitSwitch = new MirroredLEDBoolean(m_buffer, 0, 4, Color.kOrange, Color.kBlack);
        // m_lowerConveyorIndex = new MirroredLEDBoolean(m_buffer, 5, 4, Color.kBlue, Color.kBlack);
        // m_upperConveyorIndex = new MirroredLEDBoolean(m_buffer, 10, 4, Color.kPurple, Color.kBlack);
        // m_shooterAtSpeed = new MirroredLEDBoolean(m_buffer, 15, 4, Color.kCoral, Color.kBlack);
        // m_goToCargoLeft = new MirroredLEDFlash(m_buffer, 20, 4, 1.0, Color.kPurple);
        // m_allowableDistancetoHubLeft = new LEDBoolean(m_buffer, 14, 16, Color.kWhite, Color.kBlack);
        // m_readyToShootLeft = new LEDFlash(m_buffer, 1, Color.kGreen, 21, 23);


        m_shooterAtSpeed = new MirroredLEDBoolean(m_buffer, 0, 10, Color.kGreen);

        m_correctShootingDistance = new MirroredLEDBoolean(m_buffer, 10, 10, Color.kGreen, Color.kYellow);

        m_angleToHub = new LEDAngleToTargetOverAndUnder(m_buffer, 20, 40, Color.kOrange, Color.kOrange, 15.0);
        m_angleToHubReady = new MirroredLEDBoolean(m_buffer, 20, 10, Color.kGreen, Color.kBlack);

        m_noLimelight = new MirroredLEDBoolean(m_buffer, 10, 20, new Color(.3f, 0, 0), Color.kBlack);

        m_readyToShoot = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.25, Color.kGreen);

        m_readyToHang = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.25, Color.kBlue);
        m_rainbowFullStrip = new LEDRainbow(m_buffer, 0, MAX_INDEX_LED);

        m_autoPivotAtAngle = new MirroredLEDBoolean(m_buffer, 25, 4, Color.kPapayaWhip, Color.kBlack);

        Map<AutoModeFactory.AutonMode, LEDPattern> autonColorMap = Map.of(
            AutoModeFactory.AutonMode.DRIVE_OFF_TARMAC, new MirroredLEDSolidColor(m_buffer, 0, 20, Color.kRed),
            AutoModeFactory.AutonMode.ONE_BALL_LOW, new MirroredLEDSolidColor(m_buffer, 0, 20, Color.kOrange),
            AutoModeFactory.AutonMode.ONE_BALL_HIGH, new MirroredLEDFlash(m_buffer, 0, 20, 1.0, Color.kOrange),
            AutoModeFactory.AutonMode.TWO_BALL_LOW, new MirroredLEDSolidColor(m_buffer, 0, 20, Color.kYellow),
            AutoModeFactory.AutonMode.TWO_BALL_HIGH, new MirroredLEDFlash(m_buffer, 0, 20, 1.0, Color.kYellow),
            AutoModeFactory.AutonMode.THREE_BALL_LOW, new MirroredLEDSolidColor(m_buffer, 0, 20, Color.kGreen),
            AutoModeFactory.AutonMode.FOUR_BALL_LOW, new MirroredLEDSolidColor(m_buffer, 0, 20, Color.kBlue),
            AutoModeFactory.AutonMode.FOUR_BALL_HALF_HIGH, new MirroredLEDMovingPixel(m_buffer, 0, 20, Color.kBlue),
            AutoModeFactory.AutonMode.FOUR_BALL_HIGH, new MirroredLEDFlash(m_buffer, 0, 20, 1.0, Color.kBlue),
            AutoModeFactory.AutonMode.FIVE_BALL_LOW, new MirroredLEDSolidColor(m_buffer, 0, 20, Color.kPurple)
        );
        m_autoMode = new MirroredLEDPatternLookup<>(m_buffer, autonColorMap);

        m_autoUpperIndexSensor = new MirroredLEDBoolean(m_buffer, 20, 4, Color.kFuchsia, Color.kBlack);

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
        AutoModeFactory.AutonMode autoMode = m_autoModeFactory.autoModeLightSignal();
        m_autoMode.setKey(autoMode);

        if (m_autoMode.hasKey(autoMode)) {
            m_autoMode.writeLeds();
        }
        else {
            m_rainbowFullStrip.writeLeds();
        }

        m_autoPivotAtAngle.setStateAndWrite(m_collector.getIntakeRightAngleDegrees() > 89);
        m_autoUpperIndexSensor.setStateAndWrite(m_verticalConveyor.getUpperIndexSensor());
    }

    private void enabledPatterns() {

        //m_intakeIndexLeft.setStateAndWrite(m_collector.getIndexSensor());
        //m_intakeIndexRight.setStateAndWrite(m_collector.getIndexSensor());

        //m_intakeLimitSwitch.setStateAndWrite(m_collector.limitSwitchPressed());

        //m_lowerConveyorIndex.setStateAndWrite(m_verticalConveyor.getLowerIndexSensor());

        //m_upperConveyorIndex.setStateAndWrite(m_verticalConveyor.getUpperIndexSensor());

        // m_allowableDistancetoHubLeft.setStateAndWrite(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot
        // m_allowableDistancetoHubRight.setStateAndWrite(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot

        shooterLights();
        hangerLights();
    }

    private void shooterLights() {
        // Set, but don't necessarily write to the strip
        m_angleToHub.angleToTarget(m_shooterLimelight.angleError());
        m_angleToHubReady.setState(m_shooterLimelight.atAcceptableAngle());
        m_correctShootingDistance.setState(m_shooterLimelight.atAcceptableDistance());
        m_noLimelight.setState(!m_shooterLimelight.isVisible());

        // Set and write these to the strip always
        m_shooterAtSpeed.setStateAndWrite(m_shooter.isShooterAtSpeed());

        if (m_shooterLimelight.isVisible()) {
            if (m_shooter.isShooterAtSpeed() && m_shooterLimelight.isReadyToShoot()) {
                m_readyToShoot.writeLeds();
            } else {
                // If we are lined up, make the whole section green
                if (m_shooterLimelight.atAcceptableAngle()) {
                    m_angleToHubReady.writeLeds();
                }
                // If it isn't lined up, show the error
                else {
                    m_angleToHub.writeLeds();
                }
                m_correctShootingDistance.writeLeds();
            }
        } else {
            m_noLimelight.writeLeds();
        }

    }

    private void hangerLights() {
        if (DriverStation.isFMSAttached() && DriverStation.isTeleop()) {
            if (DriverStation.getMatchTime() < 25) {
                m_readyToHang.writeLeds();
            }

            if (DriverStation.getMatchTime() < 10) {
                m_rainbowFullStrip.writeLeds();
            }
        }
    }

}
