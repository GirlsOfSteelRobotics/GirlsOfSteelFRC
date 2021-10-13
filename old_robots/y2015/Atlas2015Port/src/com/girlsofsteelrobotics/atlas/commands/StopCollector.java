/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

/**
 *
 * @author user
 */
public class StopCollector extends CommandBase {

    public StopCollector() {
        requires(collector);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        collector.moveCollectorUpOrDown(0.0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}
