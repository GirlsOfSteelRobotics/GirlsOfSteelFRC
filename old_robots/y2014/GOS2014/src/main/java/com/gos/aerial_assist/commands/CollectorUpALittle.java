/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Timer;
import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * @author The two lees (minus jisue)
 */
public class CollectorUpALittle extends GosCommandBaseBase {

    private static final double PUT_DOWN_TIME = 0.15;

    private final Collector m_collector;
    private double m_startTime;
    private double m_changeInTime;

    public CollectorUpALittle(Collector collector) {
        m_collector = collector;
    }

    @Override
    public void initialize() {
        m_startTime = Timer.getFPGATimestamp();
        System.out.println("Collector up a little!!!!!!!!!!!");

        m_collector.collectorWheelReverse();
    }

    @Override
    public void execute() {

        m_changeInTime = Timer.getFPGATimestamp() - m_startTime;
        if (m_changeInTime < PUT_DOWN_TIME) {
            m_collector.moveCollectorUpOrDown(Configuration.DISENGAGE_COLLECTOR_SPEED);
        } else {
            m_collector.moveCollectorUpOrDown(Configuration.ENGAGE_COLLECTOR_SPEED);
        }
    }

    @Override
    public boolean isFinished() {
        return m_changeInTime > .3;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollector();
        m_collector.stopCollectorWheel();
    }



}
