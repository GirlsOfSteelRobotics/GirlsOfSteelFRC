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

    protected void initialize() {
    }

    protected void execute() {
        feeder.pushBlocker();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
