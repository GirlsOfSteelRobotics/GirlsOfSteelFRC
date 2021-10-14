/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class RetractClimberPiston extends CommandBase{

    public RetractClimberPiston() {
        requires (climber);
    }
    
    

    protected void initialize() {
        
    }

    protected void execute() {
        climber.retractLifterPiston();
    }

    protected boolean isFinished() {
        return !climber.isPistonExtended();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
