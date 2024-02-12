package com.gos.crescendo2024.subsystems;


import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.led_patterns.AlertPatterns;
import com.gos.crescendo2024.led_patterns.AutoModePattern;
import com.gos.crescendo2024.led_patterns.AutoPattern;
import com.gos.crescendo2024.led_patterns.HasPiecePattern;
import com.gos.crescendo2024.led_patterns.TeleopPattern;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedManagerSubsystem extends SubsystemBase {

    private static final int MAX_INDEX_LED = 30;

    private final IntakeSubsystem m_intakeSubsystem;

    private final Autos m_autoModeFactory;

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
        m_autoModePattern = new AutoModePattern(m_buffer, MAX_INDEX_LED);
    }

    public void enabledPatterns() {
        if (DriverStation.isTeleop()) {
            m_teleopPattern.writeLED();
        } else if (DriverStation.isAutonomous()) {
            m_autoPattern.writeAutoPattern();
        }

        m_hasPiecePattern.update(m_intakeSubsystem.hasGamePiece());
        m_hasPiecePattern.writeLeds();
    }

    public void disabledPatterns() {
        Autos.AutoModes autoMode = m_autoModeFactory.autoModeLightSignal();
        m_autoModePattern.writeAutoModePattern(autoMode);
        m_alert.writeLEDs();
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

