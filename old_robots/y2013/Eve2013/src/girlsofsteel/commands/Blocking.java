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
    private boolean blockerRaised = false;
    private boolean newBlockerRaised = false;
    private boolean oldBlockerRaised = false;
        public Blocking(){
            blockerRaised = false;
            requires (feeder);
        }

    @Override
    protected void initialize() {

    }

    @Override
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

    @Override
    protected boolean isFinished() {
        return oldBlockerRaised != newBlockerRaised;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
