
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import girlsofsteel.commands.CommandBase;
import girlsofsteel.subsystems.UltrasonicSensor;

/**
 *
 * @author
 */
public class TestingUltrasonicSensor extends CommandBase{

    private final UltrasonicSensor m_ultra;

    public TestingUltrasonicSensor(UltrasonicSensor ultra) {
        m_ultra = ultra;
    }

    @Override
    protected void initialize() {
       // ultra.enable();
    }

    @Override
    protected void execute() {
        System.out.println(m_ultra.getRangeInInches());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
