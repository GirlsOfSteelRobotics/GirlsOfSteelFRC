/*
 * This is a class we made for the special speed control sensor.
 */

package com.gos.ultimate_ascent.objects;

/**
 * @author Sylvie
 */
public class MagneticSpeedSensor {

    private final MagneticPulseCounter m_pulseCounter;

    //Units: Pulses per second
    public MagneticSpeedSensor(int channel) {
        m_pulseCounter = new MagneticPulseCounter(channel);
        new Thread(m_pulseCounter).start(); // NOPMD
    }

    public double get() {
        return m_pulseCounter.getRate();
    }

}
