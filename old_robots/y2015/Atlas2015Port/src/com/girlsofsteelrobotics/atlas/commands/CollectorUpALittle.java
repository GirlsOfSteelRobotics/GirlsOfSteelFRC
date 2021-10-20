/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Timer;
import com.girlsofsteelrobotics.atlas.Configuration;

/**
 *
 * @author The two lees (minus jisue)
 */
public class CollectorUpALittle extends CommandBase {

    double startTime;
    double putDownTime = 0.15;
    double changeInTime;

    public CollectorUpALittle() {
    //Doesn't require collector because it needs to be used while the collector wheels are spinning
    }

    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
        System.out.println("Collector up a little!!!!!!!!!!!");

        collector.collectorWheelReverse();
    }

    protected void execute() {

        changeInTime = Timer.getFPGATimestamp() - startTime;
        if(changeInTime < putDownTime)
            collector.moveCollectorUpOrDown(Configuration.disengageCollectorSpeed);
        else
            collector.moveCollectorUpOrDown(Configuration.engageCollectorSpeed);
    }

    protected boolean isFinished(){
        return changeInTime > .3;
    }

    protected void end() {
        collector.stopCollector();
        collector.stopCollectorWheel();
    }

    protected void interrupted() {
        end();
    }

}
