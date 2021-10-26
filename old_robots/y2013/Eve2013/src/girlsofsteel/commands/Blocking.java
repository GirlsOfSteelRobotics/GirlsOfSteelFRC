/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import girlsofsteel.subsystems.Feeder;

/**
 *
 * @author GirlsOfSTEEL
 */
public class Blocking extends CommandBase{
    boolean blockerRaised = false;
    boolean newBlockerRaised = false;
    boolean oldBlockerRaised = false;
        public Blocking(){
            blockerRaised = false;
            requires (feeder);
        }

    protected void initialize() {

    }

    protected void execute() {
            blockerRaised = Feeder.getIsRaised();
            oldBlockerRaised = blockerRaised;
            if(!blockerRaised){
                feeder.pushBlocker();
                Feeder.setIsRaised(true);
            }
            else{
                feeder.pullBlocker();
                Feeder.setIsRaised(false);
            }
            newBlockerRaised = Feeder.getIsRaised();
    }

    protected boolean isFinished() {
        return oldBlockerRaised != newBlockerRaised;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
