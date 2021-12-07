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



    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        climber.retractLifterPiston();
    }

    @Override
    protected boolean isFinished() {
        return !climber.isPistonExtended();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
