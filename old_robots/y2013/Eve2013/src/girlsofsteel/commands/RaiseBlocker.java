/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

/**
 *
 * @author GirlsOfSTEEL
 */
public class RaiseBlocker extends CommandBase{

    public RaiseBlocker(){
        requires(feeder);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        feeder.pushBlocker();
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
