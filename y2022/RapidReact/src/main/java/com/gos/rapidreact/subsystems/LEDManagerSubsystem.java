package com.gos.rapidreact.subsystems;

import com.gos.rapidreact.Constants;
import com.gos.rapidreact.led.LEDAngleToTargetOverOrUnder;
import com.gos.rapidreact.led.LEDBoolean;
import com.gos.rapidreact.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDManagerSubsystem extends SubsystemBase {
    private final IntakeLimelightSubsystem m_intakeLimelight;
    private final ShooterLimelightSubsystem m_shooterLimelight;
    private final CollectorSubsystem m_collector;
    private final ShooterSubsystem m_shooter;
    private final HorizontalConveyorSubsystem m_horizonalConveyor;
    private final VerticalConveyorSubsystem m_verticalConveyor;

    private static final int MAX_INDEX_LED = 60;
    private static final int MIDDLE_INDEX_LED = MAX_INDEX_LED / 2;
    private static final int PORT = Constants.LED;
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;
    private LEDBoolean m_intakeIndexLeft;
    private LEDBoolean m_lowerConveyorIndexLeft;
    private LEDBoolean m_upperConveyorIndexLeft;
    private LEDFlash m_goToCargoLeft;
    private LEDBoolean m_allowableDistancetoHubLeft;
    private LEDBoolean m_shooterAtSpeedLeft;
    private LEDFlash m_readyToShootLeft;
    private LEDAngleToTargetOverOrUnder m_angleToHubLeft;
    private LEDBoolean m_intakeIndexRight;
    private LEDBoolean m_lowerConveyorIndexRight;
    private LEDBoolean m_upperConveyorIndexRight;
    private LEDFlash m_goToCargoRight;
    private LEDBoolean m_allowableDistancetoHubRight;
    private LEDBoolean m_shooterAtSpeedRight;
    private LEDFlash m_readyToShootRight;
    private LEDAngleToTargetOverOrUnder m_angleToHubRight;

    public LEDManagerSubsystem(IntakeLimelightSubsystem intakeLimelightSubsystem, ShooterLimelightSubsystem shooterLimelightSubsystem, CollectorSubsystem collector, ShooterSubsystem shooterSubsystem, HorizontalConveyorSubsystem horizontalConveyorSubsystem, VerticalConveyorSubsystem verticalConveyorSubsystem) {
        m_intakeLimelight = intakeLimelightSubsystem;
        m_shooterLimelight = shooterLimelightSubsystem;
        m_collector = collector;
        m_shooter = shooterSubsystem;
        m_horizonalConveyor = horizontalConveyorSubsystem;
        m_verticalConveyor = verticalConveyorSubsystem;

        m_led = new AddressableLED(PORT);
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_intakeIndexLeft = new LEDBoolean(m_buffer, 0, 2, Color.kOrange, Color.kBlack);
        m_lowerConveyorIndexLeft = new LEDBoolean(m_buffer, 3, 5, Color.kBlue, Color.kBlack);
        m_upperConveyorIndexLeft = new LEDBoolean(m_buffer, 6, 8, Color.kPurple, Color.kBlack);
        m_goToCargoLeft = new LEDFlash(m_buffer, 1, Color.kPurple, 10, 12);
        m_allowableDistancetoHubLeft = new LEDBoolean(m_buffer, 14, 16, Color.kWhite, Color.kBlack);
        m_shooterAtSpeedLeft = new LEDBoolean(m_buffer, 18, 20, Color.kCoral, Color.kBlack);
        m_readyToShootLeft = new LEDFlash(m_buffer, 1, Color.kGreen, 21, 23);
        m_angleToHubLeft = new LEDAngleToTargetOverOrUnder(m_buffer, Color.kRed, 25, 29, 10);

        m_intakeIndexRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 0, MIDDLE_INDEX_LED + 2, Color.kOrange, Color.kBlack);
        m_lowerConveyorIndexRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 3,  MIDDLE_INDEX_LED + 5, Color.kBlue, Color.kBlack);
        m_upperConveyorIndexRight = new LEDBoolean(m_buffer,  MIDDLE_INDEX_LED + 6, MIDDLE_INDEX_LED + 8, Color.kPurple, Color.kBlack);
        m_goToCargoRight = new LEDFlash(m_buffer, 1, Color.kPurple, MIDDLE_INDEX_LED + 10, MIDDLE_INDEX_LED + 12);
        m_allowableDistancetoHubRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 14, MIDDLE_INDEX_LED + 16, Color.kWhite, Color.kBlack);
        m_shooterAtSpeedRight = new LEDBoolean(m_buffer, MIDDLE_INDEX_LED + 18, MIDDLE_INDEX_LED + 20, Color.kCoral, Color.kBlack);
        m_readyToShootRight = new LEDFlash(m_buffer, 1, Color.kGreen, MIDDLE_INDEX_LED + 21, MIDDLE_INDEX_LED + 23);
        m_angleToHubRight = new LEDAngleToTargetOverOrUnder(m_buffer, Color.kRed, MIDDLE_INDEX_LED + 25, MIDDLE_INDEX_LED + 29, 10);

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


    @Override
    public void periodic() {
        clear();
        if (DriverStation.isEnabled()) {
            m_intakeIndexLeft.checkBoolean(m_collector.getIndexSensor()); //TODO: make this longer than the time that it's turned on
            m_intakeIndexRight.checkBoolean(m_collector.getIndexSensor());

            m_lowerConveyorIndexLeft.checkBoolean(m_verticalConveyor.getLowerIndexSensor());
            m_lowerConveyorIndexRight.checkBoolean(m_verticalConveyor.getLowerIndexSensor());

            m_upperConveyorIndexLeft.checkBoolean(m_verticalConveyor.getUpperIndexSensor());
            m_upperConveyorIndexRight.checkBoolean(m_verticalConveyor.getUpperIndexSensor());

            if (m_intakeLimelight.distanceToCargo() < 3) { //3 meters
                m_goToCargoLeft.flash();
                m_goToCargoRight.flash();
            }

            m_allowableDistancetoHubLeft.checkBoolean(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot
            m_allowableDistancetoHubRight.checkBoolean(m_shooterLimelight.getDistanceToHub() < ShooterLimelightSubsystem.MAX_SHOOTING_DISTANCE); //5 meters, change to max ability to shoot

            m_shooterAtSpeedLeft.checkBoolean(m_shooter.isShooterAtSpeed());
            m_shooterAtSpeedRight.checkBoolean(m_shooter.isShooterAtSpeed());

            if (m_shooterLimelight.getDistanceToHub() < 5 && m_shooter.isShooterAtSpeed() && m_shooterLimelight.angleError() < 4) {
                m_readyToShootLeft.flash();
                m_readyToShootRight.flash();
            }

            if (m_shooterLimelight.angleError() > 0) {
                m_angleToHubRight.angleToTarget(m_shooterLimelight.angleError());
            }

            if (m_shooterLimelight.angleError() < 0) {
                m_angleToHubRight.angleToTarget(m_shooterLimelight.angleError());
            }
        }

        m_led.setData(m_buffer);
    }

}
