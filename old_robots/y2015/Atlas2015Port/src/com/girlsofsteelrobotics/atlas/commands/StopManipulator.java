/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

/**
 *
 * @author appasamysm
 */
//Is this nessisary?
public class StopManipulator extends CommandBase {

    public StopManipulator()
    {
        requires(manipulator);
    }

    @Override
    protected void initialize() {
        manipulator.stopManipulator();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
