package com.gos.rebound_rumble.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.rebound_rumble.RobotMap;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.objects.EncoderGoSPidController;
import com.gos.rebound_rumble.objects.ShooterLookupTable;
import com.gos.rebound_rumble.objects.SmoothEncoder;

@SuppressWarnings({"PMD.GodClass", "PMD.TooManyMethods"})
public class Shooter extends SubsystemBase {

    public static final double KEY_SPEED = 24.0; //dead-reckoning speed to use for
    //shooting with the key
    public static final double BRIDGE_SPEED = 29.0; //MAGIC value for speed when
    //shooting from the bridge button board stufficles
    private static final double MIN_SLIDER = 0.0;
    private static final double MAX_SLIDER = 3.15;

    public static final double VELOCITY_ERROR_RANGE = 1.0; //this is the speed that the
    //shooter wheel can be off by before it shoots the ball
    //hopefully this can be lowered once the PID is tuned nicely
    private static final double SCALE_TOP_ROLLERS_OFF = 5.0;

    private static final double PID_OUTPUT_THRESHOLD = 0.05; //change in voltage

    //constants -> meters if a distance
    private static final double TOP_HOOP_HEIGHT = 2.4892;
    private static final double ROBOT_HEIGHT = 1.2065; //floor to the axis of the shooter wheel
    private static final double WHEEL_DIAMETER = 0.2032; //shooter wheel diameter
    private static final double GEAR_RATIO = 1.0; //gear ratio of the shooter wheel
    private static final double PULSES = 100.0;
    private static final double ENCODER_UNIT = (Math.PI * WHEEL_DIAMETER * GEAR_RATIO) / PULSES;
    //don't change this! ^^ just a formula
    private static final double yDistance = TOP_HOOP_HEIGHT - ROBOT_HEIGHT; //the vertical distance
    //from the shooter to the top basket

    private static final double p = 0.4; //0.25; //0.25; //0.15; //0.1; //0.25;
    private static final double i = 50.0; //2.5; //0.06; //0.0002;
    private static final double d = 0.0;
    //integral threshold -> cuts of the value that is being multipled by the i term
    //with the PID output
    private static final double INTEGRAL_THRESHOLD = 2500.0; //errorSum usually stabilized around 1700

