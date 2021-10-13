/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author sam
 */
public class StartClimbMotors extends CommandBase {

    public StartClimbMotors() {
         requires (climber);
    }

    //Command for starting the motors to begin climbing
    
    
    protected void initialize() {
    }

    protected void execute() {
        climber.forwardLeftClimberSpike();
        climber.forwardRightClimberSpike();
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