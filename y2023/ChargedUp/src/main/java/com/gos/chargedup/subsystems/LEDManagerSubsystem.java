package com.gos.chargedup.subsystems;

import com.gos.lib.led.mirrored.MirroredLEDFlash;
import com.gos.lib.led.mirrored.MirroredLEDPercentScale;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class LEDManagerSubsystem extends SubsystemBase {
    // subsystems
    private final CommandXboxController m_joystick;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    private final MirroredLEDPercentScale m_drivetrainSpeed;


    private final MirroredLEDFlash m_coneGamePieceSignal;

    private final MirroredLEDFlash m_cubeGamePieceSignal;

    private boolean m_optionConeLED;

    private boolean m_optionCubeLED;

    private static final int MAX_INDEX_LED = 30;

    public LEDManagerSubsystem(CommandXboxController joystick) {
        m_joystick = joystick;

        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(0);

        m_drivetrainSpeed = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kGreen, 1);

        m_coneGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kYellow);

        m_cubeGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kMediumPurple);

        m_led.setLength(m_buffer.getLength());

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
    }

    @Override
    public void periodic() {
        clear();
        if (m_optionConeLED) {
            m_coneGamePieceSignal.writeLeds();
        }

        else if (m_optionCubeLED) {
            m_cubeGamePieceSignal.writeLeds();
        }

        driverPracticePatterns();
        m_led.setData(m_buffer);
    }

    public void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }
    }

    public void teleopPatterns() {
        m_optionCubeLED = false;
        m_optionConeLED = false;
    }

    private void driverPracticePatterns() {
        m_drivetrainSpeed.distanceToTarget(Math.abs(m_joystick.getLeftY()));
        m_drivetrainSpeed.writeLeds();
    }

    public void setConeGamePieceSignal() {
        m_optionConeLED = true;

    }

    public void setCubeGamePieceSignal() {
        m_optionCubeLED = true;
    }

    public CommandBase commandConeGamePieceSignal() {
        return this.runEnd(this::setConeGamePieceSignal, this::teleopPatterns);
    }

    public CommandBase commandCubeGamePieceSignal() {
        return this.runEnd(this::setCubeGamePieceSignal, this::teleopPatterns);
    }
}