    private double m_angle;
    private double m_hangTime;
    private double m_newXDistance;
    private final Jaguar m_jags = new Jaguar(RobotMap.SHOOTER_JAGS);
    //    CHANGE FOR REAL WATSON:
    //    public Encoder encoder = new Encoder(RobotMap.ENCODER_SHOOTER_CHANNEL_A,
    //            RobotMap.ENCODER_SHOOTER_CHANNEL_B, true,
    //            CounterBase.EncodingType.k4X);
    //    private Relay topRollers = new Relay(RobotMap.TOP_ROLLER_SPIKE);
    //    PRACTICE WATSANNE:
    public Encoder m_encoder = new SmoothEncoder(RobotMap.ENCODER_SHOOTER_CHANNEL_A,
        RobotMap.ENCODER_SHOOTER_CHANNEL_B, true,
        CounterBase.EncodingType.k4X);
    private final Relay m_topRollersSpike = new Relay(RobotMap.TOP_ROLLER_SPIKE);
    private final EncoderGoSPidController m_pid = new EncoderGoSPidController(p, i, d, m_encoder,
        new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                setJags(output);
            }
        }, EncoderGoSPidController.RATE, INTEGRAL_THRESHOLD);

    private final ShooterLookupTable m_shooterLookupTable;

    public Shooter() {
        m_shooterLookupTable = new ShooterLookupTable();
    }



    public void setJags(double speed) {
        m_jags.set(-speed);
    }

    public void stopJags() {
        setJags(0.0);
    }

    public void topRollersForward() {
        m_topRollersSpike.set(Relay.Value.kForward);
    }

    public void topRollersBackward() {
        m_topRollersSpike.set(Relay.Value.kReverse);
    }

    public void topRollersOff() {
        m_topRollersSpike.set(Relay.Value.kOff);
    }

    public void initEncoder() {
        m_encoder.setDistancePerPulse(ENCODER_UNIT);
    }

    public void stopEncoder() {
    }

    public void initPID() {
        m_pid.setOutputThreshold(PID_OUTPUT_THRESHOLD);
        m_pid.enable();
    }

    public void setPIDSpeed(double speed) {
        m_pid.setSetPoint(speed);
    }

    //re-assigns the P & I values -> the D value is always 0.0 because it is a
    //rate PID controller
    public void setPIDValues(double p, double i, double d) {
        m_pid.setPID(p, i, d);
    }

    //this is for the weird bug in having to reassign PID values in execute
    //must be called in execute before anything else if using the shooter PID
    public void setPIDValues() {
        m_pid.setPID(p, i, d);
    }

    public double getPIDSetPoint() {
        return m_pid.getSetPoint();
    }

    //if the shooter wheel is within the set point range it will return true
    //used to decide if the top rollers should be run or not
    public boolean isWithinSetPoint(double setPoint) {
        if (setPoint == 0) { //set point will be false if you are not getting a velocity
            return false; //will not run the top rollers if the wheel is not run
        } else {
            return (m_pid.getRate() > setPoint - VELOCITY_ERROR_RANGE
                && m_pid.getRate() < setPoint + VELOCITY_ERROR_RANGE);
        }
    }

    public boolean isWithinSetPoint(double setPoint, double errorRange) {
        if (setPoint == 0) { //set point will be false if you are not getting a velocity
            return false; //will not run the top rollers if the wheel is not run
        } else {
            return (m_pid.getRate() > setPoint - errorRange
                && m_pid.getRate() < setPoint + errorRange);
        }
    }

    public void disablePID() {
        m_pid.disable();
    }

    public void resetPIDError() {
        m_pid.resetError();
    }

    public double getDistance() { //from the fender to the camera -> in meters
        return Camera.getXDistance() - 38 * (0.0254 / 1.0); //fender is 38inches
    }

    public void shoot(double speed) {
        setPIDValues();
        setPIDSpeed(speed);
        if (isWithinSetPoint(speed)) {
            topRollersForward();
        } else {
            if (!isWithinSetPoint(speed, SCALE_TOP_ROLLERS_OFF * VELOCITY_ERROR_RANGE)) {
                topRollersOff();
            }
        }
    }

    public void autoShoot(double cameraDistance) {
        setPIDValues();
        double velocity;
        velocity = m_shooterLookupTable.getVelocityFrTable(cameraDistance);
        System.out.println(cameraDistance + "       velocity:  " + velocity);
        setPIDSpeed(velocity);
        if (isWithinSetPoint(velocity)) {
            topRollersForward();
        } else {
            if (!isWithinSetPoint(velocity, SCALE_TOP_ROLLERS_OFF * VELOCITY_ERROR_RANGE)) {
                topRollersOff();
            }
        }
    }

    public void autoShootBestFitLine(double cameraDistance) {
        setPIDValues();
        double velocity;
        velocity = 2.16 * cameraDistance + 14.25;
        setPIDSpeed(velocity);
        if (isWithinSetPoint(velocity)) {
            topRollersForward();
        } else {
            if (!isWithinSetPoint(velocity, SCALE_TOP_ROLLERS_OFF * VELOCITY_ERROR_RANGE)) {
                topRollersOff();
            }
        }
    }

    //now tuned for banking...
    //    public void TESTAutoShootBank(double bankAddition, double cameraDistance){
    //        setPIDValues();
    //        double xDistance;
    //        double velocity;
    //        xDistance = cameraDistance + bankAddition;
    //        velocity = getVelocityFrTable(xDistance);
    //        System.out.println(xDistance + "       velocity:  " + velocity);
    //        setPIDSpeed(velocity);
    //        if(isWithinSetPoint(velocity)){
    //            topRollersForward();
    //        }else{
    //            if(!isWithinSetPoint(velocity, SCALE_TOP_ROLLERS_OFF*VELOCITY_ERROR_RANGE)){
    //                topRollersOff();
    //            }
    //        }
    //    }
    //
    //    public void autoShootBank(double cameraDistance){
    //        setPIDValues();
    //        double xDistance;
    //        double velocity;
    //        xDistance = cameraDistance + 0.74;
    //        velocity = getVelocityFrTable(xDistance);
    //        System.out.println(xDistance + "       velocity:  " + velocity);
    //        setPIDSpeed(velocity);
    //        if(isWithinSetPoint(velocity)){
    //            topRollersForward();
    //        }else{
    //            if(!isWithinSetPoint(velocity, SCALE_TOP_ROLLERS_OFF*VELOCITY_ERROR_RANGE)){
    //                topRollersOff();
    //            }
    //        }
    //    }

    public void shootUsingBallVelocity(double velocity) {
        double newVelocity = velocity * (5.5 / 4.0);
        //the velocity of the wheel must be altered to give the ball the correct
        //velocity -> the wheel velocity does not equal the ball velocity
        m_pid.setSetPoint(newVelocity);
    }

    //shoots using a direct velocity (of the wheel)
    public void shootUsingVelocity(double velocity) {
        double newVelocity = velocity;
        m_pid.setSetPoint(newVelocity);
    }

    //if the robot is not moving you use this function of physics to calculate
    //the velocity of the BALL
    public double getImmobileBallVelocity(double xDistance) {
        calculateAngle(xDistance);

        double ballVelocity;
        ballVelocity = getBallVelocity(xDistance);
        return ballVelocity;
    }

    //if the robot is moving it calculates the velocity it should be set at and
    //accounts for the compensation of the robot's velocity
    public double getMovingBallVelocity(double xDistance, double robotVelocityY,
                                        double robotVelocityX) {
        calculateAngle(xDistance);

        double initialVelocity;
        initialVelocity = getBallVelocity(xDistance);
        //must find the initial velocity that has to be set
        double ballVelocity;
        ballVelocity = getSpeedCompensation(xDistance, initialVelocity, robotVelocityY);
        //gets the compensation and returns a new velocity -> must set the compensation
        //of the turret separately in the command (shouldn't be accessed in the
        //Shooter subsystem
        return ballVelocity;
    }

    //this is the long complicated physics equation that was derived with mentors
    public double getBallVelocity(double xDistance) {
        double velocity;
        velocity = ((xDistance * Math.sqrt(9.8 / ((2 * (xDistance * Math.cos(m_angle))) - yDistance))) / Math.tan(m_angle)) * (5.5 / 4.0);
        return velocity;
    }

    private void calculateAngle(double xDistance) {
        m_angle = Math.atan(yDistance / xDistance); //radians
    }

    //the compensation that the speed of the ball has to go based on the
    //robot velocity -> new distances of being shot
    private double getSpeedCompensation(double xDistance, double ballVelocity,
                                        double robotVelocityY) {
        m_hangTime = xDistance / (ballVelocity * Math.cos(m_angle)); //angle in radians (needs to be)

        m_newXDistance = xDistance - (robotVelocityY * m_hangTime);

        double velocity;
        velocity = getBallVelocity(m_newXDistance);

        return velocity;
    }

    //gets the angle compensation of the turret based on the speed of the robot
    public double getAngleCompensation(double robotVelocityX) {
        double angleCompensation;
        angleCompensation = ((-m_hangTime * robotVelocityX) / m_newXDistance) * (180.0 / Math.PI);
        return angleCompensation; //returns degrees
    }


    public double getEncoderRate() {
        return m_encoder.getRate();
    }

    public double getEncoderDistance() {
        return m_encoder.getDistance();
    }

    //button board stuff -> not used
    //This is for the manual shoot speed change when using the slider
    public double manualShooterSpeedConverter(double sliderValue) {
        double minShooterValue = 14.0;
        double maxShooterValue = 35.0;
        return ((maxShooterValue - minShooterValue) * (sliderValue - MIN_SLIDER)) / (MAX_SLIDER - MIN_SLIDER);
    }

    public double getIncrementValue(double sliderValue) {
        double minIncrementValue = -2.0;
        double maxIncrementValue = 2.0;
        return ((maxIncrementValue - minIncrementValue) * (sliderValue - MIN_SLIDER)) / (MAX_SLIDER - MIN_SLIDER);
    }

}
