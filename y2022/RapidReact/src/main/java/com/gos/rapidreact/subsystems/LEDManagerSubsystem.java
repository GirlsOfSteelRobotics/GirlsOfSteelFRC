package com.gos.rapidreact.subsystems;

import com.gos.rapidreact.Constants;
import com.gos.rapidreact.LED.LEDFlash;
import com.gos.rapidreact.LED.LEDMovingPixel;
import com.gos.rapidreact.LED.LEDPolkaDots;
import com.gos.rapidreact.LED.LEDRainbow;
import com.gos.rapidreact.LED.LEDBoolean;
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
    private LEDFlash m_ledFlashSecond;
    private LEDRainbow m_ledRainbow;
    private LEDPolkaDots m_ledPolkaDots;
    private LEDMovingPixel m_movingPixel;
    private LEDBoolean m_boolean;


    public LEDManagerSubsystem() {
        m_led = new AddressableLED(PORT);
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_ledFlash = new LEDFlash(m_buffer, 2, Color.kPapayaWhip, 0, MAX_INDEX_LED / 2);
        m_ledFlashSecond = new LEDFlash(m_buffer, 1, Color.kAquamarine, MAX_INDEX_LED / 2, MAX_INDEX_LED);
        m_ledRainbow = new LEDRainbow(MAX_INDEX_LED - 2, m_buffer, 3);
        m_ledPolkaDots = new LEDPolkaDots(m_buffer, 0, MAX_INDEX_LED);
        m_movingPixel = new LEDMovingPixel(m_buffer, 0, MAX_INDEX_LED, Color.kPurple);
        m_boolean = new LEDBoolean(m_buffer, 0, MAX_INDEX_LED / 6, Color.kDarkGreen, Color.kBlack);

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
        boolean seeLimelight = false;
        clear();
        // m_ledPolkaDots.polkaDots();
        // m_ledFlash.flash();
        // m_ledRainbow.rainbow();
        //m_movingPixel.movingPixel();
        //m_boolean.checkBoolean(false);

        m_led.setData(m_buffer);
    }

}
