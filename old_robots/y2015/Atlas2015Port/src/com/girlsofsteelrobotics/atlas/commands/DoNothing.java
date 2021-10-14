/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

/**
 *
 * @author ClarVie
 */
public class DoNothing extends CommandBase {

    public DoNothing() {
        requires(driving);
    }

    protected void initialize() {
        //smanipulator.disablePID();
    }

    protected void execute() {
        //manipulator.stopJag();
//        chassis.stopJags();
//        collector.stopCollector();
//        collector.stopCollectorWheel();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        //manipulator.stopJag();
//        chassis.stopJags();
//        collector.stopCollector();
//        collector.stopCollectorWheel();
        //manipulator.startPID();
    }

    protected void interrupted() {
        end();
    }

}
