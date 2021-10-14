/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class StopClimbMotors extends CommandBase {

    public StopClimbMotors() {
          requires (climber);
    }

    //stops the motors used for climbing
    
    
    protected void initialize() {
      
    }

    protected void execute() {
        climber.stopLeftClimberSpike();
        climber.stopRightClimberSpike();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
