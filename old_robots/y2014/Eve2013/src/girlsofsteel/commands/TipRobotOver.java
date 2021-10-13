/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class TipRobotOver extends CommandBase{

    public TipRobotOver() {
        requires (climber);
    }

    //will extend the piston to tip the robot over onto the pyramid
    
    
    protected void initialize() {
        
        
    }

    protected void execute() {
        climber.extendLifterPiston();
    }

    protected boolean isFinished() {
        return climber.isPistonExtended();
    }

    protected void end() {
        
    }

    protected void interrupted() {
    }
    
}
