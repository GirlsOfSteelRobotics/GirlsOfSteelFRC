/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.commands;

/**
 * This stops the collector wheel.
 * Drivers can use it to stop the collector wheel manually
 * @author Abby, Sophia, Sonia
 */
//This is a seperate command to stop the wheel aside from the automatic 
public class CollectorWheelStop extends CommandBase{
    
    /**
     * This command requires the collector.
     * @author Sophia, Sonia
     */
    public CollectorWheelStop(){
        requires (collector);
    }
    
    /**
     * There is nothing in this method.
     * @author Sophia, Sonia
     */
    protected void initialize() {
     
    }

     /**
     * There is nothing in this method.
     * @author Sophia, Sonia
     */
    protected void execute() {
         
    }

    /**
     * This command only needs to run once so it returns true.
     * @return true because it needs to run once 
     * @author Sophia, Sonia
     */
    protected boolean isFinished() {
        return true;
        
    }

    /**
     * This method stops the collector wheel.
     * @author Sophia, Sonia
     */
    protected void end() {
        collector.stopCollectorWheel();
        //The collector wheel can now be stopped manually by drivers.
    }

    /**
     * There is nothing in this method.
     * @author Sophia, Sonia
     */
    protected void interrupted() {
        
    }
    
}
