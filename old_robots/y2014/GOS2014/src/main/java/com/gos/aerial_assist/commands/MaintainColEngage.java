/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * @author Heather
 */
public class MaintainColEngage extends GosCommand {

    private final Collector m_collector;

    //Maintains the collector's dissengaged position'
    public MaintainColEngage(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // while (!collector.isCollectorEngaged()) {
        m_collector.moveCollectorUpOrDown(Configuration.ENGAGE_COLLECTOR_SPEED); //1 for competition bot, -1 for practice bot
        //}
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollector();
    } //the wheel stops spinning if it hasn't already, and the arm stops moving up once it hits the limit switch



}
