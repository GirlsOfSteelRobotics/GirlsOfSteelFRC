/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * |\  |
 * | \ |
 * |  \|OTE!!! larger margin for error in shooter than for Watson :)
 */
package girlsofsteel.subsystems;

//import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.MagneticSpeedSensor;
import girlsofsteel.objects.ShooterPoint;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sylvie
 */
public class Shooter extends Subsystem {

    private final Jaguar m_shooterJag;
    //Makes the special speed control sensor
    private final MagneticSpeedSensor m_magSpeed;
    //angle between the shooter and the slot
    //height of the top slot that the Frisbee can be shot into
    //how much shooter wheel is off by
    public static final double VELOCITY_ERROR_RANGE = 1.0; //MAGIC
    //p, i, and d values FROM LAST YEAR CHECK FOR THIS YEAR
    private final PIDController m_pid;
    private double m_p;
    private double m_i;
    private double m_d;
    //Array of ShooterPoints objects to track voltage, encoder speed, and battery voltage of shooting
    public ShooterPoint[] m_speeds = new ShooterPoint[100];

    private boolean m_shoot;

    public Shooter() {
        //Shooter Jag
        m_shooterJag = new Jaguar(RobotMap.SHOOTER_JAG);

        //This is the special shooter speed controller.
        m_magSpeed = new MagneticSpeedSensor(RobotMap.DIGITAL_INTPUT_CHANNEL);

        //Makes the PIDController
        m_pid = new PIDController(m_p, m_i, m_d, m_magSpeed,
                new PIDOutput() {
                    @Override
                    public void pidWrite(double output) {
                        setJags(output);
                    }
                });
        for (int i = 0; i < m_speeds.length; i++) {
            m_speeds[i] = null; // NOPMD
        }
    }

//PID methods
    public void initPID() {
        m_pid.enable();
    }

    public void setPIDspeed(double speed) {
        m_pid.setSetpoint(speed);
    }

    public void setPIDValues(double p, double i, double d) {
        this.m_p = p;
        this.m_i = i;
        this.m_d = d;
        m_pid.setPID(p, i, d);
    }

    public void disablePID() {
        m_pid.disable();
    }

    public boolean isWithinSetPoint(double setPoint) {
        //set point will be false if you are not getting a velocity
        if (setPoint == 0) {
            return false;
        } else {//otherwise look at the encoder
            return (m_magSpeed.get() > setPoint - VELOCITY_ERROR_RANGE
                    && m_magSpeed.get() < setPoint + VELOCITY_ERROR_RANGE);
        }
    }

    //Jag methods
    public void setJags(double speed) {
        m_shooterJag.set(speed); //Should this be negative?
    }

    public void stopJags() {
        setJags(0.0);
    }

    //Encoder methods
    public void initEncoder() {
        //need to start magSpeed?
    }

    public double getEncoderRate() {
        return m_magSpeed.get();
    }

    public void stopEncoder() {
        //need to stop magSpeed?
    }

    public void shoot(double speed) {
        //shoots the Frisbee (spins rollers at calculated speed)
        setPIDValues(m_p, m_i, m_d);
        setPIDspeed(speed);
    }

    public void fillArray(double voltage, double encoderSpeed, double battery) {
        for (int i = 0; i < m_speeds.length; i++) {
            if (m_speeds[i] == null) {
                ShooterPoint point = new ShooterPoint(voltage, encoderSpeed, battery);
                m_speeds[i] = point;
                i = m_speeds.length; // NOPMD
            }
        }
    }

    /*
     * FOR THE SHOOTER TUNING
     * Where one point is on a 2dimensional graph: xaxis is Encoder Speed
     * and yaxis is Battery Voltage.
     * Finds the distance between these two points given the coordinates.
     * "desired" parameters are the middle point coordinates while the "2"
     * parameters are the testing points.
     */
    public double getDistance(double desiredEncoderSpeed, double desiredBattery1, double encoderSpeed2, double battery2) {
        //Manually squared because Math.pow() could not be found. Does anyone know why?
        return Math.sqrt(((desiredEncoderSpeed - encoderSpeed2) * (desiredEncoderSpeed - encoderSpeed2)) + ((desiredBattery1 - battery2) * (desiredBattery1 - battery2)));
    }

    /*
     * Make the "speeds" array a one-dimensional array with with objects of speeds (with voltage, encoder speed, and battery).
     * This will make things easier and perfect
     *
     * TODO:
     * Having found actual values for "speeds", use MatLab to get a BFL. Then use the BFL to get more accurate values.
     */
    public double getVoltageFromTable(double encoderSpeed, double battery) {
        double voltage = 0.0;

        //How close the other points should be to the main point
        //TODO this constant needs to be finetuned via testing
        double rangeCutOff = 0.1;

        /*The vector where close points will be saved
         * NOTE: Because this vector is generic, when getting values from the array it is necessary to CAST the value to a ShooterPoint
         */
        List<ShooterPoint> closePoints = new ArrayList<>();

        //Get all points close to the main point and add them to the closePoints array
        for (ShooterPoint otherPoint : m_speeds) {
            //The other point
            if (getDistance(encoderSpeed, battery, otherPoint.getEncoderSpeed(), otherPoint.getBattery()) < rangeCutOff) {
                closePoints.add(otherPoint);
            }
        }

        //The voltage of one of the close points
        double closePointVoltage;

        //Find the average of all the voltages for the close points
        for (ShooterPoint otherPoint : closePoints) {
            closePointVoltage = otherPoint.getVoltage();
            voltage += closePointVoltage;
        }
        //Dividing by number of close points
        voltage /= closePoints.size();

        return voltage;
    }

    public void printPointsArray() {
        for (ShooterPoint speed : m_speeds) {
            if (speed != null) {
                System.out.println("Point Voltage: " + speed.getVoltage() + "\t");
                System.out.print("Point Shooter Encoder Speed: " + speed.getEncoderSpeed());
                System.out.println("Point Battery Voltage: " + speed.getBattery());
            }
        }
    }

    public void setShootTrue(){
        m_shoot = true;
    }

    public void setShootFalse(){
        m_shoot = false;
    }

    public boolean isTimeToShoot(){
        return m_shoot;
    }

    @Override
    protected void initDefaultCommand() {
    }
}
