/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.subsystems.Kicker;

/**
 * @author Sylvie and Cla
 */
public class TestKickerEncoder extends GosCommandBase {

    private final Kicker m_kicker;
    private double m_direction;

    public TestKickerEncoder(Kicker kicker) {
        m_kicker = kicker;
        addRequirements(m_kicker);
    }

    @Override
    public void initialize() {
        m_kicker.stopJag();
        m_kicker.initEncoders();
        m_kicker.resetEncoders();
        SmartDashboard.putNumber("Direction", 0.0);
    }

    @Override
    public void execute() {
        m_direction = SmartDashboard.getNumber("Direction", 0);
        if (m_direction == 1) {
            m_kicker.setTalon(0.5);
        } else if (m_direction == -1) {
            m_kicker.setTalon(-.5);
        } else {
            m_kicker.stopTalon();
        }
    }

    @Override
    public boolean isFinished() {
        return false; //changeTime > 5;
    }

    @Override
    public void end(boolean interrupted) {
        m_kicker.stopJag();
    }



}
