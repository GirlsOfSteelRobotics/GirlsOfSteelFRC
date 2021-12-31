package com.gos.rebound_rumble.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import com.gos.rebound_rumble.RobotMap;
import com.gos.rebound_rumble.objects.EncoderGoSPIDController;

@SuppressWarnings({"PMD.AvoidReassigningParameters", "PMD.GodClass", "PMD.TooManyMethods", "PMD.TooManyFields"})
public class Chassis extends Subsystem {

    private static final double WHEEL_DIAMETER = 0.1524; //in meters
    private static final double GEAR_RATIO = 15.0 / 24.0;
    private static final double PULSES_RIGHT = 250.0;
    private static final double PULSES_LEFT = 360.0;
    private static final double ENCODER_UNIT_RIGHT = (WHEEL_DIAMETER * Math.PI * GEAR_RATIO * 1.065) / PULSES_RIGHT;//m per s
    private static final double ENCODER_UNIT_LEFT = (WHEEL_DIAMETER * Math.PI * GEAR_RATIO * 1.07) / PULSES_LEFT;
    private static final double ROBOT_DIAMETER = 0.8128; //the radius*2 of the circle the robot makes while turning in place

    //rate p & i & d values -> same for the left & right -> tuned for PIT comp
    private static final double rateP = 0.75;
    private static final double rateI = 0.1;
    private static final double rateD = 0.0;
    private static final double positionRightP = 0.48;//0.415;//practice bot:0.48;
    private static final double positionRightI = 0.0;
    private static final double positionRightD = 0.1;
    private static final double positionLeftP = 0.48;//0.4;//practice bot:0.48;
    private static final double positionLeftI = 0.0;
    private static final double positionLeftD = 0.1;

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
    private static final double EPSILON = 0.05;//the ewrror range for PID position control

    //driving -> ALL unnecessary right now, do VERY LAST if everything is working gorgeously
    private static final double MAX_RATE = 5; //TODO find the max rate of the chassis
    private static final double MAX_ACCELERATION = 0.5; //TODO find the max acceleration of the chassis

    //create Jags
    private final Jaguar m_rightJags = new Jaguar(RobotMap.RIGHT_JAGS);
    private final Jaguar m_leftJags = new Jaguar(RobotMap.LEFT_JAGS);
    //create gyro
    private final Gyro m_gyro = new AnalogGyro(RobotMap.GYRO_RATE_ANALOG);
    //create stuff for encoders
    //CHANGE FOR REAL WATSON:
    private final Encoder m_rightEncoder = new Encoder(RobotMap.ENCODER_RIGHT_CHANNEL_A,
        RobotMap.ENCODER_RIGHT_CHANNEL_B, false, CounterBase.EncodingType.k4X);

    private final Encoder m_leftEncoder = new Encoder(RobotMap.ENCODER_LEFT_CHANNEL_A,
        RobotMap.ENCODER_LEFT_CHANNEL_B, true, CounterBase.EncodingType.k4X);
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

