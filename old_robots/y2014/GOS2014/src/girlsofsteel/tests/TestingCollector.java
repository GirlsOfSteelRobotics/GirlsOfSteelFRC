/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import girlsofsteel.commands.CommandBase;
import girlsofsteel.subsystems.Collector;

/**
 *
 * @author appasamysm
 */
public class TestingCollector extends CommandBase {

    //not finished
    private final Collector m_collector;
    private boolean m_direction;

    public TestingCollector(Collector collector) {
        m_collector = collector;
        requires(m_collector);
        m_direction = true;
    }

    @Override
    protected void initialize() {
        m_collector.collectorWheelFoward();
    }

    @Override
    protected void execute() {

        m_direction = !m_direction;
        m_collector.stopCollectorWheel();
        if (m_direction) {
            m_collector.collectorWheelFoward();
        } else {
            m_collector.collectorWheelReverse();
        }
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
