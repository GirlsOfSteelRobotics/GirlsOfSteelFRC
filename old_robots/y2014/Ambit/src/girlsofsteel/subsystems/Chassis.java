/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.HotWheels;
import girlsofsteel.objects.WeirdEncoder;

/**
 *
 * @author Acer
 */
public class Chassis extends Subsystem
{
    //These are the literal wheel jaguars
    Jaguar wheelJag1 = new Jaguar(RobotMap.WHEEL_JAG_1); // TOP LEFT WHEEL
    Jaguar wheelJag2 = new Jaguar(RobotMap.WHEEL_JAG_2); // TOP RIGHT WHEEL
    Jaguar wheelJag3 = new Jaguar(RobotMap.WHEEL_JAG_3); // BOTTOM RIGHT WHEEL
    Jaguar wheelJag4 = new Jaguar(RobotMap.WHEEL_JAG_4); // BOTTOM LEFT WHEEL
    
    //These are the top spinny things on top of the wheels
    Jaguar topJag1 = new Jaguar(RobotMap.TOP_JAG_1);
    Jaguar topJag2 = new Jaguar(RobotMap.TOP_JAG_2);
    Jaguar topJag3 = new Jaguar(RobotMap.TOP_JAG_3);
    Jaguar topJag4 = new Jaguar(RobotMap.TOP_JAG_4);
    
    //These are the encoders for the wheels
    Encoder wheelJag1Encoder = new Encoder(RobotMap.WHEEL_JAG_1_ENCODER_A, RobotMap.WHEEL_JAG_1_ENCODER_B);
    Encoder wheelJag2Encoder = new Encoder(RobotMap.WHEEL_JAG_2_ENCODER_A, RobotMap.WHEEL_JAG_2_ENCODER_B);
    Encoder wheelJag3Encoder = new Encoder(RobotMap.WHEEL_JAG_3_ENCODER_A, RobotMap.WHEEL_JAG_3_ENCODER_B);
    Encoder wheelJag4Encoder = new Encoder(RobotMap.WHEEL_JAG_4_ENCODER_A, RobotMap.WHEEL_JAG_4_ENCODER_B);
    
    DigitalInput topJag1Input = new DigitalInput(RobotMap.TOP_JAG_1_ENCODER);
    DigitalInput topJag2Input = new DigitalInput(RobotMap.TOP_JAG_2_ENCODER);
    DigitalInput topJag3Input = new DigitalInput(RobotMap.TOP_JAG_3_ENCODER);
    DigitalInput topJag4Input = new DigitalInput(RobotMap.TOP_JAG_4_ENCODER);
    
    // These are the top encoders for the top part of the wheels
    WeirdEncoder topJag1Encoder = new WeirdEncoder(topJag1Input);
    WeirdEncoder topJag2Encoder = new WeirdEncoder(topJag2Input);
    WeirdEncoder topJag3Encoder = new WeirdEncoder(topJag3Input);
    WeirdEncoder topJag4Encoder = new WeirdEncoder(topJag4Input);
    
    HotWheels wheel1 = new HotWheels(topJag1, wheelJag1, wheelJag1Encoder, topJag1Encoder);
    HotWheels wheel2 = new HotWheels(topJag2, wheelJag2, wheelJag2Encoder, topJag2Encoder);  
    HotWheels wheel3 = new HotWheels(topJag3, wheelJag3, wheelJag3Encoder, topJag3Encoder);
    HotWheels wheel4 = new HotWheels(topJag4, wheelJag4, wheelJag4Encoder, topJag4Encoder);
    
    public void initEncoders(){
        wheelJag1Encoder.start();
        wheelJag2Encoder.start();
        wheelJag3Encoder.start();
        wheelJag4Encoder.start();
        topJag1Encoder.start();       
        topJag2Encoder.start();       
        topJag3Encoder.start();
        topJag4Encoder.start();
    }
    
    public void drive(double x, double y, double rotation){
        
        
        
    }
    
    public void stop(){
        
    }
    
    protected void initDefaultCommand() 
    {
        
    }
    
}
