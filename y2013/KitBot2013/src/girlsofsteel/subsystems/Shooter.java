/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;

/**
 *
 * @author Sylvie
 */
public class Shooter extends Subsystem{

    protected void initDefaultCommand() {
    }
    
    Jaguar shooterJag;
    
    public Shooter(){
        shooterJag = new Jaguar(RobotMap.SHOOTER_JAG);
    }
    
    //Shooting with direct voltage
    public void shootXVolts(double volts){
        //Dividing the desired volts by twelve since it needs to be in a -1 to 1 scale
        shooterJag.set(volts/12.0);
    }
    
    //Disables Jaguar.
    public void disableJag(){
        shooterJag.set(0.0);
    }
}













