package girlsofsteel.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.EncoderGoSPIDController;

public class Chassis extends Subsystem {

    public static final double DISTANCE_BACKBOARD_TO_BRIDGE = 7.0104;
    //backboard to where we need to be to push the bridge down
    public static final double DISTANCE_KEY_TO_BRIDGE = 2.03;
    //set place in the key (where we have dead-reckoning) to where we need to be
    //to push the bridge down

    private static final double SLOW_MAX_RATE = 0.5;//the slowest rate in m/s
    //used for the bridge velocity PID control

    public static final double DEADZONE_RANGE = 0.3;
    //deadzone range for the driver's controller
    //TODO find the integral threshold for the chassis's rate PIDs
    private static final double INTEGRAL_THRESHOLD = 999999999;
    //how to: print out the errorSum & see where it levels off, make this a bit higher
    //highest error acculumation amount
    private final double EPSILON = 0.05;//the ewrror range for PID position control
    //create Jags
    private final Jaguar rightJags = new Jaguar(RobotMap.RIGHT_JAGS);
    private final Jaguar leftJags = new Jaguar(RobotMap.LEFT_JAGS);
    //create gyro
    private final Gyro gyro = new AnalogGyro(RobotMap.GYRO_RATE_ANALOG);
    //create stuff for encoders
    //CHANGE FOR REAL WATSON:
    private final Encoder rightEncoder = new Encoder(RobotMap.ENCODER_RIGHT_CHANNEL_A,
            RobotMap.ENCODER_RIGHT_CHANNEL_B, false, CounterBase.EncodingType.k4X);

    private final Encoder leftEncoder = new Encoder(RobotMap.ENCODER_LEFT_CHANNEL_A,
            RobotMap.ENCODER_LEFT_CHANNEL_B, true, CounterBase.EncodingType.k4X);
    private final double WHEEL_DIAMETER = 0.1524; //in meters
    private final double GEAR_RATIO = 15.0 / 24.0;
    private final double PULSES_RIGHT = 250.0;
    private final double PULSES_LEFT = 360.0;
    private final double ENCODER_UNIT_RIGHT = (WHEEL_DIAMETER * Math.PI * GEAR_RATIO*1.065) / PULSES_RIGHT;//m per s
    private final double ENCODER_UNIT_LEFT = (WHEEL_DIAMETER * Math.PI * GEAR_RATIO*1.07) / PULSES_LEFT;
    private final double ROBOT_DIAMETER = 0.8128; //the radius*2 of the circle the robot makes while turning in place
    //practice:
//    private Encoder rightEncoder = new Encoder(RobotMap.ENCODER_RIGHT_CHANNEL_A,
//            RobotMap.ENCODER_RIGHT_CHANNEL_B, true, CounterBase.EncodingType.k4X);
//
//    private Encoder leftEncoder = new Encoder(RobotMap.ENCODER_LEFT_CHANNEL_A,
//            RobotMap.ENCODER_LEFT_CHANNEL_B, true, CounterBase.EncodingType.k4X);
//    private final double PRACTICE_GEAR_RATIO = 12.0 / 22.0;
//    private final double ENCODER_UNIT_RIGHT = (WHEEL_DIAMETER * Math.PI * PRACTICE_GEAR_RATIO) / 360.0;
//    private final double ENCODER_UNIT_LEFT = (WHEEL_DIAMETER * Math.PI * PRACTICE_GEAR_RATIO) / 250.0;

    //create info for PIDs

