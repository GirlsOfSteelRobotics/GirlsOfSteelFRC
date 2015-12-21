/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;


import edu.wpi.first.wpilibj.SimpleRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {

    // create Jaguar objects for right and left motors on electronics test board 
    //  right is channel 1
    //  left is channel 4
    
    Jaguar rightJaguar = new Jaguar(1);
    Jaguar leftJaguar = new Jaguar(4);
    
    // create Joystick objects for right and left joysticks
    /* axis definitions for GameStop controller
     * 1: left stick, left (-1) right (1)
     * 2: left stick, up(-1) down (1)
     * 3: right stick, left (-1) right (1) (but it is binary centers a -1)
     * 4: right stick, up (-1) down (1) (but it centers at 0.378)
     * 5: track button, left (-1) right (1) (binary)
     * 6: track button, up (-1) down (1) (binary)
     * 
     * axis definitions for Logitech X3D joystick
     * 1: stick, left (-1) right (1)
     * 2: stick, up (-1) down (1)
     * 3: stick twist, left/CCW (-1) right/CW (1)
     * 4: front lever, up (-1) down (1)
     * 
     */
    Joystick rightStick = new Joystick(1);
    Joystick leftStick = new Joystick(2);
    
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        

        /**
         * This sequence of commands spins the right motor for 3 seconds,
         * then spins both motors for 3 seconds, then spins the left motor
         * for 3 seconds
         */

        rightJaguar.set(0.5);   // start right motor at half speed
        Timer.delay(3.0);       // wait 3 seconds
        leftJaguar.set(0.5);    // start left motor at half speed
        Timer.delay(3.0);       // wait 3 seconds
        rightJaguar.set(0.0);   // stop right motor
        Timer.delay(3.0);       // wait 3 seconds
        leftJaguar.set(0.0);    // stop left motor
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

        while (isOperatorControl() && isEnabled()) {
            
            // read raw left and right joystick up/down values
            // (up/down is channel 2 on both types of joysticks)
            double leftCommand = leftStick.getRawAxis(2);
            double rightCommand = rightStick.getRawAxis(2);

            // print joystick values to monitor 
            System.out.print("left: " + leftCommand + ";  ");
            System.out.println("right: " + rightCommand);   
            
            // send joystick commands to motor controllers
            rightJaguar.set(rightCommand);
            leftJaguar.set(leftCommand);
      
            // wait 1/100 of a second before running loop again
            Timer.delay(0.01);
        }
                    
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
