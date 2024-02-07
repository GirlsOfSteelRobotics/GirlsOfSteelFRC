/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * This command disengages the collector arm on the manipulator.  It moves the
 * collector arm upward into its position on the manipulator.
 *
 * @author Sophia, Abby, Sonia
 */
//This command moves arm wheel to retrieve ball.
public class DisengageCollector extends GosCommandBaseBase {

    private final Collector m_collector;

    /**
     * This command uses the collector subsystem.
     *
     * @author Sophia, Sonia
     */
    public DisengageCollector(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    /**
     * This method does not have anything in it.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void initialize() {
    }

    /**
     * This turns on the jag to disengage the collector to the pivot arm.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void execute() {
        //System.out.println("execute---------------------");
        m_collector.moveCollectorUpOrDown(Configuration.DISENGAGE_COLLECTOR_SPEED); //1 for competition bot, -1 for practice bot
        //collector arm moves up to release ball and return to "ready" position to collect the ball again
    }

    /**
     * This method is constantly called to see if the command is finished.
     *
     * @return False (Never ends)
     * @author Sophia, Sonia
     */
    @Override
    public boolean isFinished() {
        //System.out.println(collector.isCollectorDisengaged());
        return false; ////Only for monday (2/10) testing, with use of hardstop
        //return collector.isCollectorDisengaged(); //Will be used, but not for monday (2/10) testing because there are not limit switches
        //Tells drivers/whoever that the arm is no longer in contant with the ball
    }

    /**
     * At the end of the command, the collector arm jag is turned off.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollector();
    } //the wheel stops spinning if it hasn't already, and the arm stops moving up once it hits the limit switch

    /**
     * If the command is interrupted, it calls the end() method.
     *
     * @author Sophia, Sonia
     */



}
