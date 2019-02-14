package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class LidarLitePWM {
	private Counter counter;
	
	public LidarLitePWM (int dio) {
		DigitalInput source = new DigitalInput(dio);
		counter = new Counter(source);
	    counter.setMaxPeriod(1.0);
	    // Configure for measuring rising to falling pulses
	    counter.setSemiPeriodMode(true);
	    counter.reset();
	}

	/**
	 * Take a measurement and return the distance in cm
	 * 
	 * @return Distance in cm
	 */
	public double getDistance() {
		double cm;
		while (counter.get() < 1) {
			System.out.println("LidarLitePWM: waiting for distance measurement");
		}
		/* getPeriod returns time in seconds. The hardware resolution is microseconds.
		 * The LIDAR-Lite unit sends a high signal for 10 microseconds per cm of distance.
		 */
		cm = (counter.getPeriod() * 1000000.0 / 10.0) - 18;
		return cm;
	}
}