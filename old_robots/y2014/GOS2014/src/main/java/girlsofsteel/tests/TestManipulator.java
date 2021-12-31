/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.RobotMap;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.subsystems.Manipulator;

/**
 * @author sophia
 */
public class TestManipulator extends CommandBase {

    private final Manipulator m_manipulator;
    private double m_speed;

    public TestManipulator(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    @Override
    protected void initialize() {
        SmartDashboard.putBoolean(RobotMap.manipulatorSD, true);
    }

    @Override
    protected void execute() {
        m_speed = SmartDashboard.getNumber(RobotMap.manipulatorSD, 0.0);
        if (SmartDashboard.getBoolean(RobotMap.manipulatorSD, false)) {
            m_manipulator.setJag(m_speed);
        } else {
            m_manipulator.setJag(0.0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false; //TODO: im not sure what this should be
    }

    @Override
    protected void end() {
        m_manipulator.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }


}
