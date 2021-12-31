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
public class CollectorUpALittle extends CommandBase {

    private static final double m_putDownTime = 0.15;

    private final Collector m_collector;
    private double m_startTime;
    private double m_changeInTime;

    public CollectorUpALittle(Collector collector) {
        m_collector = collector;
    }

    @Override
    protected void initialize() {
        m_startTime = Timer.getFPGATimestamp();
        System.out.println("Collector up a little!!!!!!!!!!!");

        m_collector.collectorWheelReverse();
    }

    @Override
    protected void execute() {

        m_changeInTime = Timer.getFPGATimestamp() - m_startTime;
        if (m_changeInTime < m_putDownTime) {
            m_collector.moveCollectorUpOrDown(Configuration.disengageCollectorSpeed);
        } else {
            m_collector.moveCollectorUpOrDown(Configuration.engageCollectorSpeed);
        }
    }

    @Override
    protected boolean isFinished() {
        return m_changeInTime > .3;
    }

    @Override
    protected void end() {
        m_collector.stopCollector();
        m_collector.stopCollectorWheel();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
