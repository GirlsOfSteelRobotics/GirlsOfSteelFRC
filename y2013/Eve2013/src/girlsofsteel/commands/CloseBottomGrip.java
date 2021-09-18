/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class CloseBottomGrip extends CommandBase{

    public CloseBottomGrip() {
        
        requires(bottomGripper);
    }

    //Command for closing the bottom gripper around the pyramid
    
    
    protected void initialize() {
         
    }

    protected void execute() {
       
        bottomGripper.closeGrip();  
    }

    protected boolean isFinished() {
            return true;
        
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
