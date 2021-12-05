/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.girlsofsteelrobotics.atlas.Configuration;
import com.girlsofsteelrobotics.atlas.RobotMap;
import com.girlsofsteelrobotics.atlas.objects.EncoderGoSPIDController;

/**
 *
 * @author Sophia and Sam
 */
public class Manipulator extends Subsystem {

    private double angle; //starting angle
    private final Jaguar manipulatorJag;
    private final Encoder bobTheArmEncoder;
    private final Relay dasBootLights;
    private final EncoderGoSPIDController manipulatorPID;
    private static final double minAngle = 0; //needs to be changed, not correct
    private static final double maxAngle = 113; //110 for comp bot//also needs to be changed
    private static final double pulsePerRotation = 360; //THIS IS CORRECT FOR THE COMPETITION ROBOT
    private double desiredAngle;
    private static final double manipulatorJagSpeedStop = 0.0;
    private static final double manipulatorJagSpeedDown = 1.0;
    private static final double manipulatorJagSpeedUp = -1.0;
    private static final double gearRatio = 13.0/70.0;
    private static final double distancePerPulse = (pulsePerRotation * gearRatio)/pulsePerRotation; //360 is the number of degrees in a circle

    /*
   (theta:360 as arcLength:circumference) -> use this to find out theta and distance

    1.) Pulses per rotation of encoder (get Raw) Yes
    2.) Set distance per pulse to be in degrees (getDistance, full rotation = 360) Yes
    3.) Tune PID

    */

    //Old p for the practice bot arm
    private static  double p = 0.1; //negative for the competition robot
    //private static double p = 0.12; //positive for the 2nd chassis
    private static final double i = 0.0;
    private static final double d = 0.0;

    //96 is from the old arm that was on the practice bot
    //private static int ZERO_ENCODER_VALUE = 86; //101 is the max angle, -17 (SHOULD BE CONSTANT) is how off from the horizontal all the way down is

    private static int ZERO_ENCODER_VALUE = 89; //Practice bot <- //92; //COMPETITION BOT

    public Manipulator() {
        p = Configuration.manipulatorPivotP;

        ZERO_ENCODER_VALUE = Configuration.pivotEncoderZeroValue;

        manipulatorJag = new Jaguar(RobotMap.MANIPULATOR_JAG);
        bobTheArmEncoder = new Encoder(RobotMap.MANIPULATOR_ENCODER_A, RobotMap.MANIPULATOR_ENCODER_B, true, CounterBase.EncodingType.k2X); //reverse boolean is true on the 2nd robot
        bobTheArmEncoder.setDistancePerPulse(distancePerPulse); //assuming there's 360 pulses per revoluation

        dasBootLights = new Relay(RobotMap.LIGHTS);
        angle = maxAngle;


        manipulatorPID = new EncoderGoSPIDController(p, i, d, bobTheArmEncoder, new PIDOutput() {

            @Override
            public void pidWrite(double output) {
                manipulatorJag.set(output);
            }
        }, EncoderGoSPIDController.POSITION, ZERO_ENCODER_VALUE);

        System.out.println("Encoder Value ------------ " + manipulatorPID.getSignedDistance());

        //Starts the PID COMMENT THIS OUT WHEN YOU DO MANIPULATOR ENCODER TESTING
        startPID();
        initEncoder();
        resetPIDError();
    }

    //these are the lights that could be used to signal other teams if we are
    //ready to pass, etc.
    public void turnDasBootLightsOn() {
        dasBootLights.set(Relay.Value.kForward);
    }

    public void turnDasBootLightsOff() {
        dasBootLights.set(Relay.Value.kOff);
    }
    /*
     if the current angle is smaller than the desired angle: it's moving counterclockwise
     check to see if current angle is greater than the desired angle, meaning it has reached it
     if the current angle is greater than the desired angle: it's moving clockwise
     check to see if current angle is less than the desired angle, when it has reached it
     */

    public boolean checkAngle(double desiredAngle) {
        return desiredAngle == angle;
        //if we are allowed to go past zero, this code may not be correct
    }

    public void moveJag(double desiredAngle) {
        this.desiredAngle = desiredAngle;
        if (desiredAngle < minAngle || desiredAngle > maxAngle) {
            throw new IllegalArgumentException("Angle for arm manipulation is invalid");
        }
        if (desiredAngle == angle) {
            return;
        } else if (desiredAngle > angle) {
            manipulatorJag.set(.1); //assuming that 100% power is okay
        } else {
            manipulatorJag.set(-.1);
        }
        setAngle();
//        if (desiredAngle == angle) {
//            System.out.println("Here");
//        } else if (desiredAngle > angle) {
//            manipulatorJag.set(0.3); //assuming that 100% power is okay
//        } else {
//            manipulatorJag.set(-0.3);
//        }
    }

