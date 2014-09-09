/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.tests;

import girlsofsteel.commands.CommandBase;

/**
 *
 * @author appasamysm
 */
public class TestingCollector extends CommandBase {

    //not finished
    private boolean direction;
    private boolean history;

    public TestingCollector() {
        requires(collector);
        direction = true;
    }

    protected void initialize() {
        collector.collectorWheelFoward();
    }

    protected void execute() {

        direction = !direction;
        collector.stopCollectorWheel();
        if (direction) {
            collector.collectorWheelFoward();
        } else {
            collector.collectorWheelReverse();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}
