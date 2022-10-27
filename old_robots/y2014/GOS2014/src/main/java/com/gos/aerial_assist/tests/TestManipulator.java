/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.RobotMap;
import com.gos.aerial_assist.commands.CommandBase;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author sophia
 */
public class TestManipulator extends CommandBase {

    private final Manipulator m_manipulator;
    private double m_speed;

    public TestManipulator(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean(RobotMap.MANIPULATOR_SD, true);
    }

    @Override
    public void execute() {
        m_speed = SmartDashboard.getNumber(RobotMap.MANIPULATOR_SD, 0.0);
        if (SmartDashboard.getBoolean(RobotMap.MANIPULATOR_SD, false)) {
            m_manipulator.setJag(m_speed);
        } else {
            m_manipulator.setJag(0.0);
        }
    }

    @Override
    public boolean isFinished() {
        return false; //TODO: im not sure what this should be
    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.stopJag();
    }




}
