/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 move right side slower to match the left
 */
package com.girlsofsteelrobotics.atlas.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.girlsofsteelrobotics.atlas.RobotMap;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.PIDOutput;
import com.girlsofsteelrobotics.atlas.Configuration;
import com.girlsofsteelrobotics.atlas.objects.EncoderGoSPIDController;
import com.girlsofsteelrobotics.atlas.objects.LSPBPIDPlanner;

/**
 *
 * @author Heather
 */
public class Chassis extends Subsystem {

    private final Jaguar rightJag;
    private final Jaguar leftJag;

    //RobotDrive drive = new RobotDrive(rightJag, leftJag); USE THIS IF DRIVING CODE DOESN'T WORK
    private final double deadZoneScale = 0.3;

    private final Encoder rightEncoder;
    private final Encoder leftEncoder;

    private final EncoderGoSPIDController leftPositionPID;
    private final EncoderGoSPIDController rightPositionPID;
    public LSPBPIDPlanner leftChassisPlanner;
    public LSPBPIDPlanner rightChassisPlanner;

    //need the p for 2nd robot
    private double Ppright = 0.5; //Competition chassis gains 0.2
    private final double Piright = 0;
    private final double Pdright = 0;
    private double Ppleft = 0.5; //Competition chassis gains 0.2
    private final double Pileft = 0;
    private final double Pdleft = 0;

    private final double Vpright = 0.0;
    private final double Viright = 0.0;
    private final double Vdright = 0.0;
    private final double Vpleft = 0.0;
    private final double Vileft = 0.0;
    private final double Vdleft = 0.0;

    private final double leftPulsePerRevolution = 360;  //pretty sure it's correct
    private final double rightPulsePerRevolution = 360;
    private final double wheelCircumference = 0.152 * Math.PI; //In meters

    private final double gearRatio = 8.0 / 5.0; //CORRECT ON THE COMPETITION CHASSIS

//    private double leftDistancePerPulsePosition = (wheelCircumference) / (leftPulsePerRevolution * gearRatio);//(leftPulsePerRevolution * gearRatio) / (wheelCircumference);
//    private double rightDistancePerPulsePosition = (wheelCircumference) / (rightPulsePerRevolution * gearRatio);//(rightPulsePerRevolution * gearRatio) / (wheelCircumference);
//
    //Practice bot, 4/19/14 based on raw values after pushing robot forward 5.68 meters
    //Divided the raw values by four because the distances were off by a factor of four
//    private double leftDistancePerPulsePosition = (5.68 / (24163.5/4.0));
//    private double rightDistancePerPulsePosition = (5.68 / (25416/4));


    //Competition robot, 4/23/14 pushed the robot forward 1 meter
    //Divided the raw values by four because the distances were off by a factor of four
    private final double leftDistancePerPulsePosition = (1.0 / (4810.4/4.0));
    private final double rightDistancePerPulsePosition = (1.0 / (4957.5/4));

