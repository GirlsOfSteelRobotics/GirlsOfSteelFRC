/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;

/**
 *
 * @author Sylvie
 */
public class Chassis extends Subsystem {

    private Jaguar jag1;
    private Jaguar jag2;
    private Jaguar jag3;
    private Relay relay1;
    private Relay relay2;
    private Encoder jag1Encoder;
    private Encoder jag2Encoder;

    public Chassis() {
        jag1 = new Jaguar(RobotMap.JAG1_PORT);
        jag2 = new Jaguar(RobotMap.JAG2_PORT);
        jag3 = new Jaguar(RobotMap.JAG3_PORT);
        relay1 = new Relay(RobotMap.SPIKE1_PORT);
        relay2 = new Relay(RobotMap.SPIKE2_PORT);
        jag1Encoder = new Encoder(RobotMap.JAG1_ENCODER_A, RobotMap.JAG1_ENCODER_B);
        jag2Encoder = new Encoder(RobotMap.JAG2_ENCODER_A, RobotMap.JAG2_ENCODER_B);
    }

    /*
     * Sets one specified jag to the given speed
     * 1 -> jag 1
     * 2 -> jag 2
     * 3 -> jag 3
     */
    public void setJag(int num, double speed) {
        switch (num) {
            case 1:
                jag1.set(speed);
                break;
            case 2:
                jag2.set(speed);
                break;
            case 3:
                jag3.set(speed);
                break;
            default:
                System.out.println("The jag number that was asked for is not"
                        + " valid");
        }
    }

    /*
     * Sets all three jags to the given speed
     */
    public void setJags(double speed) {
        jag1.set(speed);
        jag2.set(speed);
        jag3.set(speed);
    }

    public void stopJags() {
        setJags(0.0);
    }

    public void startEncoders() {
        jag1Encoder.start();
        jag2Encoder.start();
    }

    public void stopEncoders() {
        jag1Encoder.stop();
        jag2Encoder.stop();
    }

    /*
     * This function will return the value of the chosen encoder
     * 1 -> Encoder 1
     * 2 -> Encoder 2
     * Returns -1 if an invalid input is entered
     */
    public double getEncoders(int numEncoder) {
        double value = 0.0;
        switch (numEncoder) {
            case 1:
                value = jag1Encoder.get();
                break;
            case 2:
                value = jag2Encoder.get();
                break;
            default:
                value = -1;
        }

        return value;
    }

    public void forwardSpikeOne() {
        relay1.set(Relay.Value.kForward);
    }

    public void forwardSpikeTwo() {
        relay2.set(Relay.Value.kForward);
    }

    public void reverseSpikeOne() {
        relay1.set(Relay.Value.kReverse);
    }

    public void reverseSpikeTwo() {
        relay2.set(Relay.Value.kReverse);
    }

    protected void initDefaultCommand() {
    }
}
