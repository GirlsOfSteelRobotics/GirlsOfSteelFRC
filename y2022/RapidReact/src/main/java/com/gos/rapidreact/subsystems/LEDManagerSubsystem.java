package com.gos.rapidreact.subsystems;

import com.gos.rapidreact.Constants;
import com.gos.rapidreact.LED.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDManagerSubsystem extends SubsystemBase {
    private static final int MAX_INDEX_LED = 30;
    private static final int PORT = Constants.LED;
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;
    private LEDFlash m_ledFlash;
    
    public LEDManagerSubsystem() {
        m_led = new AddressableLED(PORT);
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_ledFlash = new LEDFlash(MAX_INDEX_LED, PORT, m_led, m_buffer, 2, Color.kPapayaWhip);

        m_led.setLength(m_buffer.getLength());

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
    }
    
    @Override
    public void periodic() {
        m_ledFlash.flash();
        m_led.setData(m_buffer);
    }

}
