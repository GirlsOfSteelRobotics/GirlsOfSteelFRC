package com.gos.lib.sensors;

import org.wpilib.hardware.rotation.DutyCycle;

// NOTE: the lidar sensor measurement is from the back of lidar sensor

public class LidarLite {

    private final DutyCycle m_dutyCycle;

    public LidarLite(int digitalInputChannel) {
        // 2027: Counter semi-period mode was removed; measure the high pulse width with DutyCycle instead
        m_dutyCycle = new DutyCycle(digitalInputChannel);
    }

    /**
     * Take a measurement and return the distance in cm
     *
     * @return Distance in cm
     */
    public double getDistance() {
        /*
         * The hardware resolution is microseconds.
         * The LIDAR-Lite unit sends a high signal for 10 microseconds per cm of
         * distance.
         */
        double highTimeMicroseconds = m_dutyCycle.getHighTimeNanoseconds() / 1000.0;
        return (highTimeMicroseconds / 10.0) - 18;
    }
}
