/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Collector;

/**
 * Moves the collector wheel forward.
 *
 * @author Heather, Sophia, Sonia
 */
public class CollectorWheelForward extends GosCommandBase {

    private final Collector m_collector;

    public CollectorWheelForward(Collector collector) {
        m_collector = collector;
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
     * This rolls the collector wheel forward.
     * It can be used to bring the ball into the trident.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void execute() {
        m_collector.collectorWheelFoward();
    } //This rolls the wheel forward to bring the ball into the trident

    /**
     * This returns false when we want to move the wheel forward .
     *
     * @return false always
     * @author Sophia, Sonia
     */
    @Override
    public boolean isFinished() {
        return false;
        //return Command.collector.isCollectorEngaged();
    }

    /**
     * This stops the collector wheel.
     *
     * @author Sophia, Sonia
     */
    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollectorWheel();
        //The wheel stops moving once the collector is engaged and has the ball in its grip
    }

    /**
     * This calls the end() method to stop the collector wheel
     *
     * @author Sophia, Sonia
     */



}
