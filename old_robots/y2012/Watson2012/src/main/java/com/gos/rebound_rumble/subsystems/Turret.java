package com.gos.rebound_rumble.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.RobotMap;
import com.gos.rebound_rumble.objects.Camera;

public class Turret extends SubsystemBase {

    //knob stuff doesn't really matter -> below are magical values we don't have
    private static final double MAX_TURRET_KNOB_VALUE = 0.0;
    private static final double MIN_TURRET_KNOB_VALUE = 10.0;
    private static final double PULSES = 1600.0;
    private static final double ENCODER_UNIT = 360.0 / PULSES;
    public static final double TURRET_OVERRIDE_DEADZONE = 0.5;

    private static final double KP = 0.2; //0.45;
    private static final double KI = 0.0;
    private static final double KD = 0.0;

    private double m_offsetAngle = 0.34;
    private final Jaguar m_turretJag = new Jaguar(RobotMap.TURRET_JAG);
    private final Encoder m_encoder = new Encoder(RobotMap.ENCODER_TURRET_CHANNEL_A,
        RobotMap.ENCODER_TURRET_CHANNEL_B, false, Encoder.EncodingType.k4X);
    private final PIDController m_pid = new PIDController(KP, KI, KD);

    private final Chassis m_chassis;

    public Turret(Chassis chassis) {
        m_chassis = chassis;
    }



    public void changeTurretOffset() {
        double turretOffset = SmartDashboard.getNumber("Turret Offset", 0.0);
        m_offsetAngle = turretOffset;
    }

    //sets the unit -> only used in TEST -> degrees or just pulses
    public void setEncoderUnit(double pulses, boolean inDegrees) {
        if (inDegrees) {
            m_encoder.setDistancePerPulse(360.0 / pulses);
        } else {
            m_encoder.setDistancePerPulse(1.0 / pulses);
        }
    }

    //initalizes encoder -> sets the unit to degrees
    public void initEncoder() {
        m_encoder.setDistancePerPulse(ENCODER_UNIT); //degrees
    }

    public void setJagSpeed(double speed) {
        m_turretJag.set(-speed);
    }

    public void stopJag() {
        setJagSpeed(0.0);
    }

    public void enablePID() { //for re-enabling the PID when disabled
        m_pid.enableContinuousInput(0, 360); //lets the PID know it is a circle
        m_pid.reset();
    }

    //sets the P & D values -> used for testing
    //i is 0.0 because it is a rate PID
    public void setPDs(double pVal, double dVal) {
        m_pid.setPID(pVal, 0.0, dVal);
    }

    //just in case the PID loop starts freaking out & you need to re-assing the
    //PID values in execute (used in chassis -> don't think it's having a probelem
    //in chassis's PIDs
    public void setPDs() {
        m_pid.setPID(KP, KI, KD);
    }

    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public void setPIDSetPoint(double setpoint) {
        setpoint = setpoint % 360; //takes the remainder of 360 -> so the set point
        //cannot be larger than |360|
        if (setpoint < 0) { //if the setpoint is less than 0 -> set to a positive value
            setpoint = setpoint + 360; //the negative set point is the same as the
            //positive, but plus 360
        }
        m_pid.setSetpoint(setpoint);
    }

    public double getTurretAngle() {
        double angle;
        angle = -m_encoder.getDistance() + m_chassis.getTheta();
        //the angle is the encoder (turret) angle + the gyro angle
        //the gyro angle -> is what position the chassis is facing
        //so the total angle the turret is facing is the sum of them
        angle = angle % 360; //same as above -> must be a positive number
        if (angle < 0) { //between 0 and 360
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
        return m_encoder.getDistance();
    }

    //stuff from the knob -> as of not, not needed
    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    public double getTurretAngleCompensationFromKnob(double knobValue) {
        double minAngleCompensationValue = 0.0; //I would think this would make sense -> make sure
        double maxAngleCompensationValue = 5.0; //FIND THIS!!
        double angleCompensation = ((maxAngleCompensationValue - minAngleCompensationValue) * (knobValue - MIN_TURRET_KNOB_VALUE)) / (MAX_TURRET_KNOB_VALUE - MIN_TURRET_KNOB_VALUE);
        //maps the output of the knob to the angleCompensation
        return angleCompensation;
    }

    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    public double getTurretManualSpeed(double knobValue) {
        double minSpeedValue = 0.0; //I would think this would make sense -> make sure
        double maxSpeedValue = 5.0; //FIND THIS!!
        double speed = ((maxSpeedValue - minSpeedValue) * (knobValue - MIN_TURRET_KNOB_VALUE)) / (MAX_TURRET_KNOB_VALUE - MIN_TURRET_KNOB_VALUE);
        //maps the speed of the turret to the knob values
        return speed;
    }

    public void autoTrack() { //only works when the camera has the target (a boolean
        //in the Camera object)
        double diffAngle = Camera.getDiffAngle(); //get the angle you are off from
        //the target -> the real life value is found in the Camera object
        System.out.println("Difference Angle:   " + diffAngle);
        double setPoint;
        setPoint = diffAngle / 4 + getTurretAngle() + m_offsetAngle; //degrees
        //the set point is the difference of the angle plus where the turret is currently
        //subtract the offset angle that the shooter shoots straight from
        setPIDSetPoint(setPoint);
    }
}
