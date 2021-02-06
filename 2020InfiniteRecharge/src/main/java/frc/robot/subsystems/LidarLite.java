package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// NOTE: the lidar sensor measurement is from the back of lidar sensor

public class LidarLite extends SubsystemBase {

    private final Counter m_counter;

    public LidarLite() {
        m_counter = new Counter(new DigitalInput(Constants.DIGITAL_INPUT_LIDAR_LITE));
        m_counter.setMaxPeriod(1.0);
        // Configure for measuring rising to falling pulses
        m_counter.setSemiPeriodMode(true);
        m_counter.reset();
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
