/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Collector;

/**
 * This stops the collector wheel.
 * Drivers can use it to stop the collector wheel manually
 *
 * @author Abby, Sophia, Sonia
 */
//This is a seperate command to stop the wheel aside from the automatic
public class CollectorWheelStop extends GosCommandBase {

    private final Collector m_collector;

    /**
     * This command requires the collector.
     *
     * @author Sophia, Sonia
     */
    public CollectorWheelStop(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    /**
     * There is nothing in this method.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void initialize() {

    }

    /**
     * There is nothing in this method.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void execute() {

    }

    /**
     * This command only needs to run once so it returns true.
     *
     * @return true because it needs to run once
     * @author Sophia, Sonia
     */
    @Override
    public boolean isFinished() {
        return true;

    }

    /**
     * This method stops the collector wheel.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollectorWheel();
        //The collector wheel can now be stopped manually by drivers.
    }

    /**
     * There is nothing in this method.
     *
     * @author Sophia, Sonia
     */


}
