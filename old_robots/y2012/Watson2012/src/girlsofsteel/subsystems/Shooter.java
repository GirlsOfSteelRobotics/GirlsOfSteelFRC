package girlsofsteel.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.util.SortedVector;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.Camera;
import girlsofsteel.objects.EncoderGoSPIDController;
import girlsofsteel.objects.MapDouble;
import girlsofsteel.objects.MapDoubleComparator;
import girlsofsteel.objects.SmoothEncoder;
//import girlsofsteel.commands.MiddleCollectorsForward;

public class Shooter extends Subsystem {

    public static final double KEY_SPEED = 24.0;//dead-reckoning speed to use for
    //shooting with the key
    public static final double BRIDGE_SPEED = 29.0;//MAGIC value for speed when
    //shooting from the bridge button board stufficles
    private static final double MIN_SLIDER = 0.0;
    private static final double MAX_SLIDER = 3.15;

    public static final double VELOCITY_ERROR_RANGE = 1.0;//this is the speed that the
    //shooter wheel can be off by before it shoots the ball
    //hopefully this can be lowered once the PID is tuned nicely
    private static final double SCALE_TOP_ROLLERS_OFF = 5.0;

    private static final double MAX_SHOOTER_VELOCITY = 41.0;
    private static final double PID_OUTPUT_THRESHOLD = 0.05;//change in voltage

    //constants -> meters if a distance
    private static final double TOP_HOOP_HEIGHT = 2.4892;
    private static final double ROBOT_HEIGHT = 1.2065;//floor to the axis of the shooter wheel
    private static final double WHEEL_DIAMETER = 0.2032;//shooter wheel diameter
    private static final double GEAR_RATIO = 1.0;//gear ratio of the shooter wheel
    private static final double PULSES = 100.0;
    private static final double ENCODER_UNIT = (Math.PI * WHEEL_DIAMETER * GEAR_RATIO) / PULSES;
    //don't change this! ^^ just a formula
    private double angle;
    private final double yDistance = TOP_HOOP_HEIGHT - ROBOT_HEIGHT; //the vertical distance
    //from the shooter to the top basket
    private double hangTime;
    private double newXDistance;
    private final Jaguar jags = new Jaguar(RobotMap.SHOOTER_JAGS);
//    CHANGE FOR REAL WATSON:
//    public Encoder encoder = new Encoder(RobotMap.ENCODER_SHOOTER_CHANNEL_A,
//            RobotMap.ENCODER_SHOOTER_CHANNEL_B, true,
//            CounterBase.EncodingType.k4X);
//    private Relay topRollers = new Relay(RobotMap.TOP_ROLLER_SPIKE);
//    PRACTICE WATSANNE:
    public Encoder encoder = new SmoothEncoder(RobotMap.ENCODER_SHOOTER_CHANNEL_A,
            RobotMap.ENCODER_SHOOTER_CHANNEL_B, true,
            CounterBase.EncodingType.k4X);
    private final Relay topRollersSpike = new Relay(RobotMap.TOP_ROLLER_SPIKE);
    private final double p = 0.4;//0.25;//0.25;//0.15;//0.1;//0.25;
    private final double i = 50.0;//2.5;//0.06;//0.0002;
    private final double d = 0.0;
    //integral threshold -> cuts of the value that is being multipled by the i term
    //with the PID output
    private final double INTEGRAL_THRESHOLD = 2500.0; //errorSum usually stabilized around 1700
    private final EncoderGoSPIDController PID = new EncoderGoSPIDController(p, i, d, encoder,
            new PIDOutput() {

                @Override
                public void pidWrite(double output) {
                    setJags(output);
                }
            }, EncoderGoSPIDController.RATE,INTEGRAL_THRESHOLD);

    //table sorting shooter experimental values calculator
    private  final SortedVector.Comparator comparator = new MapDoubleComparator();
    //Sorted array sorts greatest to least
    private final SortedVector list = new SortedVector(comparator);

