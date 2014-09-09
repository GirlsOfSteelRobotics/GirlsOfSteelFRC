/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.RobotMap;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author sophia
 */
public class TestManipulator extends CommandBase{
    
    double speed;
    public TestManipulator(){
        requires(manipulator);
    }

    protected void initialize() {
        SmartDashboard.putBoolean(RobotMap.manipulatorSD, true);
    }

    protected void execute() {
        speed = SmartDashboard.getNumber(RobotMap.manipulatorSD, 0.0);
        if(SmartDashboard.getBoolean(RobotMap.manipulatorSD, false)){
            manipulator.setJag(speed);
        }else{
            manipulator.setJag(0.0);
        }
    }

    protected boolean isFinished() {
        return false; //TODO: im not sure what this should be
    }

    protected void end() {
        manipulator.stopJag();
    }

    protected void interrupted() {
        end();
    }
    
           
}
