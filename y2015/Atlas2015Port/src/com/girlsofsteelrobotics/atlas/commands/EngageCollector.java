/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import com.girlsofsteelrobotics.atlas.Configuration;

/**
 * This command engages the collector arm to the manipulator.  It moves the
 * collector arm downward into its position on the manipulator.
 * @author Sophia, Abby, Sonia
 */
//This command moves arm wheel to retrieve ball.
public class EngageCollector extends CommandBase {

    /**
     * This command uses the collector subsystem.
     * @author Sophia, Sonia
     */
    public EngageCollector() {
        requires(collector);
    }

    /**
     * This method does not have anything in it.
     * @author Sophia, Sonia
     */
    protected void initialize() {
    }
    
    /**
     * This turns on the jag to engage the collector to the pivot arm.
     * @author Sophia, Sonia
     */
    protected void execute() {
        collector.moveCollectorUpOrDown(Configuration.engageCollectorSpeed); //-1 for competition robot, 1 for practice robot
    }//Collector arm moves down to collect and hold ball in trident

    /**
     * This method is constantly called to see if the command is finished.
     * @return False (Never ends)
     * @author Sophia, Sonia
     */
    protected boolean isFinished() {
        return false;//Only for monday (2/10) testing, with use of hardstop
        //return collector.isCollectorEngaged();//Will be used, but not for monday (2/10) testing because there are not limit switches
            //Tells drivers/whoever that the ball is in the trident and is being held by the collector arm
    }

    /**
     * At the end of the command, the collector arm jag is turned off.
     * @author Sophia, Sonia
     */
    protected void end() {
        collector.stopCollector();
        System.out.println("End of Engage Collector Command.");
    }

    /**
     * If the command is interrupted, it calls the end() method.
     * @author Sophia, Sonia
     */
    protected void interrupted() {
        end();
    }

}
