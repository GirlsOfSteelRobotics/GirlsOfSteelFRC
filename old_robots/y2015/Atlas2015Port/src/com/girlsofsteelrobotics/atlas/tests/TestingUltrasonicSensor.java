
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.tests;

import com.girlsofsteelrobotics.atlas.commands.CommandBase;

/**
 *
 * @author
 */
public class TestingUltrasonicSensor extends CommandBase{

    public TestingUltrasonicSensor()
    {
        //requires(ultra);
    }

    @Override
    protected void initialize() {
       // ultra.enable();
    }

    @Override
    protected void execute() {
        System.out.println(ultra.getRangeInInches());
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
