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


    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        climber.forwardLeftClimberSpike();
        climber.forwardRightClimberSpike();
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
