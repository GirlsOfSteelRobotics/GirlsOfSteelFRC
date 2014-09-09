
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import girlsofsteel.commands.CommandBase;

/**
 *
 * @author 
 */
public class TestingUltrasonicSensor extends CommandBase{
 
    public TestingUltrasonicSensor()
    {
        //requires(ultra);
    }
    
    protected void initialize() {
       // ultra.enable();
    }

    protected void execute() {
        System.out.println(ultra.getRangeInInches());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}