    //rate p & i & d values -> same for the left & right -> tuned for PIT comp
    private final static double rateP = 0.75;
    private final static double rateI = 0.1;
    private final static double rateD = 0.0;
    private final static double positionRightP = 0.48;//0.415;//practice bot:0.48;
    private final static double positionRightI = 0.0;
    private final static double positionRightD = 0.1;
    private final static double positionLeftP = 0.48;//0.4;//practice bot:0.48;
    private final static double positionLeftI = 0.0;
    private final static double positionLeftD = 0.1;
    private final EncoderGoSPIDController rightRatePID = new EncoderGoSPIDController(rateP,
            rateI, rateD, rightEncoder,
            //this is an anonymous class, it lets us send values to both jags
            //new output parameter
            new PIDOutput() {

        @Override
        public void pidWrite(double output) {
            setRightJags(output);
            }
        }, EncoderGoSPIDController.RATE,INTEGRAL_THRESHOLD);
    private final EncoderGoSPIDController leftRatePID = new EncoderGoSPIDController(rateP,
            rateI, rateD, leftEncoder,
            //this is an anonymous class, it lets us send values to both jags
            //new output parameter
            new PIDOutput() {

        @Override
        public void pidWrite(double output) {
            setLeftJags(output);
            }
        }, EncoderGoSPIDController.RATE,INTEGRAL_THRESHOLD);
    private final EncoderGoSPIDController rightPositionPID = new EncoderGoSPIDController(
            positionRightP, positionRightI, positionRightD, rightEncoder,
            new PIDOutput() {

                @Override
                public void pidWrite(double output) {
                    setRightJags(output);
                }
            }, EncoderGoSPIDController.POSITION);
    private final EncoderGoSPIDController leftPositionPID = new EncoderGoSPIDController(
            positionLeftP, positionLeftI, positionLeftD, leftEncoder,
            new PIDOutput() {

                @Override
                public void pidWrite(double output) {
                    setLeftJags(output);
                }
            }, EncoderGoSPIDController.POSITION);
    //driving -> ALL unnecessary right now, do VERY LAST if everything is working gorgeously
    private static final double MAX_RATE = 5; //TODO find the max rate of the chassis
    private final static double MAX_ACCELERATION = 0.5; //TODO find the max acceleration of the chassis
    //again, make sure you can find this easily at comp
    //^^max acceleration -> used in the acceleration limiting method on the PID controllers
    private double previousTime = 0.0;
    private double currentTime = 0.0;
    private double changeInTime = (currentTime - previousTime);
    private double setPointVelocityR = 0.0;
    private double setPointVelocityL = 0.0;
    private double currentVelocityR = 0.0;
    private double currentVelocityL = 0.0;
    private double desiredChangeInVelocityR = 0.0;
    private double desiredChangeInVelocityL = 0.0;

    public Chassis() {
        resetGyro();
    }

    @Override
    protected void initDefaultCommand() {
    }

    //jags
    public void setRightJags(double speed) {
        rightJags.set(speed);
    }

    public void setLeftJags(double speed) {
        leftJags.set(-speed*0.93);//motors need to run the same way as the right
    }

    public void stopJags() {
        setRightJags(0.0);
        setLeftJags(0.0);
    }

    //yAxis -> positive jag speed might be backwards

    public void driveJagsLinearSlowTurning(double xAxis,double yAxis, double turningScale){
        xAxis = deadzone(xAxis, DEADZONE_RANGE)*turningScale;
        yAxis = -deadzone(yAxis, DEADZONE_RANGE);

        setRightJags((yAxis - xAxis));
        setLeftJags((yAxis + xAxis));
    }

    public void driveJagsSquared(double xAxis, double yAxis, double scale) {

        xAxis = square(deadzone(xAxis, DEADZONE_RANGE), scale);
        yAxis = -square(deadzone(yAxis, DEADZONE_RANGE), scale);

        setRightJags((yAxis - xAxis));
        setLeftJags((yAxis + xAxis));

    }

    public void driveJagsLinear(double xAxis, double yAxis) {

        xAxis = deadzone(xAxis, DEADZONE_RANGE);
        yAxis = -deadzone(yAxis, DEADZONE_RANGE);

        setRightJags((yAxis - xAxis));
        setLeftJags((yAxis + xAxis));

    }

    public void driveJagsSqrt(double xAxis, double yAxis, double scale) {

        xAxis = sqrt(deadzone(xAxis, DEADZONE_RANGE), scale);
        yAxis = -sqrt(deadzone(yAxis, DEADZONE_RANGE), scale);

        setRightJags((yAxis - xAxis));
        setLeftJags((yAxis + xAxis));

    }

