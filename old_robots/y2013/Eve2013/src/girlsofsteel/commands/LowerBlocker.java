/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

/**
 *
 * @author GirlsOfSTEEL
 */
public class LowerBlocker extends CommandBase{

    public LowerBlocker(){
        requires(feeder);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        feeder.pullBlocker();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
