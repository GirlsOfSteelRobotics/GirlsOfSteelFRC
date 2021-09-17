package com.girlsofsteelrobotics.atlas.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.girlsofsteelrobotics.atlas.RobotMap;

//Created by FRC team 3357
//2008
//http://www.chiefdelphi.com/forums/showthread.php?t=82409
public class UltrasonicSensor extends SensorBase {///*

/**
 *
 * @author
 */
    /*
     * Current Status: It prints out zero for the range, and the isRangeValid() method
     * returns false.  It is no longer erroring, and it is connected to the ultrasonic
     * sensor, because the isEnabled() method returns true.
//
//    Ultrasonic sensor;
//
//    public UltrasonicSensor() {
//        sensor = new Ultrasonic(DigitalOutput.kPwmChannels, RobotMap.ULTRASONIC_SENSOR_PORT, Ultrasonic.Unit.kMillimeter);
//    }
//    
//    public void enable()
//    {
//        sensor.setEnabled(true);
//        sensor.setAutomaticMode(true);
//    }
//    
//    public void disable()
//    {
//        sensor.setEnabled(false);
//    }
//    public double getDistance()
//    {
//        sensor.ping();
//        System.out.println(sensor.isRangeValid());
//        return sensor.getRangeInches();
//    }
//
//    protected void initDefaultCommand() {
//      
//    }     */
            
    


    private final double IN_TO_CM_CONVERSION = 2.54;
    private boolean use_units;    //Are we using units or just returning voltage?
    private double min_voltage;	  //Minimum voltage the ultrasonic sensor can return
    private double voltage_range; //The range of the voltages returned by the sensor (maximum - minimum)
    private double min_distance;  //Minimum distance the ultrasonic sensor can return in inches
    private double distance_range;//The range of the distances returned by this class in inches (maximum - minimum)
    AnalogInput channel;
    //constructor
    public UltrasonicSensor() {
        channel = new AnalogInput(RobotMap.ULTRASONIC_SENSOR_PORT);
      //  default values
		use_units = true;
		min_voltage = 2.5;
		voltage_range = 5.5 - min_voltage;
		min_distance = 6.0;
		distance_range = 254.0 - min_distance;
    }
   // constructor
    public UltrasonicSensor(int _channel, boolean _use_units, double _min_voltage,
            double _max_voltage, double _min_distance, double _max_distance) {
        channel = new AnalogInput(_channel);
     //   only use unit-specific variables if we're using units
        if (_use_units) {
            use_units = true;
            min_voltage = _min_voltage;
            voltage_range = _max_voltage - _min_voltage;
            min_distance = _min_distance;
            distance_range = _max_distance - _min_distance;
        } 
    }
   //  Just get the voltage.
    private double getVoltage() {
        return channel.getVoltage();
    }
    /* GetRangeInInches
     * Returns the range in inches
     * Returns -1.0 if units are not being used
     * Returns -2.0 if the voltage is below the minimum voltage
     */

    public double getRangeInInches() {
        double range;
        //if we're not using units, return -1, a range that will most likely never be returned
        if (!use_units) {
            return -1.0;
        }
        range = channel.getVoltage();
        if (range < min_voltage) {
            return -2.0;
        }
      //  first, normalize the voltage
        range = (range - min_voltage) / voltage_range;
      //  next, denormalize to the unit range
        range = (range * distance_range) + min_distance;
        return range;
    }
    /* GetRangeInCM
     * Returns the range in centimeters
     * Returns -1.0 if units are not being used
     * Returns -2.0 if the voltage is below the minimum voltage
     */

    public double getRangeInCM() {
        double range;
        //if we're not using units, return -1, a range that will most likely never be returned
        if (!use_units) {
            return -1.0;
        }
        range = channel.getVoltage();
        if (range < min_voltage) {
            return -2.0;
        }
       // first, normalize the voltage
        range = (range - min_voltage) / voltage_range;
       // next, denormalize to the unit range
        range = (range * distance_range) + min_distance;
       // finally, convert to centimeters
        range *= IN_TO_CM_CONVERSION;
        return range;
    }

    protected void initDefaultCommand() {
    }
}

