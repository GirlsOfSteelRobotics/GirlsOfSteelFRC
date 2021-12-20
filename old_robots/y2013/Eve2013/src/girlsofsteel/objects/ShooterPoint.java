/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.objects;


/**
 *
 * @author Sylvie
 */
public class ShooterPoint {
    private final double m_voltage;
    private final double m_encoderSpeed;
    private final double m_battery;

    public ShooterPoint(double voltage, double encoderSpeed, double battery){
        this.m_voltage = voltage;
        this.m_encoderSpeed = encoderSpeed;
        this.m_battery = battery;
    }

    public double getVoltage(){
        return m_voltage;
    }

    public double getEncoderSpeed(){
        return m_encoderSpeed;
    }

    public double getBattery(){
        return m_battery;
    }
}
