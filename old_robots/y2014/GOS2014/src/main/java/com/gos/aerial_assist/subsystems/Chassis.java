/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 move right side slower to match the left
 */

package com.gos.aerial_assist.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.RobotMap;
import com.gos.aerial_assist.objects.EncoderGoSPidController;
import com.gos.aerial_assist.objects.LspbPidPlanner;

/**
 * @author Heather
 */
@SuppressWarnings({"PMD.AvoidReassigningParameters", "PMD.GodClass", "PMD.TooManyMethods"})
public class Chassis extends SubsystemBase {

    private final Jaguar m_rightJag;
    private final Jaguar m_leftJag;

    //DifferentialDrive drive = new DifferentialDrive(rightJag, leftJag); USE THIS IF DRIVING CODE DOESN'T WORK
    private static final double DEAD_ZONE_SCALE = 0.3;

    private final Encoder m_rightEncoder;
    private final Encoder m_leftEncoder;

    private final EncoderGoSPidController m_leftPositionPID;
    private final EncoderGoSPidController m_rightPositionPID;
    private final LspbPidPlanner m_leftChassisPlanner;
    private final LspbPidPlanner m_rightChassisPlanner;

    //need the p for 2nd robot
    private final double m_kPRight; //Competition chassis gains 0.2
    private static final double RIGHT_KI = 0;
    private static final double RIGHT_KD = 0;
    private final double m_kPpLeft; //Competition chassis gains 0.2
    private static final double LEFT_KI = 0;
    private static final double LEFT_KD = 0;

    //    private double leftDistancePerPulsePosition = (wheelCircumference) / (leftPulsePerRevolution * gearRatio); //(leftPulsePerRevolution * gearRatio) / (wheelCircumference);
    //    private double rightDistancePerPulsePosition = (wheelCircumference) / (rightPulsePerRevolution * gearRatio); //(rightPulsePerRevolution * gearRatio) / (wheelCircumference);
    //
    //Practice bot, 4/19/14 based on raw values after pushing robot forward 5.68 meters
    //Divided the raw values by four because the distances were off by a factor of four
    //    private double leftDistancePerPulsePosition = (5.68 / (24163.5/4.0));
    //    private double rightDistancePerPulsePosition = (5.68 / (25416/4));


    //Competition robot, 4/23/14 pushed the robot forward 1 meter
    //Divided the raw values by four because the distances were off by a factor of four
    private static final double LEFT_DISTANCE_PER_PULSE_POSITION = (1.0 / (4810.4 / 4.0));
    private static final double RIGHT_DISTANCE_PER_PULSE_POSITION = (1.0 / (4957.5 / 4));

