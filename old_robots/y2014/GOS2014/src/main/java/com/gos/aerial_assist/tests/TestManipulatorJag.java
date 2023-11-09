/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.Timer;
import com.gos.aerial_assist.commands.GosCommand;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author sam
 */
public class TestManipulatorJag extends GosCommand {

    private final Manipulator m_manipulator;

    public TestManipulatorJag(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
        System.out.println("Test Manipulator Jag is running");
    }

    @Override
    public void execute() {
        //        i++;
        //        while(i == 1)
        //        {
        System.out.println("Here");
        m_manipulator.testJagsForward();
        Timer.delay(5);
        m_manipulator.stopTestJags();
        Timer.delay(5);
        m_manipulator.testJagsBackward();
        Timer.delay(5);
        //        }
        //manipulator.moveJags(joystick.getY());
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.stopTestJags();
    }



}
