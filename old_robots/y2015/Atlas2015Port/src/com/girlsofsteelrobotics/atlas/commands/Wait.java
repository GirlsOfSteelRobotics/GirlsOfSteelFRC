/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Sylvie and Heather
 *
 * This waits for the given number of SECONDSSSS
 */
public class Wait extends CommandBase {

    double time;
    double startTime;

    public Wait(double sec) {
        time = sec;
    }

    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > time;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
