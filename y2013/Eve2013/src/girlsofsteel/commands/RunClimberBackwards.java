/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class RunClimberBackwards extends CommandBase {

    public RunClimberBackwards() {
         requires (climber);
    }

    //Command for starting the motors to begin climbing
    
    
    protected void initialize() {
    }

    protected void execute() {
        climber.reverseLeftClimberSpike();
        climber.reverseRightClimberSpike();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        climber.stopLeftClimberSpike();
        climber.stopRightClimberSpike();
    }

    protected void interrupted() {
        end();
    }
    
}