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


    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        climber.stopLeftClimberSpike();
        climber.stopRightClimberSpike();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
