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


    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        climber.reverseLeftClimberSpike();
        climber.reverseRightClimberSpike();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        climber.stopLeftClimberSpike();
        climber.stopRightClimberSpike();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
