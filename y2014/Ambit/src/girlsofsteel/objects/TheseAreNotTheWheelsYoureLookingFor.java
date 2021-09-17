/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.objects;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Abby and Makenzie
 */
public class TheseAreNotTheWheelsYoureLookingFor {

    //made encoder & jags
    private Encoder speedE;
    private DigitalInput rotationInput;
    private WeirdEncoder rotationE;
    private Jaguar speedJ;
    private Jaguar rotationJ;
    //make encoder unit
//    private final static double WHEEL_DIAMETER=1.0;
//        //Change for real robot
//    private final static double GEAR_RATIO=1.0;
//        //Change for real robot
//    private final static double PULSES=360.0;
//        //Change for real robot
//    private final static double ENCODER_UNIT=(WHEEL_DIAMETER*Math.PI*GEAR_RATIO)
//            /PULSES;
    //make PIDController for speed
    private PIDController PIDController;
    private final static double P = .0;
    private final static double I = .0;
    private final static double D = .0;
    //make PIDConroller for roation
    private PIDController PIDControllerR;
    private final static double PR = .0;
    private final static double IR = .0;
    private final static double DR = .0;

    public TheseAreNotTheWheelsYoureLookingFor(Encoder speedE, DigitalInput rotationInput, Jaguar speedJ, Jaguar rotationJ) {
        //speed
        this.speedE = speedE;
        this.speedJ = speedJ;
        //rotation
        this.rotationInput = rotationInput;
        rotationE=new WeirdEncoder(this.rotationInput);
        this.rotationJ = rotationJ;
        PIDController = new PIDController(P, I, D, this.speedE,
                new PIDOutput() {
                    public void pidWrite(double output) {
                        setSpeedJag(output);
                    }
                });
        PIDControllerR = new PIDController(PR, IR, DR,rotationE,
                new PIDOutput() {
                    public void pidWrite(double output) {
                        setRotationJag(output);
                    }
                });
        PIDControllerR.setContinuous();//make it a circle
        PIDControllerR.setInputRange(0.0, 360.0);//set how big the circle is
    }

    public void setSpeedJag(double speed) {
        speedJ.set(speed);
    }

    public void setSpeed(double speed) {
        PIDController.setSetpoint(speed);
    }

    public void setRotationJag(double rotation) {
        rotationJ.set(rotation);
    }

    public void setRotation(double rotation) {
        
        rotation=rotation%360;
        
        PIDControllerR.setSetpoint(rotation);
    }
}
