/*
 * This is a class we made for the special speed control sensor.
 */

package com.gos.ultimate_ascent.objects;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author Sylvie
 */
public class MagneticSpeedSensor implements PIDSource {

    private final MagneticPulseCounter m_pulseCounter;

    //Units: Pulses per second
    public MagneticSpeedSensor(int channel) {
        m_pulseCounter = new MagneticPulseCounter(channel);
        new Thread(m_pulseCounter).start(); // NOPMD
    }

    public double get() {
        return m_pulseCounter.getRate();
    }

    @Override
    public double pidGet() {
        return get();
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        throw new UnsupportedOperationException();
    }
}