    //limits the set point for the rate of the PID controller to the slow max rate
    public void driveSlowVelocity(double xAxis, double yAxis) {
        yAxis = mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), SLOW_MAX_RATE);
        xAxis = mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), SLOW_MAX_RATE);
        rightRatePID.setSetPoint((yAxis - xAxis));
        leftRatePID.setSetPoint((yAxis + xAxis));
    }

    public void initHoldPosition(){
        setPositionPIDValues(10.0, 0.0, 10.0, 0.0);//righP,rightD,leftP,leftD
        rightPositionPID.enable();
        leftPositionPID.enable();
    }

    public void holdPosition(){
        setPositionPIDSetPoint(0.0);
    }

    public void nudge(double xValue){
        double setPoint = 0.0;
        setPoint = setPoint + xValue*0.0025;
        setPositionPIDSetPoint(setPoint);
    }

    public void driveVelocitySquared(double xAxis, double yAxis, double scale) {

        double maxRate = Math.sqrt(MAX_RATE);

        xAxis = square(mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), maxRate), scale);
        yAxis = -square(mappingJoystickToRate(deadzone(yAxis, DEADZONE_RANGE), maxRate), scale);

        // sets the SetPoint by limiting the accleration when change in velocity is beyond a range
        accelerationLimit(xAxis, yAxis);

    }

    public void driveVelocityLinear(double xAxis, double yAxis) {

        xAxis = mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), MAX_RATE);
        yAxis = -mappingJoystickToRate(deadzone(yAxis, DEADZONE_RANGE), MAX_RATE);

        // sets the SetPoint by limiting the accleration when change in velocity is beyond a range
        //accelerationLimit(xAxis, yAxis);
        rightRatePID.setSetPoint((yAxis - xAxis));
        leftRatePID.setSetPoint((yAxis + xAxis));
    }

    public void driveVelocitySqrt(double xAxis, double yAxis, double scale) {

        xAxis = sqrt(mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), MAX_RATE), scale);
        yAxis = -sqrt(mappingJoystickToRate(deadzone(yAxis, DEADZONE_RANGE), MAX_RATE), scale);

        // sets the SetPoint by limiting the accleration when change in velocity is beyond a range
        accelerationLimit(xAxis, yAxis);

    }

    private void accelerationLimit(double xAxis, double yAxis) {
        currentTime = System.currentTimeMillis() / 1000; //turns to seconds
        //the current rate needs to accurate with the robot -> gotten from encoders
        currentVelocityR = getRightEncoderRate();
        currentVelocityL = getLeftEncoderRate();
        //set point velocity from the joystick
        setPointVelocityL = yAxis + xAxis; //normal set point equation
        setPointVelocityR = yAxis - xAxis; //normal set point equation
        changeInTime = currentTime - previousTime;
        //previousTime starts at 0 -> when this runs, changes to last currentTime
        desiredChangeInVelocityR = setPointVelocityR - currentVelocityR; //prvious starts at 0 -> gets set to current later
        desiredChangeInVelocityL = setPointVelocityL - currentVelocityL; //same as above
        if (changeInTime != 0) {
            // checking for right rate PID
            if ((Math.abs(desiredChangeInVelocityR) / changeInTime) <= MAX_ACCELERATION / changeInTime) {
                rightRatePID.setSetPoint(setPointVelocityR);
                //^^if the desiredChange is not over the max acceleration -> just
                //^^set it to what it wants to be
                //if the desiredChange is over the max (or under the negative max)
                //it will just add the max (or subtract) acceleration
            } else if (desiredChangeInVelocityR / changeInTime > MAX_ACCELERATION / changeInTime) {
                rightRatePID.setSetPoint(currentVelocityR + MAX_ACCELERATION);
            } else if (desiredChangeInVelocityR / changeInTime < -MAX_ACCELERATION / changeInTime) {
                rightRatePID.setSetPoint(currentVelocityR - MAX_ACCELERATION);
            }
            // now for the left side
            if ((Math.abs(desiredChangeInVelocityL) / changeInTime) <= MAX_ACCELERATION / changeInTime) {
                leftRatePID.setSetPoint(setPointVelocityL);
            } else if (desiredChangeInVelocityL / changeInTime > MAX_ACCELERATION / changeInTime) {
                leftRatePID.setSetPoint(currentVelocityL + MAX_ACCELERATION);
            } else if (desiredChangeInVelocityL / changeInTime < -MAX_ACCELERATION / changeInTime) {
                leftRatePID.setSetPoint(currentVelocityL - MAX_ACCELERATION);
            }
        }
        previousTime = currentTime;
    }

    private double square(double joystickValue, double scale) {
        double newJoystickValue;

        if (joystickValue > 1) {
            newJoystickValue = scale * (joystickValue * joystickValue);
        } else {
            newJoystickValue = -scale * (joystickValue * joystickValue);
        }
        return newJoystickValue;
    }

    private double sqrt(double joystickValue, double scale) {
        double newJoystickValue;

        if (joystickValue > 1) {
            newJoystickValue = scale * Math.sqrt(joystickValue);
        } else {
            newJoystickValue = -scale * Math.sqrt(Math.abs(joystickValue));
        }
        return newJoystickValue;
    }

    /**if the joystickValue in the range of the joystick where we want it to not move
     * then the new joystick value is the scaled by the deadZoneRange slope
     * @return newJoystickValue
     * @param double joystickValue, @param double deadZoneRange
     */
    private double deadzone(double joystickValue, double deadZoneRange) {
        double newJoystickValue = 0.0;
        if (joystickValue > deadZoneRange) {
            newJoystickValue = (joystickValue / (1 - deadZoneRange)) - deadZoneRange;
        } else if (joystickValue < (-1 * deadZoneRange)) {
            newJoystickValue = (joystickValue / (1 - deadZoneRange)) + deadZoneRange;
        } else {
            newJoystickValue = 0.0;
        }

        return newJoystickValue;
    }

    private double mappingJoystickToRate(double joystickValue, double maxRate) {
        double newJoystickValue;

        newJoystickValue = joystickValue * maxRate;

        return newJoystickValue;
    }

    public void goToLocation(double x, double y, double degreesToFace){
        double degreesToTurn = MathUtils.atan2(y, x)*180/Math.PI;
        double distanceToMove = Math.sqrt((x*x)+(y*y));
        turn(degreesToTurn);
        move(distanceToMove);
        turn(degreesToFace - degreesToTurn);
    }

    //autonomous move method
    public void move(double distance){
        setPositionPIDSetPoint(distance);
    }

    //autonomous turning method
    public void turn(double degreesToTurn) {
        double setPoint = convertDegreesToPositionChange(degreesToTurn);
        if (setPoint > 0) {
            rightPositionPID.setSetPoint(-setPoint);
            leftPositionPID.setSetPoint(setPoint);
        } else if (setPoint < 0) {
            rightPositionPID.setSetPoint(setPoint);
            leftPositionPID.setSetPoint(-setPoint);
        }
    }

    private double convertDegreesToPositionChange(double degreesToTurn) {
        if(degreesToTurn > 180){//if the degree change is greater than 180 (farthest
            //turn to the right) -> it makes the degree change between -180 & 180
            double n = Math.floor((degreesToTurn+180)/360);
            degreesToTurn =- (360*n);
        }
        double positionChange;
        positionChange = degreesToTurn*(ROBOT_DIAMETER*Math.PI/360.0);
        //changes the degree change to how much the robot should turn to face
        //the degree change
        return positionChange;
    }

    //checks if go to location is finished
    public boolean isGoToLocationFinished(double xDistance, double yDistance) {
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
        double degrees = MathUtils.atan2(yDistance, xDistance)*180/Math.PI;
        if (isMoveFinished(distance)) {
            return isTurnFinished(degrees);
        } else {
            return false;
        }
    }

    public boolean isMoveFinished(double distanceToMove) {
        return isWithinRange(getRightEncoderDistance(), distanceToMove)
            && isWithinRange(getLeftEncoderDistance(), distanceToMove);
    }

    public boolean isTurnFinished(double degreesToTurn) {
        double setPoint = convertDegreesToPositionChange(degreesToTurn);
        return isWithinRange(getRightEncoderDistance(), setPoint)
            && isWithinRange(getLeftEncoderDistance(), setPoint);
    }

    private boolean isWithinRange(double point, double target) {
        return point > target - EPSILON && point < target + EPSILON;
    }

    //PID
    public void initRatePIDs() {
        rightRatePID.setSetPoint(0.0);
        leftRatePID.setSetPoint(0.0);
        rightRatePID.enable();
        leftRatePID.enable();
    }

    public void initPositionPIDs() {
        rightPositionPID.setSetPoint(0.0);
        leftPositionPID.setSetPoint(0.0);
        rightPositionPID.enable();
        leftPositionPID.enable();
    }

    public void setRatePIDValues(double p, double i, double d) {
        rightRatePID.setPID(p, i, d);
        leftRatePID.setPID(p, i, d);
    }

    public void setPositionPIDValues(double rightP, double rightD, double leftP,
            double leftD) {
        rightPositionPID.setPID(rightP,positionRightI,rightD);
        leftPositionPID.setPID(leftP,positionLeftI,leftD);
    }

    public void setPIDsPosition(){
        rightPositionPID.setPID(positionRightP,positionRightI,positionRightD);
        leftPositionPID.setPID(positionLeftP, positionLeftI, positionLeftD);
    }

    public void setPIDsRate(){
        rightRatePID.setPID(rateP, rateI, rateD);
        leftRatePID.setPID(rateP, rateI, rateD);
    }

    public void setRatePIDSetPoint(double setPoint) {
        rightRatePID.setSetPoint(setPoint);
        leftRatePID.setSetPoint(setPoint);
    }

    public void setPositionPIDSetPoint(double setPoint) {
        rightPositionPID.setSetPoint(setPoint);
        leftPositionPID.setSetPoint(setPoint);
    }

    public void disableRatePIDs() {
        rightRatePID.disable();
        leftRatePID.disable();
        stopJags();
    }

    public void disablePositionPIDs() {
        rightPositionPID.disable();
        leftPositionPID.disable();
        stopJags();
    }

    //encoder
    public void initEncoders() {
        rightEncoder.setDistancePerPulse(ENCODER_UNIT_RIGHT);
        leftEncoder.setDistancePerPulse(ENCODER_UNIT_LEFT);
//        rightEncoder.setDistancePerPulse(ENCODER_UNIT_RIGHT);
//        leftEncoder.setDistancePerPulse(ENCODER_UNIT_LEFT);
    }

    public double getRightEncoderDistance() {
        return rightEncoder.getDistance();
    }

    public double getLeftEncoderDistance() {
        return leftEncoder.getDistance();
    }

    public double getRightEncoderRate() {
        return rightEncoder.getRate();
    }

    public double getLeftEncoderRate() {
        return leftEncoder.getRate();
    }

    public void resetEncoders() {
        rightEncoder.reset();
        leftEncoder.reset();
    }

    public void endEncoders() {
    }

    public boolean isMoving() {
        //if the encoder rates are 0 the robot is not moving
        return rightEncoder.getRate() != 0.0 || leftEncoder.getRate() != 0.0;
    }

    public void resetGyro() {
        gyro.reset();
    }

    public double getTheta() {
        double theta;
        theta = gyro.getAngle();
        //the angle returned needs to be between 0 & 360
        theta = theta % 360;
        if (theta < 0) {
            theta = theta + 360;
        }
        return theta;
    }

    //TODO fix these equations -> these are not getting the robot velocity in the x & y directions
    public double getRobotVelocityX(double theta) {
        double robotVelocityX;
        robotVelocityX = Math.sin(theta); //needs to be in radians
        return robotVelocityX;
    }

    public double getRobotVelocityY(double theta) {
        double robotVelocityY;
        robotVelocityY = Math.cos(theta);//needs to be in radians
        return robotVelocityY;
    }
}
