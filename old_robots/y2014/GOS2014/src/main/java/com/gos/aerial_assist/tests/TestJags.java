/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.commands.GosCommand;
import com.gos.aerial_assist.subsystems.Chassis;

/**
 * @author user
 */
public class TestJags extends GosCommand {

    private final Chassis m_chassis;

    public TestJags(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
    }

    @Override
    public void execute() {
        m_chassis.setJags(1.0);
        SmartDashboard.putNumber("Left Encoder", m_chassis.getLeftEncoder());
        SmartDashboard.putNumber("Right Encoder", m_chassis.getRightEncoder());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setJags(0.0);
    }



}
