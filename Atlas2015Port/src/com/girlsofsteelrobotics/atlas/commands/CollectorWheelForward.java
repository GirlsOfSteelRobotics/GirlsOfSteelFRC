/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

/**
 * Moves the collector wheel forward.
 * @author Heather, Sophia, Sonia
 */
public class CollectorWheelForward extends CommandBase {
    
    /**
     * There is nothing in this method.
     * @author Sophia, Sonia
     */
    public CollectorWheelForward(){
        //Doesn't have the requires stuff because we want to be able to lift
        // the collector and spin the wheel at the same time
    }

    /**
     * There is nothing in this method.
     * @author Sophia, Sonia
     */
    protected void initialize() {
       
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
        return false;
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
    
    
}
