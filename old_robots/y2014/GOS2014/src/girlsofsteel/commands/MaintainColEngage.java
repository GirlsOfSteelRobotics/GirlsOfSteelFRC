/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.Configuration;

/**
 *
 * @author Heather
 */
public class MaintainColEngage extends CommandBase {

    //Maintains the collector's dissengaged position'
    public MaintainColEngage() {
        requires(collector);
    }

    protected void initialize() {
    }

    protected void execute() {
       // while (!collector.isCollectorEngaged()) {
            collector.moveCollectorUpOrDown(Configuration.engageCollectorSpeed); //1 for competition bot, -1 for practice bot
        //}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        collector.stopCollector();
    }//the wheel stops spinning if it hasn't already, and the arm stops moving up once it hits the limit switch

    protected void interrupted() {
        end();
    }

}
