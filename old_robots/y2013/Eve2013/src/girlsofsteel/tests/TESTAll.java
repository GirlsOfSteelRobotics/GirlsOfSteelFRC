/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;


import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 * @author kateashwood
 */
public class TESTAll extends CommandGroup {

    // Called just before this Command runs the first time
    public TESTAll() {
        //add all test commands to smartdashboard
        SmartDashboard.putData(new Chassis());
        SmartDashboard.putData(new Shooter());
        SmartDashboard.putData(new Climber());
        //NOT WORKING FIX****************************************************************
        
    }

}
