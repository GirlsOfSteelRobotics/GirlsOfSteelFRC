/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author sam
 */
public class TestManipulatorJag extends CommandBase {
    private Joystick joystick;
    private final int i = 0;

    public TestManipulatorJag () {
        requires(manipulator);
    }

    @Override
    protected void initialize() {
        joystick = oi.getOperatorJoystick();
        System.out.println("Test Manipulator Jag is running");
    }

    @Override
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

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
       manipulator.stopTestJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