    public Chassis() {
        leftChassisPlanner = new LSPBPIDPlanner();
        rightChassisPlanner = new LSPBPIDPlanner();

        Ppright = Configuration.rightPositionP;
        Ppleft = Configuration.leftPositionP;

        rightJag = new Jaguar(RobotMap.RIGHT_JAG_PORT);
        leftJag = new Jaguar(RobotMap.LEFT_JAG_PORT);

        //2nd Robot encoder stuff
        rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B, Configuration.rightEncoderReverse, CounterBase.EncodingType.k4X);
        leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B, Configuration.leftEncoderReverse, CounterBase.EncodingType.k4X);

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
        rightPositionPID = new EncoderGoSPIDController(Ppright, Piright, Pdright, rightEncoder, new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                rightJag.set(output);
            }
        }, 2, Configuration.rightPIDReverseEncoder, false); //For the competition bot (practice bot is true, false)

        leftPositionPID = new EncoderGoSPIDController(Ppleft, Pileft, Pdleft, leftEncoder, new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                leftJag.set(output);
            }
        }, 2, Configuration.leftPIDReverseEncoder, false);
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
        leftJag.set(speed * Configuration.leftSetpointSign);
        rightJag.set(speed * Configuration.rightSetpointSign); //Right jag is backwards on the competition robot
    }

    public void setLeftJag(double speed) {
        leftJag.set(speed * Configuration.leftSetpointSign); //left jag going wrong way so negative
    }

    public void setRightJag(double speed) {
        rightJag.set(speed * Configuration.rightSetpointSign);
    }

    public void initEncoders() {
        rightEncoder.setDistancePerPulse(rightDistancePerPulsePosition);
        leftEncoder.setDistancePerPulse(leftDistancePerPulsePosition);
    }

    public double getLeftEncoderDistance() {

        return leftEncoder.getDistance();
    }

    public double getRightEncoderDistance() {
        return rightEncoder.getDistance();
    }

    public double getRateRightEncoder() {
        return rightEncoder.getRate();
    }

    public double getRateLeftEncoder() {
        return leftEncoder.getRate();
    }

    public double getLeftEncoder() {
        return leftEncoder.get();
    }

    public double getRightEncoder() {
        return rightEncoder.get();
    }

    public double getLeftRaw() {
        return leftEncoder.getRaw();
    }

    public double getRightRaw() {
        return rightEncoder.getRaw();
    }

    public void stopJags() {
        rightJag.set(0.0);
        leftJag.set(0.0);
    }

    public void driveJagsSquared(double xAxis, double yAxis, double scale) {
        xAxis = square(jerkyDeadZone(xAxis), scale); //when smoothDeadZone is coded, replace jerkyDeadZone with it
        yAxis = -square(jerkyDeadZone(yAxis), scale); //when smoothDeadZone is coded, replace jerkyDeadZone with it

        rightJag.set((yAxis - xAxis));
        leftJag.set((yAxis + xAxis));
    }

    public void driveJagsSqrt(double xAxis, double yAxis, double scale) {
        xAxis = sqrt(jerkyDeadZone(xAxis), scale);
        yAxis = -sqrt(jerkyDeadZone(yAxis), scale);

        rightJag.set((yAxis - xAxis));
        leftJag.set((yAxis + xAxis));
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
        rightJag.set(-yright);
        leftJag.set(yleft);
    }

    /*
     regular deadzone that starts at 0
     does not scale
     could possbility lead to abrupt acceleration that may be dangerous
     is not tasty
     */
    public double jerkyDeadZone(double value) {
        if (value < deadZoneScale && value > 0.0) {
            value = 0.0;
        } else if (value > -deadZoneScale && value < 0.0) {
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
        if (value > deadZoneScale) {
            value = ((value - deadZoneScale) / (1 - deadZoneScale));
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
        rightJag.set((y - x));
        leftJag.set(-(y + x));

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
        leftJag.set(y + x);
        rightJag.set(-(y - x));
        //System.out.println("right: " + (-(x-y)) +"\n left: " + (x+y));
    }

    @Override
    protected void initDefaultCommand() {
    }

    public double getErrorSum() {
        return leftPositionPID.getErrorSum();
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public void initPositionPIDS() {
        rightPositionPID.enable();
        leftPositionPID.enable();
        rightPositionPID.setSetPoint(0.0);
        leftPositionPID.setSetPoint(0.0);
    }

    public void resetPositionPIDError() {
        rightPositionPID.resetError();
        leftPositionPID.resetError();
    }

    public void setLeftPositionPIDValues(double p, double i, double d) {
        leftPositionPID.setPID(p, i, d);
    }

    /**
     *
     * @param p a double
     * @param i another double
     * @param d a different double
     * @author Arushi, Sylvie, Jisue
     *
     */
    public void setRightPositionPIDValues(double p, double i, double d) {
        rightPositionPID.setPID(p, i, d);
    }

    /**
     * @param setPoint a double
     * @author Sylvie, Arushi, Jisue sets the right position PID setPoint to
     * double setpoint
     */
    public void setRightPIDPosition(double setPoint) {
        rightPositionPID.setSetPoint(setPoint);
    }

    /**
     * @param setPoint a double
     * @author Sylvie, Arushi, Jisue sets the left position PID setPoint to
     * double setpoint
     */
    public void setLeftPIDPosition(double setPoint) {
        leftPositionPID.setSetPoint(setPoint);
    }

    /**
     * @author Arushi, Jisue, Sylvie
     * @return the Error of Left Position PID
     */
    public double getLeftPositionError() {
        return leftPositionPID.getError();
    }

    /**
     * @author Sylvie, Arushi, Jisue this disables position PIDs
     */
    public void disablePositionPID() {
        rightPositionPID.setSetPoint(0);
        leftPositionPID.setSetPoint(0);
        rightPositionPID.disable();
        leftPositionPID.disable();
    }

    public void setPosition(double distance) {
        System.out.println("Right Side Error: " + rightPositionPID.getError());
        System.out.println("Left Side Error: " + leftPositionPID.getError());
        //rightPositionPID.setSetPoint(distance);
        rightPositionPID.setSetPoint(Configuration.rightSetpointSign * distance);
        leftPositionPID.setSetPoint(Configuration.leftSetpointSign * distance);
    }

    public void setPositionSeparate(double leftDistance, double rightDistance) {
        rightPositionPID.setSetPoint(Configuration.rightSetpointSign * rightDistance); //This is correct for the competition and practice chassis
        leftPositionPID.setSetPoint(Configuration.leftSetpointSign * leftDistance);
    }

    /*
     Takes in the encoder values at the start time and then checks if the robot
     has moved since

     (must be called in a loop to get the updated results)
     */
    public boolean isMoving() {
        double startingRightPosition = rightEncoder.getDistance();
        double startingLeftPosition = leftEncoder.getDistance();
        double movingOffset = 0.05; //How much the encoders will change to be called "moving"
        double startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 400) { //Waits 400 milliseconds
        }
        double currentRightPosition = rightEncoder.getDistance();
        double currentLeftPosition = leftEncoder.getDistance();

        return Math.abs(currentRightPosition - startingRightPosition) > movingOffset
                || Math.abs(currentLeftPosition - startingLeftPosition) > movingOffset;
    }

    public double getLeftEncoderRate() {
        return leftEncoder.getRate();
    }

    public double getRightEncoderRate() {
        return rightEncoder.getRate();
    }

    public void setRightEncoderReverseDirection(boolean reverseDirection) {
        rightEncoder.setReverseDirection(reverseDirection);

    }

    public void setLeftEncoderReverseDirection(boolean reverseDirection) {
        leftEncoder.setReverseDirection(reverseDirection);
    }

}
