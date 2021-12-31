package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import girlsofsteel.RobotMap;

//Created by FRC team 3357
//2008
//http://www.chiefdelphi.com/forums/showthread.php?t=82409
public class UltrasonicSensor {

    private static final double IN_TO_CM_CONVERSION = 2.54;

    private boolean m_useUnits;    //Are we using units or just returning voltage?
    private double m_minVoltage;      //Minimum voltage the ultrasonic sensor can return
    private double m_voltageRange; //The range of the voltages returned by the sensor (maximum - minimum)
    private double m_minDistance;  //Minimum distance the ultrasonic sensor can return in inches
    private double m_distanceRange; //The range of the distances returned by this class in inches (maximum - minimum)
    private final AnalogInput m_channel;

    //constructor
    public UltrasonicSensor() {
        m_channel = new AnalogInput(RobotMap.ULTRASONIC_SENSOR_PORT);
        //  default values
        m_useUnits = true;
        m_minVoltage = 2.5;
        m_voltageRange = 5.5 - m_minVoltage;
        m_minDistance = 6.0;
        m_distanceRange = 254.0 - m_minDistance;
    }

    // constructor
    public UltrasonicSensor(int channel, boolean useUnits, double minVoltage,
                            double maxVoltage, double minDistance, double maxDistance) {
        m_channel = new AnalogInput(channel);
        //   only use unit-specific variables if we're using units
        if (useUnits) {
            m_useUnits = true;
            m_minVoltage = minVoltage;
            m_voltageRange = maxVoltage - minVoltage;
            m_minDistance = minDistance;
            m_distanceRange = maxDistance - minDistance;
        }
    }

    //  Just get the voltage.
    private double getVoltage() {
        return m_channel.getVoltage();
    }
    /* GetRangeInInches
     * Returns the range in inches
     * Returns -1.0 if units are not being used
     * Returns -2.0 if the voltage is below the minimum voltage
     */

    public double getRangeInInches() {
        //if we're not using units, return -1, a range that will most likely never be returned
        if (!m_useUnits) {
            return -1.0;
        }
        double range = m_channel.getVoltage();
        if (range < m_minVoltage) {
            return -2.0;
        }
        //  first, normalize the voltage
        range = (range - m_minVoltage) / m_voltageRange;
        //  next, denormalize to the unit range
        range = (range * m_distanceRange) + m_minDistance;
        return range;
    }
    /* GetRangeInCM
     * Returns the range in centimeters
     * Returns -1.0 if units are not being used
     * Returns -2.0 if the voltage is below the minimum voltage
     */

    public double getRangeInCM() {
        //if we're not using units, return -1, a range that will most likely never be returned
        if (!m_useUnits) {
            return -1.0;
        }
        double range = m_channel.getVoltage();
        if (range < m_minVoltage) {
            return -2.0;
        }
        // first, normalize the voltage
        range = (range - m_minVoltage) / m_voltageRange;
        // next, denormalize to the unit range
        range = (range * m_distanceRange) + m_minDistance;
        // finally, convert to centimeters
        range *= IN_TO_CM_CONVERSION;
        return range;
    }

    protected void initDefaultCommand() {
    }
}
