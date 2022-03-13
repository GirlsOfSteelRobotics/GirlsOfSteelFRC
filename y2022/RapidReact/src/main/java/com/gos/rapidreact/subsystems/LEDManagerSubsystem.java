package com.gos.rapidreact.subsystems;

import com.gos.rapidreact.Constants;
import com.gos.rapidreact.led.LEDDistanceToTarget;
import com.gos.rapidreact.led.LEDFlash;
import com.gos.rapidreact.led.LEDRainbow;
import com.gos.rapidreact.led.LEDBoolean;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("PMD.TooManyFields")
public class LEDManagerSubsystem extends SubsystemBase {
    private final IntakeLimelightSubsystem m_intakeLimelight;
    private final ShooterLimelightSubsystem m_shooterLimelight;
    private final CollectorSubsystem m_collector;
    private final ShooterSubsystem m_shooter;
    private final VerticalConveyorSubsystem m_verticalConveyor;

    private static final int MAX_INDEX_LED = 60;
    private static final int MIDDLE_INDEX_LED = MAX_INDEX_LED / 2;
    private static final int PORT = Constants.LED;
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;
    // private final LEDBoolean m_intakeIndexLeft;
    private final LEDBoolean m_lowerConveyorIndexLeft;
    private final LEDBoolean m_upperConveyorIndexLeft;
    private final LEDFlash m_goToCargoLeft;
    // private final LEDBoolean m_allowableDistancetoHubLeft;
    private final LEDBoolean m_shooterAtSpeedLeft;
    // private final LEDFlash m_readyToShootLeft;
    private final LEDAngleToTargetOverOrUnder m_angleToCargoLeft;

    // private final LEDBoolean m_intakeIndexRight;
    private final LEDBoolean m_lowerConveyorIndexRight;
    private final LEDBoolean m_upperConveyorIndexRight;
    private final LEDFlash m_goToCargoRight;
    // private final LEDBoolean m_allowableDistancetoHubRight;
    private final LEDBoolean m_shooterAtSpeedRight;
    // private final LEDFlash m_readyToShootRight;
    private final LEDAngleToTargetOverOrUnder m_angleToCargoRight;

    private final LEDFlash m_readyToHang;
    private final LEDRainbow m_rainbow;

    private final LEDAngleToTargetOverOrUnder m_autoCheckAngleLeft;
    private final LEDAngleToTargetOverOrUnder m_autoCheckAngleRight;
    private final LEDRainbow m_autoCorrectAngleLeft;
    private final LEDRainbow m_autoCorrectAngleRight;

    private final LEDDistanceToTarget m_autoCheckDistanceLeft;
    private final LEDDistanceToTarget m_autoCheckDistanceRight;
    private final LEDRainbow m_autoCorrectDistanceLeft;
    private final LEDRainbow m_autoCorrectDistanceRight;
    // private final LEDBase m_autoMode;



