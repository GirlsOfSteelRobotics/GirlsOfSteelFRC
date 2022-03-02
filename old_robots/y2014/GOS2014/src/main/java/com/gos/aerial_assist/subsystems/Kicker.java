/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 move right side slower to match the left

 We have a 4 k4x limit on encoders
 */

package com.gos.aerial_assist.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.aerial_assist.RobotMap;
import com.gos.aerial_assist.objects.EncoderGoSPidController;
import com.gos.aerial_assist.objects.LspbPidPlanner;

/**
 * @author Heather
 */
public class Kicker extends SubsystemBase {

    private static final double m_pulsePerRevolution = 360;  //correct
    private static final double m_distancePerPulse = 1.0 / m_pulsePerRevolution; //No gear ratio.

    private final Jaguar m_kickerJag;
    private final Talon m_kickerTalon;

    private final Encoder m_kickerEncoder;

    private final DigitalInput m_kickerLimitSwitch;

    private EncoderGoSPidController m_kickerPositionPID;
    private final LspbPidPlanner m_kickerPlanner;

    public Kicker() {
        // Pright = Configuration.rightPositionP; //TODO
        // Pleft = Configuration.leftPositionP;

        m_kickerJag = new Jaguar(RobotMap.KICKER_MOTOR);
        m_kickerTalon = new Talon(7);

        m_kickerEncoder = new Encoder(RobotMap.KICKER_ENCODER_A, RobotMap.KICKER_ENCODER_B, true, CounterBase.EncodingType.k2X);

        m_kickerPlanner = new LspbPidPlanner(0.025);


        //        kickerPositionPID = new EncoderGoSPIDController(p, i, d, kickerEncoder, new PIDOutput() {
        //
        //            public void pidWrite(double output) {
        //                kickerSpike.set(output);
        //            }
        //        }, 2, false, true); //false to reverse encoder, true to MOD the value

        m_kickerLimitSwitch = new DigitalInput(RobotMap.KICKER_LIMIT);
    }

    public void holdPosition() {
        m_kickerPositionPID.setSetPoint(m_kickerEncoder.getDistance());
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
        m_kickerEncoder.setDistancePerPulse(m_distancePerPulse);
    }

    public double getEncoderDistance() {
        return m_kickerEncoder.getDistance();
    }

    public double getRateEncoder() {
        return m_kickerEncoder.getRate();
    }

    public double getEncoder() {
        return m_kickerEncoder.get();
    }

    public double getRaw() {
        return m_kickerEncoder.getRaw();
    }

    public void setJag(double speed) {
        m_kickerJag.set(speed);
    }

    public void stopJag() {
        m_kickerJag.set(0.0);
    }

    public void setTalon(double speed) {
        m_kickerTalon.set(speed);
    }

    public void stopTalon() {
        m_kickerTalon.set(0.0);
    }



    public void resetEncoders() {
        m_kickerEncoder.reset();
    }

    public void initPIDS() {
        m_kickerPositionPID.enable();
        m_kickerPositionPID.setSetPoint(0.0);
    }

    public void resetPIDError() {
        m_kickerPositionPID.resetError();
    }

    public void setLeftPIDValues(double p, double i, double d) {
        m_kickerPositionPID.setPID(p, i, d);
    }

    /**
     * @param p a double
     * @param i another double
     * @param d a different double
     * @author Arushi, Sylvie, Jisue
     */
    public void setPIDValues(double p, double i, double d) {
        m_kickerPositionPID.setPID(p, i, d);
    }

    /**
     * @param p a double
     * @param i another double
     * @param d another different double
     * @author Arushi, Sylvie, Jisue Sets left Velocity PID Values to 3 given
     * values
     */
    public void setPIDPosition(double setPoint) {
        m_kickerPositionPID.setSetPoint(setPoint);
    }

    /**
     * @author Sylvie, Arushi, Jisue this disables position PIDs
     */
    public void disablePositionPID() {
        m_kickerPositionPID.setSetPoint(0);
        m_kickerPositionPID.disable();
    }

    public void setEncoderReverseDirection(boolean reverseDirection) {
        m_kickerEncoder.setReverseDirection(reverseDirection);
    }

    public boolean getLimitSwitch() {
        //Is backward!
        return !m_kickerLimitSwitch.get(); //true when the kicker is fully loaded
    }

    public LspbPidPlanner getKickerPlanner() {
        return m_kickerPlanner;
    }
}
