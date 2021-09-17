/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Timer;
import com.girlsofsteelrobotics.atlas.objects.Camera;

/**
 * Moves the collector wheel forward for autonomous and has an is finished case
 * @author Heather, Jisue
 */
public class CollectorWheelForwardAutoVer extends CommandBase {
    
  
    double time = 2.5; //when 3 seconds pass it will drop the ball
    double startTime;
    public CollectorWheelForwardAutoVer(){
        //Doesn't have the requires stuff because we want to be able to lift
        // the collector and spin the wheel at the same time
    }

    /**
     * There is nothing in this method.
     * @author Sophia, Sonia
     */
    protected void initialize() {
       startTime = Timer.getFPGATimestamp();
       CommandBase.camera.isHot = hotAtLeastOnce();//CommandBase.camera.isGoalHot(); //Get is hot here
       System.out.println("CAMERA IS HOT?::: " + CommandBase.camera.isHot);
               
    }

    /**
     * This rolls the collector wheel forward.  
     * It can be used to bring the ball into the trident.
     * @author Sophia, Sonia
     */
    protected void execute() {
       collector.collectorWheelFoward(); 
    } //This rolls the wheel forward to bring the ball into the trident

    /**
     * This returns false when we want to move the wheel forward .
     * @return false always
     * @author Sophia, Sonia
     */
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > time;
        //return CommandBase.collector.isCollectorEngaged();
    }
    
    /**
     * This stops the collector wheel.
     * @author Sophia, Sonia
     */
    protected void end() {
        collector.stopCollectorWheel();
        //The wheel stops moving once the collector is engaged and has the ball in its grip
    }

    /**
     * This calls the end() method to stop the collector wheel
     * @author Sophia, Sonia
     */
    protected void interrupted() {
        end();
    }
    
    /*
    If the method sees hot at least one time, it will return true
    */
    private boolean hotAtLeastOnce() {
        for(int i = 0; i < 30; i++) {
            if(Camera.isGoalHot())
                return true;
        }
        return false;
    }
}
