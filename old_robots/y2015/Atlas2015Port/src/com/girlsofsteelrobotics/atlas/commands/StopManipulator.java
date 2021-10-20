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

    protected void initialize() {
        manipulator.stopManipulator();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}
