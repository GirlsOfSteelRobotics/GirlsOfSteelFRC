package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.RobotMap;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.objects.Camera;

public class Turret extends Subsystem implements PIDOutput, PIDSource {

    public double offsetAngle = 0.34;
    //knob stuff doesn't really matter -> below are magical values we don't have
    static final double MAX_TURRET_KNOB_VALUE = 0.0;
    static final double MIN_TURRET_KNOB_VALUE = 10.0;
    private static final double PULSES = 1600.0;
    private static final double ENCODER_UNIT = 360.0 / PULSES;
    public final double TURRET_OVERRIDE_DEADZONE = 0.5;
    
    Jaguar turretJag = new Jaguar(RobotMap.TURRET_JAG);
    Encoder encoder = new Encoder(RobotMap.ENCODER_TURRET_CHANNEL_A,
            RobotMap.ENCODER_TURRET_CHANNEL_B, false, Encoder.EncodingType.k4X);
    private double p = 0.2;//0.45;
    private double i = 0.0;
    private double d = 0.0;
    PIDController PID = new PIDController(p, i, d, this, this);
    boolean pressedRightSwitch = false;

    public Turret() {
    }
    
    public void initDefaultCommand(){
        
    }
    
    public void changeTurretOffset(){
        double turretOffset = SmartDashboard.getDouble("Turret Offset", 0.0);
        offsetAngle = turretOffset;
    }
    
    //sets the unit -> only used in TEST -> degrees or just pulses
    public void setEncoderUnit(double pulses, boolean inDegrees){
        if(inDegrees){
            encoder.setDistancePerPulse(360.0/pulses);
        }else{
            encoder.setDistancePerPulse(1.0/pulses);
        }
        encoder.start();
    }
    
    //initalizes encoder -> sets the unit to degrees
    public void initEncoder(){
        encoder.setDistancePerPulse(ENCODER_UNIT); //degrees
        encoder.start();
    }

    public void setJagSpeed(double speed) {
        turretJag.set(-speed);
    }

    public void stopJag() {
        setJagSpeed(0.0);
    }

    //stuff for the PID
    public void pidWrite(double output) {
        setJagSpeed(output);
    }

    //more stuff for the PID
    public double pidGet() {
        return getTurretAngle();
    }

    public void enablePID() { //for re-enabling the PID when disabled
        PID.setContinuous(); //lets the PID know it is a circle
        PID.setInputRange(0.0, 360.0); //and goes between 0 to 360 to 0 to 360, etc
        PID.enable();
    }

    //sets the P & D values -> used for testing
    //i is 0.0 because it is a rate PID
    public void setPDs(double pVal, double dVal){
        PID.setPID(pVal, 0.0, dVal);
    }
    
    //just in case the PID loop starts freaking out & you need to re-assing the
    //PID values in execute (used in chassis -> don't think it's having a probelem
    //in chassis's PIDs
    public void setPDs(){
        PID.setPID(p, i, d);
    }
    
    public void disablePID() {
        PID.disable();
    }

    public void setPIDSetPoint(double setpoint) {
        setpoint = setpoint % 360;//takes the remainder of 360 -> so the set point
        //cannot be larger than |360|
        if (setpoint < 0) { //if the setpoint is less than 0 -> set to a positive value
            setpoint = setpoint + 360;//the negative set point is the same as the
            //positive, but plus 360
        }
        PID.setSetpoint(setpoint);
    }

    public double getTurretAngle() {
        double angle;
        angle = -encoder.getDistance() + CommandBase.chassis.getTheta();
        //the angle is the encoder (turret) angle + the gyro angle
        //the gyro angle -> is what position the chassis is facing
        //so the total angle the turret is facing is the sum of them
        angle = angle % 360;//same as above -> must be a positive number
        if (angle < 0) {//between 0 and 360
            angle = angle + 360;
        }
        return angle;
    }

    public void moveToAngleCompensation(double angleCompensation) {
        double setPoint;
        setPoint = angleCompensation + getTurretAngle();
        //adds the angleCompensation to the place the turret is at currently so
        //the set point does not try to move to just it's position, but it's
        //position relative to the position of the turret at the moment
        setPIDSetPoint(setPoint);
    }

    public double getEncoderDistance() {
        return encoder.getDistance();
    }

    //stuff from the knob -> as of not, not needed
    public double getTurretAngleCompensationFromKnob(double knobValue) {
        double minAngleCompensationValue = 0.0; //I would think this would make sense -> make sure
        double maxAngleCompensationValue = 5.0; //FIND THIS!!
        double angleCompensation = ((maxAngleCompensationValue - minAngleCompensationValue) * (knobValue - MIN_TURRET_KNOB_VALUE)) / (MAX_TURRET_KNOB_VALUE - MIN_TURRET_KNOB_VALUE);
        //maps the output of the knob to the angleCompensation
        return angleCompensation;
    }

    public double getTurretManualSpeed(double knobValue) {
        double minSpeedValue = 0.0; //I would think this would make sense -> make sure
        double maxSpeedValue = 5.0; //FIND THIS!!
        double speed = ((maxSpeedValue - minSpeedValue) * (knobValue - MIN_TURRET_KNOB_VALUE)) / (MAX_TURRET_KNOB_VALUE - MIN_TURRET_KNOB_VALUE);
        //maps the speed of the turret to the knob values
        return speed;
    }
    
    public void autoTrack() { //only works when the camera has the target (a boolean
        //in the Camera object)
        double diffAngle = Camera.getDiffAngle();//get the angle you are off from
        //the target -> the real life value is found in the Camera object
        System.out.println("Difference Angle:   " + diffAngle);
        double setPoint;
        setPoint = diffAngle / 4 + getTurretAngle() + offsetAngle;//degrees
        //the set point is the difference of the angle plus where the turret is currently
        //subtract the offset angle that the shooter shoots straight from
        setPIDSetPoint(setPoint);
    }
    
}