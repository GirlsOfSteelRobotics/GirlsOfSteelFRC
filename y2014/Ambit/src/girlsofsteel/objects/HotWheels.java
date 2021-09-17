/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.objects;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Acer
 */
public class HotWheels {

    Jaguar top;
    Jaguar wheel;
    Encoder wheelEncoder;
    WeirdEncoder topEncoder;
    PIDController wheelPID;
    private final double WHEEL_DIAMETER = 0.1524;
    private final double GEAR_RATIO = 15.0 / 24.0;
    private final double PULSES_RIGHT = 250.0;
    private final double PULSES_LEFT = 360.0;
    private final double ENCODER_UNIT_RIGHT = (WHEEL_DIAMETER * Math.PI * GEAR_RATIO * 1.065) / PULSES_RIGHT;
    private final double ENCODER_UNIT_LEFT = (WHEEL_DIAMETER * Math.PI * GEAR_RATIO * 1.07) / PULSES_LEFT;
    private final double ROBOT_DIAMETER = 0.8128;
    private final double EPSILON = 0.3;
    private static double rateP = 0.75;
    private static double rateI = 0.1;
    private static double rateD = 0.0;

    public HotWheels(Jaguar topJag, Jaguar wheelJag, Encoder wheelE, WeirdEncoder topE) {
        top = topJag;
        wheel = wheelJag;
        wheelEncoder = wheelE;
        topEncoder = topE;

        wheelPID = new PIDController(rateP, rateI, rateD, wheelEncoder,
                new PIDOutput() {

                    public void pidWrite(double output) {
                        wheel.set(output);
                    }
                });
    }

    //Set wheel speed
    //Use wheel PID stuff
    public void setSpeed(double speed) {
        wheelPID.setSetpoint(speed);
    }

    //Set top spinning speed
    //Needs work
    public void setRotation() {
    }
}
