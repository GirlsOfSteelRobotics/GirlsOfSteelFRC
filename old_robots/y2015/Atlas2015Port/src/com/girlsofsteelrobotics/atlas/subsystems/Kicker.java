/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 move right side slower to match the left

 We have a 4 k4x limit on encoders
 */
package com.girlsofsteelrobotics.atlas.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.girlsofsteelrobotics.atlas.RobotMap;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import com.girlsofsteelrobotics.atlas.objects.EncoderGoSPIDController;
import com.girlsofsteelrobotics.atlas.objects.LSPBPIDPlanner;

/**
 *
 * @author Heather
 */
public class Kicker extends Subsystem {

    private Jaguar kickerJag;
    private Talon kickerTalon;

    private Encoder kickerEncoder;

    private DigitalInput kickerLimitSwitch;

    private EncoderGoSPIDController kickerPositionPID;
    public LSPBPIDPlanner kickerPlanner;

    private double p = 500; //Works well on the kicker!
    private double i = 0;
    private double d = 0;

    private double pulsePerRevolution = 360;  //correct
    private double distancePerPulse = 1.0 / pulsePerRevolution; //No gear ratio.

    public Kicker() {
        // Pright = Configuration.rightPositionP; //TODO
        // Pleft = Configuration.leftPositionP;

        kickerJag = new Jaguar(RobotMap.KICKER_MOTOR);
        kickerTalon = new Talon(7);

        kickerEncoder = new Encoder(RobotMap.KICKER_ENCODER_A, RobotMap.KICKER_ENCODER_B, true, CounterBase.EncodingType.k2X);

        kickerPlanner = new LSPBPIDPlanner(0.025);


//        kickerPositionPID = new EncoderGoSPIDController(p, i, d, kickerEncoder, new PIDOutput() {
//
//            public void pidWrite(double output) {
//                kickerSpike.set(output);
//            }
//        }, 2, false, true); //false to reverse encoder, true to MOD the value

       kickerLimitSwitch = new DigitalInput(RobotMap.KICKER_LIMIT);
    }

    public void holdPosition() {
        kickerPositionPID.setSetPoint(kickerEncoder.getDistance());
    }

    /*
    public void setSpikeForward() {
        kickerJag.set(Relay.Value.kForward); //Checked 04/08/14: forward is forward
    }

    public void setSpikeBackwards() {
        kickerJag.set(Relay.Value.kReverse);
    }
    */

    public void initEncoders() {
        kickerEncoder.setDistancePerPulse(distancePerPulse);
    }

    public double getEncoderDistance() {
        return kickerEncoder.getDistance();
    }

    public double getRateEncoder() {
        return kickerEncoder.getRate();
    }

    public double getEncoder() {
        return kickerEncoder.get();
    }

    public double getRaw() {
        return kickerEncoder.getRaw();
    }

    public void setJag(double speed) {
        kickerJag.set(speed);
    }

    public void stopJag() {
        kickerJag.set(0.0);
    }

    public void setTalon(double speed) {
        kickerTalon.set(speed);
    }

    public void stopTalon() {
        kickerTalon.set(0.0);
    }

    protected void initDefaultCommand() {
    }

    public void resetEncoders() {
        kickerEncoder.reset();
    }

    public void initPIDS() {
        kickerPositionPID.enable();
        kickerPositionPID.setSetPoint(0.0);
    }

    public void resetPIDError() {
        kickerPositionPID.resetError();
    }

    public void setLeftPIDValues(double p, double i, double d) {
        kickerPositionPID.setPID(p, i, d);
    }

    /**
     *
     * @param p a double
     * @param i another double
     * @param d a different double
     * @author Arushi, Sylvie, Jisue
     *
     */
    public void setPIDValues(double p, double i, double d) {
        kickerPositionPID.setPID(p, i, d);
    }

    /**
     *
     * @param p a double
     * @param i another double
     * @param d another different double
     * @author Arushi, Sylvie, Jisue Sets left Velocity PID Values to 3 given
     * values
     */
    public void setPIDPosition(double setPoint) {
        kickerPositionPID.setSetPoint(setPoint);
    }

    /**
     * @author Sylvie, Arushi, Jisue this disables position PIDs
     */
    public void disablePositionPID() {
        kickerPositionPID.setSetPoint(0);
        kickerPositionPID.disable();
    }

    public void setEncoderReverseDirection(boolean reverseDirection) {
        kickerEncoder.setReverseDirection(reverseDirection);
    }

    public boolean getLimitSwitch()
    {
        //Is backward!
        return !kickerLimitSwitch.get(); //true when the kicker is fully loaded
    }

}
