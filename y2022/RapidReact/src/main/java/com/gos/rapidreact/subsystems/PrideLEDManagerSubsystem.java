package com.gos.rapidreact.subsystems;


import com.gos.rapidreact.Constants;
import com.gos.rapidreact.led.LEDRainbow;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PrideLEDManagerSubsystem extends SubsystemBase {

    private static final int MAX_INDEX_LED = 60;
    private static final int PORT = Constants.LED;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    private final LEDRainbow m_prideRainbow;

    public PrideLEDManagerSubsystem() {
        m_led = new AddressableLED(PORT);
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);

        m_prideRainbow = new LEDRainbow(m_buffer, 0, MAX_INDEX_LED);

        m_led.setLength(m_buffer.getLength());

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();

    }

    @Override
    public void periodic() {
        m_prideRainbow.writeLeds();
    }
}

