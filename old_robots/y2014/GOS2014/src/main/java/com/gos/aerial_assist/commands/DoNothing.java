/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Driving;

/**
 * @author ClarVie
 */
public class DoNothing extends GosCommandBaseBase {

    public DoNothing(Driving driving) {
        addRequirements(driving);
    }

    @Override
    public void initialize() {
        //smanipulator.disablePID();
    }

    @Override
    public void execute() {
        //manipulator.stopJag();
        //        chassis.stopJags();
        //        collector.stopCollector();
        //        collector.stopCollectorWheel();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //manipulator.stopJag();
        //        chassis.stopJags();
        //        collector.stopCollector();
        //        collector.stopCollectorWheel();
        //manipulator.startPID();
    }



}
