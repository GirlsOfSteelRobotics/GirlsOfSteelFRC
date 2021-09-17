/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;

/**
 *
 * @author user
 */
public class Manipulator extends Subsystem {
    
private Solenoid extendPiston1Solenoid;
private Solenoid retractPiston1Solenoid;
private Solenoid extendPiston2Solenoid;
private Solenoid retractPiston2Solenoid;

    public Manipulator() {
        
    extendPiston1Solenoid = new Solenoid(
            RobotMap.MODULE, RobotMap.EXTEND_PISTON_ONE_SOLENOID);
    
    retractPiston1Solenoid = new Solenoid(
            RobotMap.MODULE, RobotMap.RETRACT_PISTON_ONE_SOLENOID);
    
    extendPiston2Solenoid = new Solenoid(
            RobotMap.MODULE, RobotMap.EXTEND_PISTON_TWO_SOLENOID);
    
    retractPiston2Solenoid = new Solenoid(
            RobotMap.MODULE, RobotMap.RETRACT_PISTON_TWO_SOLENOID);
}

    public void extendPistonOne() {
        extendPiston1Solenoid.set(true);
        retractPiston1Solenoid.set(false);
    }
    public void retractPistonOne() {
        extendPiston1Solenoid.set(false);
        retractPiston1Solenoid.set(true);
    }
    public void extendPistonTwo() {
        extendPiston2Solenoid.set(true);
        retractPiston2Solenoid.set(false);
    }
    public void retractPistonTwo() {
        extendPiston2Solenoid.set(false);
        retractPiston2Solenoid.set(true);
    }

    protected void initDefaultCommand() {
    }
    
}
