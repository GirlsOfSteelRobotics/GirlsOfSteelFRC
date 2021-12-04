/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.girlsofsteelrobotics.atlas.RobotMap;
import com.girlsofsteelrobotics.atlas.commands.CommandBase;

/**
 *
 * @author Abby
 */
public class TestCollector extends CommandBase {

    @Override
    protected void initialize() {
        SmartDashboard.putNumber(RobotMap.CollectorJagSpeed, 0.0);
        SmartDashboard.putBoolean(RobotMap.CollectorWheelSpikeForward, false);
        SmartDashboard.putBoolean(RobotMap.CollectorWheelSpikeBackward, false);
        SmartDashboard.putNumber(RobotMap.CollectorEncoderReader, 0.0);

    }

    @Override
    protected void execute() {
        collector.moveCollectorUpOrDown(SmartDashboard.getNumber(RobotMap.CollectorJagSpeed, 0));
        if (SmartDashboard.getBoolean(RobotMap.CollectorWheelSpikeForward, false) == true) {
            collector.collectorWheelFoward();
            // forward
        } else{
        collector.stopCollectorWheel();
        }
        if (SmartDashboard.getBoolean(RobotMap.CollectorWheelSpikeBackward, false) == true) {
            collector.collectorWheelReverse();
            // backward
        } else {
            collector.stopCollectorWheel();
        }
//        if (SmartDashboard.getBoolean(RobotMap.CollectorWheelSpikeStop, false) == true) {
//            collector.stopCollectorWheel();
//        }
        double collectorEncoderValue = collector.getCollectorSpeed();
        SmartDashboard.putNumber(RobotMap.CollectorEncoderReader, collectorEncoderValue);
        //This should print out the encoder vlaue on screen. Maybe.

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
