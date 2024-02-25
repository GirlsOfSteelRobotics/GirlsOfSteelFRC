/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import com.gos.aerial_assist.commands.GosCommandBase;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * @author appasamysm
 */
public class TestingCollector extends GosCommandBase {

    //not finished
    private final Collector m_collector;
    private boolean m_direction;

    public TestingCollector(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
        m_direction = true;
    }

    @Override
    public void initialize() {
        m_collector.collectorWheelFoward();
    }

    @Override
    public void execute() {

        m_direction = !m_direction;
        m_collector.stopCollectorWheel();
        if (m_direction) {
            m_collector.collectorWheelFoward();
        } else {
            m_collector.collectorWheelReverse();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
