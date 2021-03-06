/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.RobotMap;
import com.gos.aerial_assist.objects.EncoderGoSPidController;

/**
 * @author Sophia and Sam
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Manipulator extends SubsystemBase {
    private static final double MIN_ANGLE = 0; //needs to be changed, not correct
    private static final double MAX_ANGLE = 113; //110 for comp bot//also needs to be changed
    private static final double PULSE_PER_ROTATION = 360; //THIS IS CORRECT FOR THE COMPETITION ROBOT
    private static final double MANIPULATOR_JAG_SPEED_STOP = 0.0;
    private static final double MANIPULATOR_JAG_SPEED_DOWN = 1.0;
    private static final double MANIPULATOR_JAG_SPEED_UP = -1.0;
    private static final double GEAR_RATIO = 13.0 / 70.0;
    private static final double DISTANCE_PER_PULSE = (PULSE_PER_ROTATION * GEAR_RATIO) / PULSE_PER_ROTATION; //360 is the number of degrees in a circle

    private double m_angle; //starting angle
    private final Jaguar m_manipulatorJag;
    private final Encoder m_bobTheArmEncoder;
    private final Relay m_dasBootLights;
    private final EncoderGoSPidController m_manipulatorPID;
    private double m_desiredAngle;

    /*
   (theta:360 as arcLength:circumference) -> use this to find out theta and distance

    1.) Pulses per rotation of encoder (get Raw) Yes
    2.) Set distance per pulse to be in degrees (getDistance, full rotation = 360) Yes
    3.) Tune PID

    */

    //Old p for the practice bot arm
    private static final double KP = Configuration.MANIPULATOR_PIVOT_P;
    //negative for the competition robot
    //private static double p = 0.12; //positive for the 2nd chassis
    private static final double KI = 0.0;
    private static final double KD = 0.0;

    //96 is from the old arm that was on the practice bot
    //private static int ZERO_ENCODER_VALUE = 86; //101 is the max angle, -17 (SHOULD BE CONSTANT) is how off from the horizontal all the way down is

    private static final int ZERO_ENCODER_VALUE = Configuration.PIVOT_ENCODER_ZERO_VALUE;
    //Practice bot <- //92; //COMPETITION BOT

    public Manipulator() {

        m_manipulatorJag = new Jaguar(RobotMap.MANIPULATOR_JAG);
        m_bobTheArmEncoder = new Encoder(RobotMap.MANIPULATOR_ENCODER_A, RobotMap.MANIPULATOR_ENCODER_B, true, CounterBase.EncodingType.k2X); //reverse boolean is true on the 2nd robot
        m_bobTheArmEncoder.setDistancePerPulse(DISTANCE_PER_PULSE); //assuming there's 360 pulses per revoluation

        m_dasBootLights = new Relay(RobotMap.LIGHTS);
        m_angle = MAX_ANGLE;


        m_manipulatorPID = new EncoderGoSPidController(KP, KI, KD, m_bobTheArmEncoder, m_manipulatorJag::set, EncoderGoSPidController.POSITION, ZERO_ENCODER_VALUE);

        System.out.println("Encoder Value ------------ " + m_manipulatorPID.getSignedDistance());

        //Starts the PID COMMENT THIS OUT WHEN YOU DO MANIPULATOR ENCODER TESTING
        startPID();
        initEncoder();
        resetPIDError();
    }

    //these are the lights that could be used to signal other teams if we are
    //ready to pass, etc.
    public void turnDasBootLightsOn() {
        m_dasBootLights.set(Relay.Value.kForward);
    }

    public void turnDasBootLightsOff() {
        m_dasBootLights.set(Relay.Value.kOff);
    }
    /*
     if the current angle is smaller than the desired angle: it's moving counterclockwise
     check to see if current angle is greater than the desired angle, meaning it has reached it
     if the current angle is greater than the desired angle: it's moving clockwise
     check to see if current angle is less than the desired angle, when it has reached it
     */

    public boolean checkAngle(double desiredAngle) {
        return desiredAngle == m_angle;
        //if we are allowed to go past zero, this code may not be correct
    }

    public void moveJag(double desiredAngle) {
        this.m_desiredAngle = desiredAngle;
        if (desiredAngle < MIN_ANGLE || desiredAngle > MAX_ANGLE) {
            throw new IllegalArgumentException("Angle for arm manipulation is invalid");
        }
        if (desiredAngle == m_angle) {
            return;
        } else if (desiredAngle > m_angle) {
            m_manipulatorJag.set(.1); //assuming that 100% power is okay
        } else {
            m_manipulatorJag.set(-.1);
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
        if (m_angle == m_desiredAngle) {
            return;
        } else if (m_angle < m_desiredAngle) {
            m_manipulatorJag.set(.1);
            Timer.delay(0.5);
            m_manipulatorJag.set(0.0);
        } else {
            m_manipulatorJag.set(-.1);
            Timer.delay(0.5);
            m_manipulatorJag.set(0.0);
        }
    }

    public double getArmAngle() {
        return m_angle;

        //input:The sensors (the camera)
        //output:What we want the angle of the robot to be
        //What it will do: It will use the camera to calculate the angle that needs to be used
        // by using the distance from the camera to the goal.
        //Purpose: To tell us what the angle of the robot should be in order to score
    }

    private void setAngle() {
        //USE GEAR RATIO
        double proportionalAngle = (90.0 / 288.0) * m_bobTheArmEncoder.get();
        System.out.println("Bob says: " + m_bobTheArmEncoder.get());
        this.m_angle = proportionalAngle;
    }


    public void moveManipulatorDown() {
        m_manipulatorJag.set(MANIPULATOR_JAG_SPEED_DOWN * Configuration.DESIRED_ANGLE_PIVOT_ARM_SIGN);
    } //Sets/returns the speed of the manipulator jag for manual adjusting using buttons on PS3 controller

    public void moveManipulatorUp() {
        m_manipulatorJag.set(MANIPULATOR_JAG_SPEED_UP * Configuration.DESIRED_ANGLE_PIVOT_ARM_SIGN);
        //sets/returns the speed of the manipulator jag for manual adjusting using buttons on PS3 controller
    }

    public void stopManipulator() {
        m_manipulatorJag.set(MANIPULATOR_JAG_SPEED_STOP);
    } //Stops the manipulator jag

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

    public final void initEncoder() {
        m_bobTheArmEncoder.reset();
        //bobTheArmEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); //possibly not necessary
        //initialize encoder. I will probably need this. TODO: see if this is needed
    }

    public void startJag() {
        m_manipulatorJag.set(0);
    }

    public void moveArmTest() {
        //move the arm moter. Move the jagas forward. Move Jags backward.
    }

    public void setJag(double speed) {
        m_manipulatorJag.set(speed);
    }

    public void stopJag() {
        m_manipulatorJag.set(0.0);

        if (m_desiredAngle > m_angle) { //checking direction angle went
            m_angle = m_bobTheArmEncoder.getDistance() + m_angle;
        } else {
            m_angle = m_angle - m_bobTheArmEncoder.getDistance();
        }
        m_bobTheArmEncoder.reset();
        //if we're allowed to go past 0/360 than it doesnt work anymore
    }

    public void testJagsForward() {
        m_manipulatorJag.set(0.3);
    }

    public void testJagsBackward() {
        m_manipulatorJag.set(-0.3);
    }

    public void stopTestJags() {
        m_manipulatorJag.set(0);
    }

    public void moveJags(double position) {
        m_manipulatorJag.set(position);
    }

    public double getRate() {
        return m_bobTheArmEncoder.getRate();

    }

    /*
    Doesn't take into account the encoder type (k4x)
    */
    public double getRaw() {
        return m_bobTheArmEncoder.getRaw();
    }




    public final void resetPIDError() {
        m_manipulatorPID.resetError();
    }

    public double getAbsoluteDistance() {
        return m_bobTheArmEncoder.getDistance() + ZERO_ENCODER_VALUE; //the horizontal zero
    }

    public double getDistance() {
        return m_bobTheArmEncoder.getDistance();
    }

    /*
    Zero the manipulator arm at the horizontal (horizontal is 0) NEED TODO
    */
    public void setSetPoint(double setpoint) {
        m_manipulatorPID.setSetPoint(setpoint);
    }

    public void disablePID() {
        m_manipulatorPID.disable();
    }

    public void setPID(double p1, double i1, double d1) {
        m_manipulatorPID.setPID(p1, i1, d1);
    }

    public final void startPID() {
        m_manipulatorPID.enable();
        //manipulatorPID.setOutputThreshold(1.0); Possibly needed for the velocity control but not for position PID
    }

    public double getError() {
        return m_manipulatorPID.getError();
    }

    public double getEncoder() {
        return m_bobTheArmEncoder.get();
    }

    public void holdAngle() {
        setSetPoint(getAbsoluteDistance());
    }

    public double getSetPoint() {
        return m_manipulatorPID.getSetPoint();
    }
}
