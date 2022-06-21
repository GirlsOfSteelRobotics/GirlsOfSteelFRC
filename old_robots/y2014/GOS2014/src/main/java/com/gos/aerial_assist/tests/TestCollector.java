/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.RobotMap;
import com.gos.aerial_assist.commands.CommandBase;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * @author Abby
 */
public class TestCollector extends CommandBase {

    private final Collector m_collector;

    public TestCollector(Collector collector) {
        m_collector = collector;
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber(RobotMap.COLLECTOR_JAG_SPEED, 0.0);
        SmartDashboard.putBoolean(RobotMap.COLLECTOR_WHEEL_SPIKE_FORWARD, false);
        SmartDashboard.putBoolean(RobotMap.COLLECTOR_WHEEL_SPIKE_BACKWARD, false);
        SmartDashboard.putNumber(RobotMap.COLLECTOR_ENCODER_READER, 0.0);

    }

    @Override
    public void execute() {
        m_collector.moveCollectorUpOrDown(SmartDashboard.getNumber(RobotMap.COLLECTOR_JAG_SPEED, 0));
        if (SmartDashboard.getBoolean(RobotMap.COLLECTOR_WHEEL_SPIKE_FORWARD, false)) {
            m_collector.collectorWheelFoward();
            // forward
        } else {
            m_collector.stopCollectorWheel();
        }
        if (SmartDashboard.getBoolean(RobotMap.COLLECTOR_WHEEL_SPIKE_BACKWARD, false)) {
            m_collector.collectorWheelReverse();
            // backward
        } else {
            m_collector.stopCollectorWheel();
        }
        //        if (SmartDashboard.getBoolean(RobotMap.CollectorWheelSpikeStop, false) == true) {
        //            collector.stopCollectorWheel();
        //        }
        double collectorEncoderValue = m_collector.getCollectorSpeed();
        SmartDashboard.putNumber(RobotMap.COLLECTOR_ENCODER_READER, collectorEncoderValue);
        //This should print out the encoder vlaue on screen. Maybe.

    }

    @Override
    public boolean isFinished() {
        return false;

    }

    @Override
    public void end(boolean interrupted) {

    }



}