    public void moveAngle() {
        if(angle == desiredAngle) {
            return;
        }
        else if (angle < desiredAngle) {
            manipulatorJag.set(.1);
            Timer.delay(0.5);
            manipulatorJag.set(0.0);
        } else {
            manipulatorJag.set(-.1);
            Timer.delay(0.5);
            manipulatorJag.set(0.0);
        }
    }

    public double getArmAngle() {
        return angle;

        //input:The sensors (the camera)
        //output:What we want the angle of the robot to be
        //What it will do: It will use the camera to calculate the angle that needs to be used
        // by using the distance from the camera to the goal.
        //Purpose: To tell us what the angle of the robot should be in order to score
    }

    private void setAngle() {
        //USE GEAR RATIO
        double proportionalAngle = (90.0/288.0) * bobTheArmEncoder.get();
        System.out.println("Bob says: " + bobTheArmEncoder.get());
        this.angle = proportionalAngle;
    }


    public void moveManipulatorDown() {
        manipulatorJag.set(manipulatorJagSpeedDown * Configuration.desiredAnglePivotArmSign);
    }//Sets/returns the speed of the manipulator jag for manual adjusting using buttons on PS3 controller

    public void moveManipulatorUp() {
        manipulatorJag.set(manipulatorJagSpeedUp * Configuration.desiredAnglePivotArmSign);
        //sets/returns the speed of the manipulator jag for manual adjusting using buttons on PS3 controller
    }

    public void stopManipulator() {
        manipulatorJag.set(manipulatorJagSpeedStop);
    }//Stops the manipulator jag

//    public double getCurrentAngle(double angle) {
//        bobTheArmEncoder.get();
//        angle += (bobTheArmEncoder.get() * pulsePerTick); //depends on what .get does
//        return angle;
//    }

    /*
    no
    */
    public void init() {
        initEncoder();
        startJag();
    }

    public void initEncoder() {
        bobTheArmEncoder.reset();
        //bobTheArmEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); //possibly not necessary
        //initialize encoder. I will probably need this. TODO: see if this is needed
    }

    public void startJag() {
        manipulatorJag.set(0);
    }

    public void MoveArmTest() {
        //move the arm moter. Move the jagas forward. Move Jags backward.
    }

    public void setJag(double speed) {
        manipulatorJag.set(speed);
    }

    public void stopJag() {
        manipulatorJag.set(0.0);

        if (desiredAngle > angle) { //checking direction angle went
            angle = bobTheArmEncoder.getDistance() + angle;
        } else {
            angle = angle - bobTheArmEncoder.getDistance();
        }
        bobTheArmEncoder.reset();
        //if we're allowed to go past 0/360 than it doesnt work anymore
    }

    public void testJagsForward() {
        manipulatorJag.set(0.3);
    }

    public void testJagsBackward() {
        manipulatorJag.set(-0.3);
    }

    public void stopTestJags() {
        manipulatorJag.set(0);
    }

    public void moveJags(double position) {
        manipulatorJag.set(position);
    }

    public double getRate() {
        return bobTheArmEncoder.getRate();

   }

    /*
    Doesn't take into account the encoder type (k4x)
    */
    public double getRaw() {
        return bobTheArmEncoder.getRaw();
    }

    @Override
    protected void initDefaultCommand() {
    }


    public void resetPIDError() {
        manipulatorPID.resetError();
    }

    public double getAbsoluteDistance() {
        return bobTheArmEncoder.getDistance() + ZERO_ENCODER_VALUE;//the horizontal zero
    }

    public double getDistance() {
        return bobTheArmEncoder.getDistance();
    }

    /*
    Zero the manipulator arm at the horizontal (horizontal is 0) NEED TODO
    */
    public void setSetPoint(double setpoint) {
        manipulatorPID.setSetPoint(setpoint);
    }

    public void disablePID() {
        manipulatorPID.disable();
    }

    public void setPID(double p1, double i1,double d1) {
        manipulatorPID.setPID(p1, i1, d1);
    }

    public void startPID() {
        manipulatorPID.enable();
        //manipulatorPID.setOutputThreshold(1.0); Possibly needed for the velocity control but not for position PID
    }

    public double getError() {
        return manipulatorPID.getError();
    }

    public double getEncoder() {
        return bobTheArmEncoder.get();
    }

    public void holdAngle() {
        setSetPoint(getAbsoluteDistance());
    }

    public double getSetPoint() {
        return manipulatorPID.getSetPoint();
    }
}
