/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.Timer;
import com.gos.aerial_assist.commands.CommandBase;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author sam
 */
public class TestManipulatorJag extends CommandBase {

    private final Manipulator m_manipulator;

    public TestManipulatorJag(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    @Override
    protected void initialize() {
        System.out.println("Test Manipulator Jag is running");
    }

    @Override
    protected void execute() {
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
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        m_manipulator.stopTestJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
