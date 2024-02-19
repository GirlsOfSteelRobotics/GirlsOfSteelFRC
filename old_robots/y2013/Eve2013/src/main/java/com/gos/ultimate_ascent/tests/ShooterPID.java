/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.tests;

import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.commands.GosCommandBaseBase;

/**
 * @author Sylvie
 */
public class ShooterPID extends GosCommandBaseBase {

    private final Shooter m_shooter;
    private double m_speed;

    public ShooterPID(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
        SmartDashboard.putNumber("Shooter Speed", 0.0);
        SmartDashboard.putNumber("p value", 0.0);
        SmartDashboard.putNumber("i value", 0.0);
        SmartDashboard.putBoolean("Click when Done Testing Shooter PID", false);
    }

    @Override
    public void execute() {
        m_speed = SmartDashboard.getNumber("Shooter Speed", 0.0);
        double p = SmartDashboard.getNumber("p value", 0.0);
        double i = SmartDashboard.getNumber("i value", 0.0);

        m_shooter.setPIDValues(p, i, 0.0);
        m_shooter.setPIDspeed(m_speed);

        //print encoder value
        SmartDashboard.putNumber("Encoder Value",
            m_shooter.getEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return !SmartDashboard.getBoolean("Click when Done Testing Shooter PID", true);
    }

    @Override
    public void end(boolean interrupted) {
    }



}
