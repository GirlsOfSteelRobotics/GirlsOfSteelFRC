package com.gos.deep_space.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.deep_space.RobotMap;

// NOTE: the lidar sensor measurement is from the back of lidar sensor

public class Lidar extends Subsystem{

    public double LIDAR_TOLERANCE = 0; // tune
    private Counter counter;

    public Lidar () {
        DigitalInput source = new DigitalInput(RobotMap.LIDAR_DIO);
        counter = new Counter(source);
        counter.setMaxPeriod(1.0);
        // Configure for measuring rising to falling pulses
        counter.setSemiPeriodMode(true);
        counter.reset();
    }

    @Override
    protected void initDefaultCommand() {

    }

    /**
     * Take a measurement and return the distance in cm
     *
     * @return Distance in cm
     */
    public double getDistance() {
        double cm;
        while (counter.get() < 1) {
            System.out.println("Lidar: waiting for distance measurement");
        }
        /* getPeriod returns time in seconds. The hardware resolution is microseconds.
         * The LIDAR-Lite unit sends a high signal for 10 microseconds per cm of distance.
         */
        cm = (counter.getPeriod() * 1000000.0 / 10.0) - 18;
        return cm;
    }
}
