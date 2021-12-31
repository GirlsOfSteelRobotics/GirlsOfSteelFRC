/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.RobotMap;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.subsystems.Collector;

/**
 * @author Abby
 */
public class TestCollector extends CommandBase {

    private final Collector m_collector;

    public TestCollector(Collector collector) {
        m_collector = collector;
    }

    @Override
    protected void initialize() {
        SmartDashboard.putNumber(RobotMap.CollectorJagSpeed, 0.0);
        SmartDashboard.putBoolean(RobotMap.CollectorWheelSpikeForward, false);
        SmartDashboard.putBoolean(RobotMap.CollectorWheelSpikeBackward, false);
        SmartDashboard.putNumber(RobotMap.CollectorEncoderReader, 0.0);

    }

    @Override
    protected void execute() {
        m_collector.moveCollectorUpOrDown(SmartDashboard.getNumber(RobotMap.CollectorJagSpeed, 0));
        if (SmartDashboard.getBoolean(RobotMap.CollectorWheelSpikeForward, false)) {
            m_collector.collectorWheelFoward();
            // forward
        } else {
            m_collector.stopCollectorWheel();
        }
        if (SmartDashboard.getBoolean(RobotMap.CollectorWheelSpikeBackward, false)) {
            m_collector.collectorWheelReverse();
            // backward
        } else {
            m_collector.stopCollectorWheel();
        }
//        if (SmartDashboard.getBoolean(RobotMap.CollectorWheelSpikeStop, false) == true) {
//            collector.stopCollectorWheel();
//        }
        double collectorEncoderValue = m_collector.getCollectorSpeed();
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