    public Shooter(){
        populate();//adds all the values in the table below to the shooting table
        //to find distances based on data
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void setJags(double speed) {
        jags.set(-speed);
    }

    public void stopJags() {
        setJags(0.0);
    }

    public void topRollersForward() {
        topRollersSpike.set(Relay.Value.kForward);
    }

    public void topRollersBackward() {
        topRollersSpike.set(Relay.Value.kReverse);
    }

    public void topRollersOff() {
        topRollersSpike.set(Relay.Value.kOff);
    }

    public void initEncoder() {
        encoder.setDistancePerPulse(ENCODER_UNIT);
           }

    public void stopEncoder() {
           }

    public void initPID() {
        PID.setOutputThreshold(PID_OUTPUT_THRESHOLD);
        PID.enable();
    }

    public void setPIDSpeed(double speed) {
        PID.setSetPoint(speed);
    }

    //re-assigns the P & I values -> the D value is always 0.0 because it is a
    //rate PID controller
    public void setPIDValues(double p, double i, double d){
        PID.setPID(p, i, d);
    }

    //this is for the weird bug in having to reassign PID values in execute
    //must be called in execute before anything else if using the shooter PID
    public void setPIDValues(){
        PID.setPID(p, i, d);
    }

    public double getPIDSetPoint(){
        return PID.getSetPoint();
    }

    //if the shooter wheel is within the set point range it will return true
    //used to decide if the top rollers should be run or not
    public boolean isWithinSetPoint(double setPoint) {
        if(setPoint == 0){//set point will be false if you are not getting a velocity
            return false; //will not run the top rollers if the wheel is not run
        }else{
            return (PID.getRate() > setPoint - VELOCITY_ERROR_RANGE
                && PID.getRate() < setPoint + VELOCITY_ERROR_RANGE);
        }
    }

    public boolean isWithinSetPoint(double setPoint, double errorRange) {
        if(setPoint == 0){//set point will be false if you are not getting a velocity
            return false; //will not run the top rollers if the wheel is not run
        }else{
            return (PID.getRate() > setPoint - errorRange
                && PID.getRate() < setPoint + errorRange);
        }
    }

    public void disablePID() {
        PID.disable();
    }

    public void resetPIDError(){
        PID.resetError();
    }

    public double getDistance(){//from the fender to the camera -> in meters
        return Camera.getXDistance() - 38*(0.0254/1.0);//fender is 38inches
    }

    public void shoot(double speed){
        setPIDValues();
        setPIDSpeed(speed);
        if(isWithinSetPoint(speed)){
            topRollersForward();
        }else{
            if(!isWithinSetPoint(speed, SCALE_TOP_ROLLERS_OFF*VELOCITY_ERROR_RANGE)){
                topRollersOff();
            }
        }
    }

    public void autoShoot(double cameraDistance){
        setPIDValues();
        double velocity;
        velocity = getVelocityFrTable(cameraDistance);
        System.out.println(cameraDistance + "       velocity:  " + velocity);
        setPIDSpeed(velocity);
        if(isWithinSetPoint(velocity)){
            topRollersForward();
        }else{
            if(!isWithinSetPoint(velocity, SCALE_TOP_ROLLERS_OFF*VELOCITY_ERROR_RANGE)){
                topRollersOff();
            }
        }
    }

    public void autoShootBestFitLine(double cameraDistance){
        setPIDValues();
        double velocity;
        velocity = 2.16*cameraDistance + 14.25;
        setPIDSpeed(velocity);
        if(isWithinSetPoint(velocity)){
            topRollersForward();
        }else{
            if(!isWithinSetPoint(velocity, SCALE_TOP_ROLLERS_OFF*VELOCITY_ERROR_RANGE)){
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
        PID.setSetPoint(newVelocity);
    }

    //shoots using a direct velocity (of the wheel)
    public void shootUsingVelocity(double velocity) {
        double newVelocity = velocity;
        PID.setSetPoint(newVelocity);
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
        velocity = ((xDistance * Math.sqrt(9.8 / ((2 * (xDistance * Math.cos(angle))) - yDistance))) / Math.tan(angle)) * (5.5 / 4.0);
        return velocity;
    }

    private void calculateAngle(double xDistance) {
        angle = MathUtils.atan(yDistance / xDistance);//radians
    }

    //the compensation that the speed of the ball has to go based on the
    //robot velocity -> new distances of being shot
    private double getSpeedCompensation(double xDistance, double ballVelocity,
            double robotVelocityY) {
        hangTime = xDistance / (ballVelocity * Math.cos(angle));//angle in radians (needs to be)

        newXDistance = xDistance - (robotVelocityY * hangTime);

        double velocity;
        velocity = getBallVelocity(newXDistance);

        return velocity;
    }

    //gets the angle compensation of the turret based on the speed of the robot
    public double getAngleCompensation(double robotVelocityX) {
        double angleCompensation;
        angleCompensation = ((-hangTime * robotVelocityX) / newXDistance) * (180.0 / Math.PI);
        return angleCompensation;//returns degrees
    }


    //enters shooter data into the function that calculates the velocity the
    //ball should be shot at
    public void populate() {
        //new data (4/20/12):

        list.addElement(new MapDouble(24.1,193.5));
        list.addElement(new MapDouble(2.87,20.75));
        list.addElement(new MapDouble(3.175,21.3));
        list.addElement(new MapDouble(3.785,21.9));
        list.addElement(new MapDouble(4.394,23.7));
        list.addElement(new MapDouble(5.004,24.82));
        list.addElement(new MapDouble(5.613,26.7));
        list.addElement(new MapDouble(6.223,27.7));
        list.addElement(new MapDouble(6.68,28.8));

        //old data:
//        list.addElement(new MapDouble(0.0, 0.0));
//        list.addElement(new MapDouble(2.2733, 20.15));
//        list.addElement(new MapDouble(2.4257,20.5));
//        list.addElement(new MapDouble(2.5781,21.25));
//        list.addElement(new MapDouble(2.7305,21.75));
//        list.addElement(new MapDouble(2.8829,22.0));
//        list.addElement(new MapDouble(3.0353,22.5));
//        list.addElement(new MapDouble(3.1877,23.15));
//        list.addElement(new MapDouble(3.3401,23.25));
//        list.addElement(new MapDouble(3.4925,23.4));
//        list.addElement(new MapDouble(3.6449,24.1));
//        list.addElement(new MapDouble(3.7973,24.525));
//        list.addElement(new MapDouble(3.9497,24.9));
//        list.addElement(new MapDouble(4.1021,25.14));
//        list.addElement(new MapDouble(4.2161,25.3));
//        list.addElement(new MapDouble(4.4101,25.6));
//        list.addElement(new MapDouble(4.5593,26.25));
//        list.addElement(new MapDouble(4.7117,26.8));
//        list.addElement(new MapDouble(4.8641,27.2));
//        list.addElement(new MapDouble(5.0165,27.6));
//        list.addElement(new MapDouble(5.1689,27.8));
//        list.addElement(new MapDouble(5.3213,28.1));
//        list.addElement(new MapDouble(5.4637,28.4));
    }

    /**
     * @param distance, needs to get here from the camera*-
     * @return
     */
    public double getVelocityFrTable(double distance) {

        if (list.size() == 0) {
            return 0;//ends the getVelocityFrTable method -> sends a velocity of 0
        }


        //Sorted array is sorted greatest to least
        //assigns the last value to the lowest point -> and the first to the last
        MapDouble lowest = (MapDouble) list.lastElement();
        MapDouble highest = (MapDouble) list.firstElement();

        //PLEASE make a test point SUPER close to the basket
        if (distance < lowest.getDistance()) {//if the distance is lower than the
            //lowest point -> then it will return the velocity of the lowest point
            //in the table
            //draws a line from the origin to the lowest point -> finds the velocity
            //based on the distance
            return interpolate(distance, 0, 0, lowest.getDistance(), lowest.getVelocity());
        } //ALSO PLEASE make a test point on the other side or as close to the bridge as possible
        else if (distance > highest.getDistance()) {//same as above, but for a
            //higher number of the highest point in the table
            //creates a line between the highest point and a value that we won't reach
            //returns the velocity on this line -> at the highest
//            return interpolate(distance, highest.getDistance(), highest.getVelocity(), highest.getDistance() + 5, MAX_SHOOTER_VELOCITY);
            return MAX_SHOOTER_VELOCITY;//if the distance is above anything in
            //the table -> shoot at the highest the velocity
        }

        int index = (int) Math.ceil(list.size() / 2.0);
        MapDouble currentValue = (MapDouble) list.elementAt(index);
        MapDouble currentLow = new MapDouble(0.0, 0.0);
        MapDouble currentMax = new MapDouble(0.0, 0.0);
        boolean end = false;

        //find the values above & below the distance you're looking for
        while (!end) {
            currentValue = ((MapDouble) list.elementAt(index));

            if (currentValue.getDistance() == currentLow.getDistance() || currentValue.getDistance() == currentMax.getDistance()) {
                end = true;
            }


            if (currentValue.getDistance() < distance) {
                currentLow = currentValue;
                index = index - 1;
            } else if (currentValue.getDistance() > distance) {
                currentMax = currentValue;
                index = index + 1;
            } else {
                return currentValue.getVelocity();
            }
        }

        //assigns the distances & velocities
        double distance1 = currentLow.getDistance();
        double velocity1 = currentLow.getVelocity();
        double distance2 = currentMax.getDistance();
        double velocity2 = currentMax.getVelocity();
        //finds the velocity needed based on the distance
        return interpolate(distance, distance1, velocity1, distance2, velocity2);
    }

    //uses the distance that the camera is at (distance)
    //and the 2 sets of data that the distance lies between (distance & velocity)
    //to find the velocity the shooter wheel should be set at
    private double interpolate(double distance, double distance1, double velocity1, double distance2, double velocity2) {
        double velocity = 0.0;
        velocity = velocity1 + (velocity2 - velocity1) * ((distance - distance1) / (distance2 - distance1));
        return velocity;
    }

    public double getEncoderRate() {
        return encoder.getRate();
    }

    public double getEncoderDistance() {
        return encoder.getDistance();
    }

    //button board stuff -> not used
    //This is for the manual shoot speed change when using the slider
    public double manualShooterSpeedConverter(double sliderValue) {
        double minShooterValue = 14.0;
        double maxShooterValue = 35.0;
        double speed = ((maxShooterValue - minShooterValue) * (sliderValue - MIN_SLIDER)) / (MAX_SLIDER - MIN_SLIDER); //Shooter value
        return speed;
    }

    public double getIncrementValue(double sliderValue){
        double minIncrementValue = -2.0;
        double maxIncrementValue = 2.0;
        double incrementValue = ((maxIncrementValue - minIncrementValue)*(sliderValue-MIN_SLIDER)) / (MAX_SLIDER - MIN_SLIDER);
        return incrementValue;
    }

}
