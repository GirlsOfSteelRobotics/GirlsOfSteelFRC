/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * |\  |
 * | \ |
 * |  \|OTE!!! larger margin for error in shooter than for Watson :)
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.MagneticSpeedSensor;
import girlsofsteel.objects.ShooterPoint;
import java.util.Vector;

/**
 *
 * @author Sylvie
 */
public class Shooter extends Subsystem {

    private Jaguar shooterJag;
    //Makes the special speed control sensor
    private MagneticSpeedSensor magSpeed;
    //angle between the shooter and the slot
    private final double ANGLE = 20.0; //This is NOT magic
    //height of the top slot that the Frisbee can be shot into
    private static final double FRISBEE_SLOT_HEIGHT = 21.6; //This is in INCHES
    //height of the robot
    private static final double ROBOT_HEIGHT = 3.0; //MAGIC
    //height of shooter
    private static final double SHOOTER_HEIGHT = 4.0; //MAGIC
    //how much shooter wheel is off by
    public final double VELOCITY_ERROR_RANGE = 1.0; //MAGIC
    //p, i, and d values FROM LAST YEAR CHECK FOR THIS YEAR
    private PIDController PID;
    private double p = 0.0;
    private double i = 0.0;
    private double d = 0.0;
    //Array of ShooterPoints objects to track voltage, encoder speed, and battery voltage of shooting
    public ShooterPoint[] speeds = new ShooterPoint[100];
    
    boolean shoot = false;

    public Shooter() {
        //Shooter Jag
        shooterJag = new Jaguar(RobotMap.SHOOTER_JAG);

        //This is the special shooter speed controller.
        magSpeed = new MagneticSpeedSensor(RobotMap.DIGITAL_INTPUT_CHANNEL);

        //Makes the PIDController  
        PID = new PIDController(p, i, d, magSpeed,
                new PIDOutput() {
                    public void pidWrite(double output) {
                        setJags(output);
                    }
                });
        for (int i = 0; i < speeds.length; i++) {
            speeds[i] = null;
        }
    }

//PID methods
    public void initPID() {
        PID.enable();
    }

    public void setPIDspeed(double speed) {
        PID.setSetpoint(speed);
    }

    public void setPIDValues(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
        PID.setPID(p, i, d);
    }

    public void disablePID() {
        PID.disable();
    }

    public boolean isWithinSetPoint(double setPoint) {
        //set point will be false if you are not getting a velocity
        if (setPoint == 0) {
            return false;
        } else {//otherwise look at the encoder
            return (magSpeed.get() > setPoint - VELOCITY_ERROR_RANGE
                    && magSpeed.get() < setPoint + VELOCITY_ERROR_RANGE);
        }
    }

    //Jag methods
    public void setJags(double speed) {
        shooterJag.set(speed); //Should this be negative?
    }

    public void stopJags() {
        setJags(0.0);
    }

    //Encoder methods
    public void initEncoder() {
        //need to start magSpeed?
    }

    public double getEncoderRate() {
        return magSpeed.get();
    }

    public void stopEncoder() {
        //need to stop magSpeed?
    }

    public void shoot(double speed) {
        //shoots the Frisbee (spins rollers at calculated speed)
        setPIDValues(p, i, d);
        setPIDspeed(speed);
        if (isWithinSetPoint(speed)) {
            //Send in the frisbee
        } else {
            //Don't send in the frisbee
        }
    }

    public void fillArray(double voltage, double encoderSpeed, double battery) {
        for (int i = 0; i < speeds.length; i++) {
            if (speeds[i] == null) {
                ShooterPoint point = new ShooterPoint(voltage, encoderSpeed, battery);
                speeds[i] = point;
                i = speeds.length;
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
        Vector closePoints = new Vector();

        //Get all points close to the main point and add them to the closePoints array
        for (int i = 0; i < speeds.length; i++) {
            //The other point
            ShooterPoint otherPoint = speeds[i];
            if (getDistance(encoderSpeed, battery, otherPoint.getEncoderSpeed(), otherPoint.getBattery()) < rangeCutOff) {
                closePoints.addElement(otherPoint);
            }
        }

        //The voltage of one of the close points
        double closePointVoltage;

        //Find the average of all the voltages for the close points
        for (int k = 0; k < closePoints.size(); k++) {
            ShooterPoint otherPoint = (ShooterPoint) closePoints.elementAt(k);
            closePointVoltage = otherPoint.getVoltage();
            voltage += closePointVoltage;
        }
        //Dividing by number of close points
        voltage /= closePoints.size();

        return voltage;
    }

    public void printPointsArray() {
        for (int i = 0; i < speeds.length; i++) {
            if (speeds[i] != null) {
                System.out.println("Point Voltage: " + speeds[i].getVoltage() + "\t");
                System.out.print("Point Shooter Encoder Speed: " + speeds[i].getEncoderSpeed());
                System.out.println("Point Battery Voltage: " + speeds[i].getBattery());
            }
        }
    }

    public void setShootTrue(){
        shoot = true;
    }
    
    public void setShootFalse(){
        shoot = false;
    }
    
    public boolean isTimeToShoot(){
        return shoot;
    }
    
    protected void initDefaultCommand() {
    }
}