    private final EncoderGoSPIDController m_rightRatePID = new EncoderGoSPIDController(rateP,
        rateI, rateD, m_rightEncoder,
        //this is an anonymous class, it lets us send values to both jags
        //new output parameter
        new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                setRightJags(output);
            }
        }, EncoderGoSPIDController.RATE, INTEGRAL_THRESHOLD);
    private final EncoderGoSPIDController m_leftRatePID = new EncoderGoSPIDController(rateP,
        rateI, rateD, m_leftEncoder,
        //this is an anonymous class, it lets us send values to both jags
        //new output parameter
        new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                setLeftJags(output);
            }
        }, EncoderGoSPIDController.RATE, INTEGRAL_THRESHOLD);
    private final EncoderGoSPIDController m_rightPositionPID = new EncoderGoSPIDController(
        positionRightP, positionRightI, positionRightD, m_rightEncoder,
        new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                setRightJags(output);
            }
        }, EncoderGoSPIDController.POSITION);
    private final EncoderGoSPIDController m_leftPositionPID = new EncoderGoSPIDController(
        positionLeftP, positionLeftI, positionLeftD, m_leftEncoder,
        new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                setLeftJags(output);
            }
        }, EncoderGoSPIDController.POSITION);

    //again, make sure you can find this easily at comp
    //^^max acceleration -> used in the acceleration limiting method on the PID controllers
    private double m_previousTime;
    private double m_currentTime;
    private double m_changeInTime = (m_currentTime - m_previousTime);
    private double m_setPointVelocityR;
    private double m_setPointVelocityL;
    private double m_currentVelocityR;
    private double m_currentVelocityL;
    private double m_desiredChangeInVelocityR;
    private double m_desiredChangeInVelocityL;

    public Chassis() {
        resetGyro();
    }

    @Override
    protected void initDefaultCommand() {
    }

    //jags
    public void setRightJags(double speed) {
        m_rightJags.set(speed);
    }

    public void setLeftJags(double speed) {
        m_leftJags.set(-speed * 0.93);//motors need to run the same way as the right
    }

    public void stopJags() {
        setRightJags(0.0);
        setLeftJags(0.0);
    }

    //yAxis -> positive jag speed might be backwards

    public void driveJagsLinearSlowTurning(double xAxis, double yAxis, double turningScale) {
        xAxis = deadzone(xAxis, DEADZONE_RANGE) * turningScale;
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
    @SuppressWarnings("PMD.UnusedAssignment")
    public void driveSlowVelocity(double xAxis, double yAxis) {
        yAxis = mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), SLOW_MAX_RATE);
        xAxis = mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), SLOW_MAX_RATE);
        m_rightRatePID.setSetPoint((yAxis - xAxis));
        m_leftRatePID.setSetPoint((yAxis + xAxis));
    }

    public void initHoldPosition() {
        setPositionPIDValues(10.0, 0.0, 10.0, 0.0);//righP,rightD,leftP,leftD
        m_rightPositionPID.enable();
        m_leftPositionPID.enable();
    }

    public void holdPosition() {
        setPositionPIDSetPoint(0.0);
    }

    public void nudge(double xValue) {
        double setPoint = 0.0;
        setPoint = setPoint + xValue * 0.0025;
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
        m_rightRatePID.setSetPoint((yAxis - xAxis));
        m_leftRatePID.setSetPoint((yAxis + xAxis));
    }

    public void driveVelocitySqrt(double xAxis, double yAxis, double scale) {

        xAxis = sqrt(mappingJoystickToRate(deadzone(xAxis, DEADZONE_RANGE), MAX_RATE), scale);
        yAxis = -sqrt(mappingJoystickToRate(deadzone(yAxis, DEADZONE_RANGE), MAX_RATE), scale);

        // sets the SetPoint by limiting the accleration when change in velocity is beyond a range
        accelerationLimit(xAxis, yAxis);

    }

    private void accelerationLimit(double xAxis, double yAxis) {
        m_currentTime = System.currentTimeMillis() / 1000; //turns to seconds
        //the current rate needs to accurate with the robot -> gotten from encoders
        m_currentVelocityR = getRightEncoderRate();
        m_currentVelocityL = getLeftEncoderRate();
        //set point velocity from the joystick
        m_setPointVelocityL = yAxis + xAxis; //normal set point equation
        m_setPointVelocityR = yAxis - xAxis; //normal set point equation
        m_changeInTime = m_currentTime - m_previousTime;
        //previousTime starts at 0 -> when this runs, changes to last currentTime
        m_desiredChangeInVelocityR = m_setPointVelocityR - m_currentVelocityR; //prvious starts at 0 -> gets set to current later
        m_desiredChangeInVelocityL = m_setPointVelocityL - m_currentVelocityL; //same as above
        if (m_changeInTime != 0) {
            // checking for right rate PID
            if ((Math.abs(m_desiredChangeInVelocityR) / m_changeInTime) <= MAX_ACCELERATION / m_changeInTime) {
                m_rightRatePID.setSetPoint(m_setPointVelocityR);
                //^^if the desiredChange is not over the max acceleration -> just
                //^^set it to what it wants to be
                //if the desiredChange is over the max (or under the negative max)
                //it will just add the max (or subtract) acceleration
            } else if (m_desiredChangeInVelocityR / m_changeInTime > MAX_ACCELERATION / m_changeInTime) {
                m_rightRatePID.setSetPoint(m_currentVelocityR + MAX_ACCELERATION);
            } else if (m_desiredChangeInVelocityR / m_changeInTime < -MAX_ACCELERATION / m_changeInTime) {
                m_rightRatePID.setSetPoint(m_currentVelocityR - MAX_ACCELERATION);
            }
            // now for the left side
            if ((Math.abs(m_desiredChangeInVelocityL) / m_changeInTime) <= MAX_ACCELERATION / m_changeInTime) {
                m_leftRatePID.setSetPoint(m_setPointVelocityL);
            } else if (m_desiredChangeInVelocityL / m_changeInTime > MAX_ACCELERATION / m_changeInTime) {
                m_leftRatePID.setSetPoint(m_currentVelocityL + MAX_ACCELERATION);
            } else if (m_desiredChangeInVelocityL / m_changeInTime < -MAX_ACCELERATION / m_changeInTime) {
                m_leftRatePID.setSetPoint(m_currentVelocityL - MAX_ACCELERATION);
            }
        }
        m_previousTime = m_currentTime;
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

    /**
     * if the joystickValue in the range of the joystick where we want it to not move
     * then the new joystick value is the scaled by the deadZoneRange slope
     *
     * @param double joystickValue, @param double deadZoneRange
     * @return newJoystickValue
     */
    private double deadzone(double joystickValue, double deadZoneRange) {
        double newJoystickValue;
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

    public void goToLocation(double x, double y, double degreesToFace) {
        double degreesToTurn = Math.atan2(y, x) * 180 / Math.PI;
        double distanceToMove = Math.sqrt((x * x) + (y * y));
        turn(degreesToTurn);
        move(distanceToMove);
        turn(degreesToFace - degreesToTurn);
    }

    //autonomous move method
    public void move(double distance) {
        setPositionPIDSetPoint(distance);
    }

    //autonomous turning method
    public void turn(double degreesToTurn) {
        double setPoint = convertDegreesToPositionChange(degreesToTurn);
        if (setPoint > 0) {
            m_rightPositionPID.setSetPoint(-setPoint);
            m_leftPositionPID.setSetPoint(setPoint);
        } else if (setPoint < 0) {
            m_rightPositionPID.setSetPoint(setPoint);
            m_leftPositionPID.setSetPoint(-setPoint);
        }
    }

    private double convertDegreesToPositionChange(double degreesToTurn) {
        if (degreesToTurn > 180) {//if the degree change is greater than 180 (farthest
            //turn to the right) -> it makes the degree change between -180 & 180
            double n = Math.floor((degreesToTurn + 180) / 360);
            degreesToTurn = -(360 * n);
        }
        double positionChange;
        positionChange = degreesToTurn * (ROBOT_DIAMETER * Math.PI / 360.0);
        //changes the degree change to how much the robot should turn to face
        //the degree change
        return positionChange;
    }

    //checks if go to location is finished
    public boolean isGoToLocationFinished(double xDistance, double yDistance) {
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
        double degrees = Math.atan2(yDistance, xDistance) * 180 / Math.PI;
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
        m_rightRatePID.setSetPoint(0.0);
        m_leftRatePID.setSetPoint(0.0);
        m_rightRatePID.enable();
        m_leftRatePID.enable();
    }

    public void initPositionPIDs() {
        m_rightPositionPID.setSetPoint(0.0);
        m_leftPositionPID.setSetPoint(0.0);
        m_rightPositionPID.enable();
        m_leftPositionPID.enable();
    }

    public void setRatePIDValues(double p, double i, double d) {
        m_rightRatePID.setPID(p, i, d);
        m_leftRatePID.setPID(p, i, d);
    }

    public void setPositionPIDValues(double rightP, double rightD, double leftP,
                                     double leftD) {
        m_rightPositionPID.setPID(rightP, positionRightI, rightD);
        m_leftPositionPID.setPID(leftP, positionLeftI, leftD);
    }

    public void setPIDsPosition() {
        m_rightPositionPID.setPID(positionRightP, positionRightI, positionRightD);
        m_leftPositionPID.setPID(positionLeftP, positionLeftI, positionLeftD);
    }

    public void setPIDsRate() {
        m_rightRatePID.setPID(rateP, rateI, rateD);
        m_leftRatePID.setPID(rateP, rateI, rateD);
    }

    public void setRatePIDSetPoint(double setPoint) {
        m_rightRatePID.setSetPoint(setPoint);
        m_leftRatePID.setSetPoint(setPoint);
    }

    public void setPositionPIDSetPoint(double setPoint) {
        m_rightPositionPID.setSetPoint(setPoint);
        m_leftPositionPID.setSetPoint(setPoint);
    }

    public void disableRatePIDs() {
        m_rightRatePID.disable();
        m_leftRatePID.disable();
        stopJags();
    }

    public void disablePositionPIDs() {
        m_rightPositionPID.disable();
        m_leftPositionPID.disable();
        stopJags();
    }

    //encoder
    public void initEncoders() {
        m_rightEncoder.setDistancePerPulse(ENCODER_UNIT_RIGHT);
        m_leftEncoder.setDistancePerPulse(ENCODER_UNIT_LEFT);
//        rightEncoder.setDistancePerPulse(ENCODER_UNIT_RIGHT);
//        leftEncoder.setDistancePerPulse(ENCODER_UNIT_LEFT);
    }

    public double getRightEncoderDistance() {
        return m_rightEncoder.getDistance();
    }

    public double getLeftEncoderDistance() {
        return m_leftEncoder.getDistance();
    }

    public double getRightEncoderRate() {
        return m_rightEncoder.getRate();
    }

    public double getLeftEncoderRate() {
        return m_leftEncoder.getRate();
    }

    public void resetEncoders() {
        m_rightEncoder.reset();
        m_leftEncoder.reset();
    }

    public void endEncoders() {
    }

    public boolean isMoving() {
        //if the encoder rates are 0 the robot is not moving
        return m_rightEncoder.getRate() != 0.0 || m_leftEncoder.getRate() != 0.0;
    }

    public final void resetGyro() {
        m_gyro.reset();
    }

    public double getTheta() {
        double theta;
        theta = m_gyro.getAngle();
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
