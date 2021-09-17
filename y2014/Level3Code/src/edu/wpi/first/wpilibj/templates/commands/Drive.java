/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author appasamysm
 */
public class Drive extends CommandBase {
    //CommandBase is the superclass. Drive extends it~
    
    Joystick joystick;
    double xCoordinate;
    double yCoordinate;
    //here we are initializing our variables
    
    public Drive()
    {
        requires(chassis);
        //requires means that it references the chassis 
    }
    
    protected void initialize() {
        joystick = oi.getJoystick();
        //sets the joystick being used in this method 
        //equal to the joystick being used in the input output (io) class
    }

    protected void execute() {
        xCoordinate = joystick.getX();
        yCoordinate = -joystick.getY();
        
        chassis.startJags(yCoordinate, xCoordinate);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}
