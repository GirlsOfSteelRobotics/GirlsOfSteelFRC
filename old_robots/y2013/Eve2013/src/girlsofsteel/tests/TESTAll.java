/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;


import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Feeder;
import girlsofsteel.subsystems.Gripper;

/**
 *
 * @author kateashwood
 */
public class TestAll extends CommandGroup {

    // Called just before this Command runs the first time
    public TestAll(Feeder feeder, girlsofsteel.subsystems.Chassis chassis, girlsofsteel.subsystems.Shooter shooter, girlsofsteel.subsystems.Climber climber, Gripper gripper) {
        //add all test commands to smartdashboard
        SmartDashboard.putData(new Chassis(chassis));
        SmartDashboard.putData(new Shooter(feeder, shooter));
        SmartDashboard.putData(new Climber(climber, gripper));
        //NOT WORKING FIX****************************************************************

    }

}
