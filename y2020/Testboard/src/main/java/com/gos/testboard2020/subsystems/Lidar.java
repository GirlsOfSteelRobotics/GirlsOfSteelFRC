package com.gos.testboard2020.subsystems;

import com.gos.testboard2020.Constants;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// NOTE: the lidar sensor measurement is from the back of lidar sensor

public class Lidar extends SubsystemBase {

    public static final double LIDAR_TOLERANCE = 1; // tune

    private final Counter m_counter;

    public Lidar() {
        m_counter = new Counter(new DigitalInput(Constants.LIDAR_PWM));
        m_counter.setMaxPeriod(1.0);
        // Configure for measuring rising to falling pulses
        m_counter.setSemiPeriodMode(true);
        m_counter.reset();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    /**
     * Take a measurement and return the distance in cm
     *
     * @return Distance in cm
     */
    public double getDistance() {
        double cm;
        while (m_counter.get() < 1) {
            System.out.println("Lidar: waiting for distance measurement");
        }
        /*
         * getPeriod returns time in seconds. The hardware resolution is microseconds.
         * The LIDAR-Lite unit sends a high signal for 10 microseconds per cm of
         * distance.
         */
        cm = (m_counter.getPeriod() * 1000000.0 / 10.0) - 18;
        return cm;
    }
}