    public Chassis() {
        m_leftChassisPlanner = new LspbPidPlanner();
        m_rightChassisPlanner = new LspbPidPlanner();

        m_kPRight = Configuration.RIGHT_POSITION_P;
        m_kPpLeft = Configuration.LEFT_POSITION_P;

        m_rightJag = new Jaguar(RobotMap.RIGHT_JAG_PORT);
        m_leftJag = new Jaguar(RobotMap.LEFT_JAG_PORT);

        //2nd Robot encoder stuff
        m_rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B, Configuration.RIGHT_ENCODER_REVERSE, CounterBase.EncodingType.k4X);
        m_leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B, Configuration.LEFT_ENCODER_REVERSE, CounterBase.EncodingType.k4X);

        /*Old practice bot code (before they switched the ports of right and left
         rightPositionPID = new EncoderGoSPIDController(Ppright, Piright, Pdright, leftEncoder, new PIDOutput() {

         public void pidWrite(double output) {
         rightJag.set(output);
         }
         }, 2, true);

         leftPositionPID = new EncoderGoSPIDController(Ppleft, Pileft, Pdleft, leftEncoder, new PIDOutput() {

         public void pidWrite(double output) {
         leftJag.set(output);
         }
         }, 2, false);

         */
        m_rightPositionPID = new EncoderGoSPidController(m_kPRight, RIGHT_KI, RIGHT_KD, m_rightEncoder, m_rightJag::set, 2, Configuration.RIGHT_PID_REVERSE_ENCODER, false); //For the competition bot (practice bot is true, false)

        m_leftPositionPID = new EncoderGoSPidController(m_kPpLeft, LEFT_KI, LEFT_KD, m_leftEncoder, m_leftJag::set, 2, Configuration.LEFT_PID_REVERSE_ENCODER, false);
    }

    public double square(double joystickValue, double scale) {
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

    public void setJags(double speed) {
        m_leftJag.set(speed * Configuration.LEFT_SETPOINT_SIGN);
        m_rightJag.set(speed * Configuration.RIGHT_SETPOINT_SIGN); //Right jag is backwards on the competition robot
    }

    public void setLeftJag(double speed) {
        m_leftJag.set(speed * Configuration.LEFT_SETPOINT_SIGN); //left jag going wrong way so negative
    }

    public void setRightJag(double speed) {
        m_rightJag.set(speed * Configuration.RIGHT_SETPOINT_SIGN);
    }

    public void initEncoders() {
        m_rightEncoder.setDistancePerPulse(RIGHT_DISTANCE_PER_PULSE_POSITION);
        m_leftEncoder.setDistancePerPulse(LEFT_DISTANCE_PER_PULSE_POSITION);
    }

    public double getLeftEncoderDistance() {

        return m_leftEncoder.getDistance();
    }

    public double getRightEncoderDistance() {
        return m_rightEncoder.getDistance();
    }

    public double getRateRightEncoder() {
        return m_rightEncoder.getRate();
    }

    public double getRateLeftEncoder() {
        return m_leftEncoder.getRate();
    }

    public double getLeftEncoder() {
        return m_leftEncoder.get();
    }

    public double getRightEncoder() {
        return m_rightEncoder.get();
    }

    public double getLeftRaw() {
        return m_leftEncoder.getRaw();
    }

    public double getRightRaw() {
        return m_rightEncoder.getRaw();
    }

    public void stopJags() {
        m_rightJag.set(0.0);
        m_leftJag.set(0.0);
    }

    public void driveJagsSquared(double xAxis, double yAxis, double scale) {
        xAxis = square(jerkyDeadZone(xAxis), scale); //when smoothDeadZone is coded, replace jerkyDeadZone with it
        yAxis = -square(jerkyDeadZone(yAxis), scale); //when smoothDeadZone is coded, replace jerkyDeadZone with it

        m_rightJag.set((yAxis - xAxis));
        m_leftJag.set((yAxis + xAxis));
    }

    public void driveJagsSqrt(double xAxis, double yAxis, double scale) {
        xAxis = sqrt(jerkyDeadZone(xAxis), scale);
        yAxis = -sqrt(jerkyDeadZone(yAxis), scale);

        m_rightJag.set((yAxis - xAxis));
        m_leftJag.set((yAxis + xAxis));
    }

    /*
     Basic tank drive function
     Uses the jerky dead zone
     Inputs: joysticks (2), y values for both joysticks
     Outputs: method doesn't need any return values
     How to test:
     -connect two jags to testing board
     -check to see if jags move in correct direction
     -this corresponds with the colour they show
     -if joysticks are pushed forward, both jags should turn forwards
     -Do the same with encoders
     -or (for both), make a smart dashboard object to test
     -if a number is there, the encoder/jag works, if no number (or 0) then it doesnt work
     */
    public void tankDrive(double yleft, double yright) {
        yleft = jerkyDeadZone(yleft);
        yright = jerkyDeadZone(yright);
        //drive.tankDrive(yleft, yright); USE THIS IF CODE DOESN'T WORK
        m_rightJag.set(-yright);
        m_leftJag.set(yleft);
    }

    /*
     regular deadzone that starts at 0
     does not scale
     could possbility lead to abrupt acceleration that may be dangerous
     is not tasty
     */
    public double jerkyDeadZone(double value) {
        if (value < DEAD_ZONE_SCALE && value > 0.0) {
            value = 0.0;
        } else if (value > -DEAD_ZONE_SCALE && value < 0.0) {
            value = 0.0;
        }
        return value;

    }

    /*
     A smooth deadzone that scales the values from the deadzone to 1.0
     by getting a slope and mapping the joystick values to the according
     jag speeds.
     DOESN'T WORK RIGHT NOW NEEDS TO BE FIXED!!!!!!!!! TODO
     */
    public double smoothDeadZone(double value) {
        boolean neg = value < 0;
        value = Math.abs(value);
        if (value > DEAD_ZONE_SCALE) {
            value = ((value - DEAD_ZONE_SCALE) / (1 - DEAD_ZONE_SCALE));
            if (neg) {
                value = -value;
            }
        } else {
            value = 0.0;
        }
        return value;
    }

    public double scaledDown(double value, double scaleDown) {
        value *= scaleDown;
        return value;
    }

    public void arcadeDrive(double x, double y) {
        //  System.out.println("Untouched values: " + x + ", " + y);

        //Flip this if forwards and backwards are flipped
        //y = -smoothDeadZone(y); //probably this on the competition chassis
        y = smoothDeadZone(y);
        //x = scaledDown(smoothDeadZone(x), 0.5); //Okay for the 2nd chassis (b/c it's lighter)
        x = scaledDown(smoothDeadZone(x), 0.75); //COMPETITION BOT
        // y = -jerkyDeadZone(y); //for the 2nd chassis and competition chassis
        // x = jerkyDeadZone(x);

        //drive.arcadeDrive(x, y); USE THIS IF CODE DOESN'T WORK
        //   System.out.println("x value: " + x + "\n y value: " + y);
        //  System.out.println("right jag output" + rightJag.get() + "left jag output" + leftJag.get());
        //COMPETITION BOT AND PRACTICE BOT
        //Flip the signs of both of these if turning is flipped
        m_rightJag.set((y - x));
        m_leftJag.set(-(y + x));

        /* Used to work, now this doesn't (idk why)
         rightJag.set(y + x);
         leftJag.set(-(y - x));

         */
        // System.out.println("right: " + (-(x - y)) + "\n left: " + (x + y));
    }

    public void scaleArcadeDrive(double x, double y, double scale) {
        //y = jerkyDeadZone(y);
        //x = jerkyDeadZone(x);

        y = smoothDeadZone(y) * scale;
        x = smoothDeadZone(x) * scale;

        //drive.arcadeDrive(x, y); USE THIS IF CODE DOESN'T WORK
        //System.out.println("x value: " + x +"\n y value: " + y);
        m_leftJag.set(y + x);
        m_rightJag.set(-(y - x));
        //System.out.println("right: " + (-(x-y)) +"\n left: " + (x+y));
    }



    public double getErrorSum() {
        return m_leftPositionPID.getErrorSum();
    }

    public void resetEncoders() {
        m_leftEncoder.reset();
        m_rightEncoder.reset();
    }

    public void initPositionPIDS() {
        m_rightPositionPID.enable();
        m_leftPositionPID.enable();
        m_rightPositionPID.setSetPoint(0.0);
        m_leftPositionPID.setSetPoint(0.0);
    }

    public void resetPositionPIDError() {
        m_rightPositionPID.resetError();
        m_leftPositionPID.resetError();
    }

    public void setLeftPositionPIDValues(double p, double i, double d) {
        m_leftPositionPID.setPID(p, i, d);
    }

    /**
     * @param p a double
     * @param i another double
     * @param d a different double
     * @author Arushi, Sylvie, Jisue
     */
    public void setRightPositionPIDValues(double p, double i, double d) {
        m_rightPositionPID.setPID(p, i, d);
    }

    /**
     * @param setPoint a double
     * @author Sylvie, Arushi, Jisue sets the right position PID setPoint to
     * double setpoint
     */
    public void setRightPIDPosition(double setPoint) {
        m_rightPositionPID.setSetPoint(setPoint);
    }

    /**
     * @param setPoint a double
     * @author Sylvie, Arushi, Jisue sets the left position PID setPoint to
     * double setpoint
     */
    public void setLeftPIDPosition(double setPoint) {
        m_leftPositionPID.setSetPoint(setPoint);
    }

    /**
     * @return the Error of Left Position PID
     * @author Arushi, Jisue, Sylvie
     */
    public double getLeftPositionError() {
        return m_leftPositionPID.getError();
    }

    /**
     * @author Sylvie, Arushi, Jisue this disables position PIDs
     */
    public void disablePositionPID() {
        m_rightPositionPID.setSetPoint(0);
        m_leftPositionPID.setSetPoint(0);
        m_rightPositionPID.disable();
        m_leftPositionPID.disable();
    }

    public void setPosition(double distance) {
        System.out.println("Right Side Error: " + m_rightPositionPID.getError());
        System.out.println("Left Side Error: " + m_leftPositionPID.getError());
        //rightPositionPID.setSetPoint(distance);
        m_rightPositionPID.setSetPoint(Configuration.RIGHT_SETPOINT_SIGN * distance);
        m_leftPositionPID.setSetPoint(Configuration.LEFT_SETPOINT_SIGN * distance);
    }

    public void setPositionSeparate(double leftDistance, double rightDistance) {
        m_rightPositionPID.setSetPoint(Configuration.RIGHT_SETPOINT_SIGN * rightDistance); //This is correct for the competition and practice chassis
        m_leftPositionPID.setSetPoint(Configuration.LEFT_SETPOINT_SIGN * leftDistance);
    }

    /*
     Takes in the encoder values at the start time and then checks if the robot
     has moved since

     (must be called in a loop to get the updated results)
     */
    public boolean isMoving() {
        double startingRightPosition = m_rightEncoder.getDistance();
        double startingLeftPosition = m_leftEncoder.getDistance();
        double movingOffset = 0.05; //How much the encoders will change to be called "moving"
        double startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 400) { // NOPMD(EmptyWhileStmt)
            //Waits 400 milliseconds
        }
        double currentRightPosition = m_rightEncoder.getDistance();
        double currentLeftPosition = m_leftEncoder.getDistance();

        return Math.abs(currentRightPosition - startingRightPosition) > movingOffset
            || Math.abs(currentLeftPosition - startingLeftPosition) > movingOffset;
    }

    public double getLeftEncoderRate() {
        return m_leftEncoder.getRate();
    }

    public double getRightEncoderRate() {
        return m_rightEncoder.getRate();
    }

    public void setRightEncoderReverseDirection(boolean reverseDirection) {
        m_rightEncoder.setReverseDirection(reverseDirection);

    }

    public void setLeftEncoderReverseDirection(boolean reverseDirection) {
        m_leftEncoder.setReverseDirection(reverseDirection);
    }

    public LspbPidPlanner getLeftChassisPlanner() {
        return m_leftChassisPlanner;
    }

    public LspbPidPlanner getRightChassisPlanner() {
        return m_rightChassisPlanner;
    }
}
