/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

NOTE!!!: The starting configuration of the pivot arm is STRAIGHT UP because 
the pivot arm has to stay within the perimeter restraints

 */
package girlsofsteel.commands;
import girlsofsteel.Configuration;
import girlsofsteel.OI;
/**
 *
 * @author Sophia, Sam, and Abby
 */
//BACKUP TO PID SET ANGLE: this code is super out of date and we have to update it
//multiple definitions of angle need to be updated and changed to the same through all the code

public class SetArmAngle extends CommandBase{

    private double angle = 0.0;
    private double desired;
    
    public SetArmAngle (double desiredAngle){
        requires(manipulator);
        desired = desiredAngle;
    }
    
    protected void initialize() {
        manipulator.init();
    }

    protected void execute() {
        //manipulator.getCurrentAngle(angle);
        System.out.println("Setting the angle! :D");
//        manipulator.moveAngle();
        System.out.println(angle);
        manipulator.moveJag(desired);
        angle = manipulator.getAbsoluteDistance();//TODO check logic

        // Calls SetArmAngle function. Passes angle from constructor.      
       
        //double joystickPostion;
        //joystickPostion = oi.getRightJoystick().getRawAxis(3);
        //System.out.println ("joystickPostition");

//        double m=90;
//        double x = joystickPosition;
//        joystickPosition = m*x/2 + m/2;
        
        //SetArmAngle isn't a method yet....setArmAngle(joystickPosition);
        /* create a varable thats a double
         * read the knob with joystick.getraw axis (3)
         * gives us a double
         * double fills in variable
         * convert double to degrees
         * send number to this command
         */
    }

    protected boolean isFinished() {
        System.out.println("Isfinished: " + manipulator.checkAngle(angle));
        return manipulator.checkAngle(desired);
    }

    protected void end() {
        manipulator.stopJag();
    }

    protected void interrupted() {
        end();
    }
    
}
