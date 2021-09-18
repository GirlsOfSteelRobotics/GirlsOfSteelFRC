/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.subsystems.Gripper;

/**
 *
 * @author sam
 */

//this command reads the limit switches under the grippers. When press the grippers will close after safely passing the bar
public class CloseGripPastBar extends CommandBase{
    Gripper gripper;

    public CloseGripPastBar(Gripper gripper) {
        this.gripper = gripper;
        
        requires(gripper);
    }
    
    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return gripper.pastBar();
    }

    protected void end() {
        gripper.closeGrip();
    }

    protected void interrupted() {
    }
    
}
