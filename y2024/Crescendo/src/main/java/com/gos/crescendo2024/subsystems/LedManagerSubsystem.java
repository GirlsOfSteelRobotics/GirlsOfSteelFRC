package com.gos.crescendo2024.subsystems;


import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.led_patterns.AlertPatterns;
import com.gos.crescendo2024.led_patterns.AutoModePattern;
import com.gos.crescendo2024.led_patterns.AutoPattern;
import com.gos.crescendo2024.led_patterns.HasPiecePattern;
import com.gos.crescendo2024.led_patterns.TeleopPattern;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.mirrored.MirroredLEDFlash;
import com.gos.lib.led.mirrored.MirroredLEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.frc2023.util.Alert;

import java.util.HashMap;
import java.util.Map;

public class LedManagerSubsystem extends SubsystemBase {

    private static final int MAX_INDEX_LED = 30;

    private final IntakeSubsystem m_intakeSubsystem;

    private final Autos m_autoModeFactory;

    private final Map<Autos.AutoModes, LEDPattern> m_autonColorMap;
    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    private final TeleopPattern m_teleopPattern;
    private final HasPiecePattern m_hasPiecePattern;
    private final AutoPattern m_autoPattern;

    private final AlertPatterns m_alert;

    private final AutoModePattern m_autoModePattern;



    public LedManagerSubsystem(IntakeSubsystem intakeSubsystem, Autos autoModeFactory) {
        m_autoModeFactory = autoModeFactory;
        m_intakeSubsystem = intakeSubsystem;
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT);

        m_led.setLength(m_buffer.getLength());

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();

        m_teleopPattern = new TeleopPattern(MAX_INDEX_LED, m_buffer);
        m_hasPiecePattern = new HasPiecePattern(MAX_INDEX_LED, m_buffer);
        m_autoPattern = new AutoPattern(MAX_INDEX_LED, m_buffer);
        m_alert = new AlertPatterns(MAX_INDEX_LED, m_buffer);
        m_autonColorMap = createAutonMap();
        m_autoModePattern = new AutoModePattern(m_autonColorMap, m_buffer);
    }
    public Map<Autos.AutoModes, LEDPattern> createAutonMap () {
        Map<Autos.AutoModes, LEDPattern> autonMap = new HashMap<>();
        autonMap.put(Autos.AutoModes.LEAVE_WING, new MirroredLEDSolidColor(m_buffer, 0, MAX_INDEX_LED, Color.kDenim));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_LEAVE_WING, new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 1.0, Color.kGreen));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_CENTER_LINE, new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 1.0, Color.kYellow));
        autonMap.put(Autos.AutoModes.FOUR_NOTE, new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 1.0, Color.kRed));
        return autonMap;
    }
    public void enabledPatterns() {
        if (DriverStation.isTeleop()) {
            m_teleopPattern.writeLED();
        } else if (DriverStation.isAutonomous()) {
            m_autoPattern.writeAutoPattern();
        }

        if (m_intakeSubsystem.hasGamePiece()) {
            m_hasPiecePattern.writeHasPiecePattern();
            m_hasPiecePattern.incrementCycles();
        }
        else {
            m_hasPiecePattern.resetCycles();
        }
    }

    public void disabledPatterns() {
        Autos.AutoModes autoMode = m_autoModeFactory.autoModeLightSignal();
        m_autoModePattern.writeAutoModePattern(autoMode);
        if (Alert.hasErrors()) {
            m_alert.writeAlertErrorPattern();
        } else if (Alert.hasWarnings()) {
            m_alert.writeAlertWarningPattern();
        } else {
            m_alert.writeNoAlertsPattern();
        }

    }


    @Override
    public void periodic() {
        clear();

        if (DriverStation.isEnabled()) {
            enabledPatterns();
        } else {
            disabledPatterns();
        }
        // driverPracticePatterns();
        m_led.setData(m_buffer);
    }

    private void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }
    }
}

