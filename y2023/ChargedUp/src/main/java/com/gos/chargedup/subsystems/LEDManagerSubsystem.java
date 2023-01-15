package com.gos.chargedup.subsystems;


import com.gos.lib.led.LEDDistanceToTarget;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class LEDManagerSubsystem extends SubsystemBase {
    // subsystems
    private final CommandXboxController m_joystick;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    private final LEDDistanceToTarget m_drivetrainSpeed;

    private static final int MAX_INDEX_LED = 30;

    public LEDManagerSubsystem(AddressableLED addressableLED,
                               AddressableLEDBuffer addressableLEDBuffer,
                               CommandXboxController joystick) {
        m_buffer = addressableLEDBuffer;
        m_led = addressableLED;

        m_joystick = joystick;

        m_drivetrainSpeed = new LEDDistanceToTarget(m_buffer, 0, MAX_INDEX_LED, Color.kGreen, 1);
    }

    @Override
    public void periodic() {

        driverPracticePatterns();
    }

    private void driverPracticePatterns() {
        m_drivetrainSpeed.distanceToTarget(Math.abs(m_joystick.getLeftY()));
    }
}

