/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.tests;

import com.girlsofsteelrobotics.atlas.commands.CommandBase;

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

    @Override
    protected void initialize() {
        collector.collectorWheelFoward();
    }

    @Override
    protected void execute() {

        direction = !direction;
        collector.stopCollectorWheel();
        if (direction) {
            collector.collectorWheelFoward();
        } else {
            collector.collectorWheelReverse();
        }
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
    }

}