    public LEDManagerSubsystem(IntakeLimelightSubsystem intakeLimelightSubsystem, ShooterLimelightSubsystem shooterLimelightSubsystem, CollectorSubsystem collector, ShooterSubsystem shooterSubsystem, VerticalConveyorSubsystem verticalConveyorSubsystem) {
        m_intakeLimelight = intakeLimelightSubsystem;
        m_shooterLimelight = shooterLimelightSubsystem;
        m_collector = collector;
        m_shooter = shooterSubsystem;
        m_verticalConveyor = verticalConveyorSubsystem;

        m_led = new AddressableLED(PORT);
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        // m_intakeIndexLeft = new LEDBoolean(m_buffer, 0, 2, Color.kOrange, Color.kBlack);
        m_lowerConveyorIndexLeft = new LEDBoolean(m_buffer, 3, 5, Color.kBlue, Color.kBlack);
        m_upperConveyorIndexLeft = new LEDBoolean(m_buffer, 6, 8, Color.kPurple, Color.kBlack);
        m_goToCargoLeft = new LEDFlash(m_buffer, 1, Color.kPurple, 10, 12);
        // m_allowableDistancetoHubLeft = new LEDBoolean(m_buffer, 14, 16, Color.kWhite, Color.kBlack);
        m_shooterAtSpeedLeft = new LEDBoolean(m_buffer, 18, 20, Color.kCoral, Color.kBlack);
        // m_readyToShootLeft = new LEDFlash(m_buffer, 1, Color.kGreen, 21, 23);
        m_angleToCargoLeft = new LEDAngleToTargetOverOrUnder(m_buffer, Color.kRed, 25, 29, 15);

        // m_intakeIndexRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 0, MIDDLE_INDEX_LED + 2, Color.kOrange, Color.kBlack);
        m_lowerConveyorIndexRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 3,  MIDDLE_INDEX_LED + 5, Color.kBlue, Color.kBlack);
        m_upperConveyorIndexRight = new LEDBoolean(m_buffer,  MIDDLE_INDEX_LED + 6, MIDDLE_INDEX_LED + 8, Color.kPurple, Color.kBlack);
        m_goToCargoRight = new LEDFlash(m_buffer, 1, Color.kPurple, MIDDLE_INDEX_LED + 10, MIDDLE_INDEX_LED + 12);
        // m_allowableDistancetoHubRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 14, MIDDLE_INDEX_LED + 16, Color.kWhite, Color.kBlack);
        m_shooterAtSpeedRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 18, MIDDLE_INDEX_LED + 20, Color.kCoral, Color.kBlack);
        // m_readyToShootRight = new LEDFlash(m_buffer, 1, Color.kGreen, MIDDLE_INDEX_LED + 21, MIDDLE_INDEX_LED + 23);
        m_angleToCargoRight = new LEDAngleToTargetOverOrUnder(m_buffer, Color.kRed, MIDDLE_INDEX_LED + 25, MIDDLE_INDEX_LED + 29, 15);

        m_readyToHang = new LEDFlash(m_buffer, 1, Color.kGreen, MIDDLE_INDEX_LED + 0, MAX_INDEX_LED);
        m_rainbow = new LEDRainbow(MAX_INDEX_LED, m_buffer, 0);

        m_autoCheckAngleLeft = new LEDAngleToTargetOverOrUnder(m_buffer, Color.kRed, 25, 29, 5);
        m_autoCheckAngleRight = new LEDAngleToTargetOverOrUnder(m_buffer, Color.kRed, MIDDLE_INDEX_LED + 25, MIDDLE_INDEX_LED + 29, 5);
        m_autoCorrectAngleLeft = new LEDRainbow(29, m_buffer, 25);
        m_autoCorrectAngleRight = new LEDRainbow(MIDDLE_INDEX_LED + 29, m_buffer, MIDDLE_INDEX_LED + 25);


        m_autoCheckDistanceLeft = new LEDDistanceToTarget(m_buffer, Color.kWhite, 20, 24, Units.feetToMeters(1));
        m_autoCheckDistanceRight = new LEDDistanceToTarget(m_buffer, Color.kWhite, MIDDLE_INDEX_LED + 20, MIDDLE_INDEX_LED + 24, Units.feetToMeters(1));
        m_autoCorrectDistanceLeft = new LEDRainbow(20, m_buffer, 24);
        m_autoCorrectDistanceRight = new LEDRainbow(MIDDLE_INDEX_LED + 20, m_buffer, MIDDLE_INDEX_LED + 24);





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


    @SuppressWarnings("PMD.CyclomaticComplexity")
    @Override
    public void periodic() {
        clear();
        double distanceToCargoAuto;
        double angleToCargoAuto;

        double distAllowableError = Units.inchesToMeters(3);
        double angleAllowableError = Units.degreesToRadians(2);

        if (DriverStation.isAutonomous()) {
            if (m_intakeLimelight.isVisible()) {
                distanceToCargoAuto = m_intakeLimelight.distanceToCargo();
                angleToCargoAuto = m_intakeLimelight.getAngle();

                if (!(distAllowableError > Math.abs(distanceToCargoAuto))) {
                    m_autoCheckDistanceLeft.distanceToTarget(distanceToCargoAuto);
                }

                if (!(angleAllowableError > Math.abs(angleToCargoAuto))) {
                    m_autoCheckAngleLeft.angleToTarget(angleToCargoAuto);
                }

                if (distAllowableError > Math.abs(distanceToCargoAuto)) {
                    m_autoCorrectDistanceLeft.rainbow();
                    m_autoCorrectDistanceRight.rainbow();
                }

                if (angleAllowableError > Math.abs(angleToCargoAuto)) {
                    m_autoCorrectAngleLeft.rainbow();
                    m_autoCorrectDistanceLeft.rainbow();
                }
            }
            else {
                m_rainbow.rainbow();
            }
        }

        if (DriverStation.isDisabled() && !DriverStation.isAutonomous()) {
            m_rainbow.rainbow();
        }

        if (DriverStation.isEnabled()) {
            //m_intakeIndexLeft.checkBoolean(m_collector.getIndexSensor());
            //m_intakeIndexRight.checkBoolean(m_collector.getIndexSensor());

            m_lowerConveyorIndexLeft.checkBoolean(m_verticalConveyor.getLowerIndexSensor());
            m_lowerConveyorIndexRight.checkBoolean(m_verticalConveyor.getLowerIndexSensor());

            m_upperConveyorIndexLeft.checkBoolean(m_verticalConveyor.getUpperIndexSensor());
            m_upperConveyorIndexRight.checkBoolean(m_verticalConveyor.getUpperIndexSensor());

            if (m_intakeLimelight.distanceToCargo() < 3) { //3 meters
                m_goToCargoLeft.flash();
                m_goToCargoRight.flash();
            }

            // m_allowableDistancetoHubLeft.checkBoolean(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot
            // m_allowableDistancetoHubRight.checkBoolean(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot

            m_shooterAtSpeedLeft.checkBoolean(m_shooter.isShooterAtSpeed());
            m_shooterAtSpeedRight.checkBoolean(m_shooter.isShooterAtSpeed());

//            if (m_shooterLimelight.getDistanceToHub() < 5 && m_shooter.isShooterAtSpeed() && m_shooterLimelight.angleError() < 4) {
//                m_readyToShootLeft.flash();
//                m_readyToShootRight.flash();
//            }

            if (m_intakeLimelight.getAngle() > 0 && m_intakeLimelight.isVisible()) {
                m_angleToCargoLeft.angleToTarget(m_intakeLimelight.getAngle());
            }

            if (m_intakeLimelight.getAngle() < 0 && m_intakeLimelight.isVisible()) {
                m_angleToCargoRight.angleToTarget(m_intakeLimelight.getAngle());
            }

            if (DriverStation.getMatchTime() > 135) {
                m_readyToHang.flash();
            }
        }

        m_led.setData(m_buffer);
    }

}
