/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.tests;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import com.girlsofsteelrobotics.atlas.commands.CommandBase;

/**
 *
 * @author sam
 */
public class TestManipulatorJag extends CommandBase {
    Joystick joystick;
    private final int i = 0;

    public TestManipulatorJag () {
        requires(manipulator);
    }

    protected void initialize() {
        joystick = oi.getOperatorJoystick();
        System.out.println("Test Manipulator Jag is running");
    }

    protected void execute() {
//        i++;
//        while(i == 1)
//        {
            System.out.println("Here");
            manipulator.testJagsForward();
            Timer.delay (5);
            manipulator.stopTestJags();
            Timer.delay (5);
            manipulator.testJagsBackward();
            Timer.delay (5);
//        }
        //manipulator.moveJags(joystick.getY());
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
       manipulator.stopTestJags();
    }

    protected void interrupted() {
        end();
    }

}
