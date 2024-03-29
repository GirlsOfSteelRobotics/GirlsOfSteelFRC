/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author arushibandi This is the Manual Manipulator PID Tuner on
 * SmartDashboard Arushi can use it.
 */
public class TuneManipulatorPID extends GosCommandBase {

    private double m_p;
    private double m_i;
    private double m_d;
    private double m_setpoint;
    private static final boolean PID = false;
    private final Manipulator m_manipulator;

    public TuneManipulatorPID(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
        m_manipulator.initEncoder();
        if (PID) {
            m_manipulator.resetPIDError();
            m_manipulator.startPID();
            SmartDashboard.putNumber("Pivot P", 0);
            SmartDashboard.putNumber("Pivot I", 0);
            SmartDashboard.putNumber("Pivot D", 0);
            SmartDashboard.putNumber("Pivot setpoint", 0);

            SmartDashboard.putNumber("Pivot encoder distance", 0.0);
            SmartDashboard.putNumber("Pivot encoder get", 0.0);
        }
    }

    @Override
    public void execute() {
        if (PID) {
            m_p = SmartDashboard.getNumber("Pivot P", 0);
            m_i = SmartDashboard.getNumber("Pivot I", 0);
            m_d = SmartDashboard.getNumber("Pivot D", 0);
            m_setpoint = SmartDashboard.getNumber("Pivot setpoint", 0);

            if (m_setpoint != 0) {
                m_manipulator.setSetPoint(m_setpoint);
                m_manipulator.setPID(m_p, m_i, m_d);
            }
            SmartDashboard.putNumber("Pivot Error: ", m_manipulator.getError());
            SmartDashboard.putNumber("P: ", m_p);
        }
        SmartDashboard.putNumber("Pivot encoder distance", m_manipulator.getAbsoluteDistance());
        SmartDashboard.putNumber("REAL PIVOT ENCODER", m_manipulator.getDistance());
        SmartDashboard.putNumber("Pivot encoder get", m_manipulator.getEncoder());

        System.out.println("PIVOT ENCODER: " + m_manipulator.getAbsoluteDistance());
        System.out.print("\tRaw pivot: " + m_manipulator.getRaw());
    }

    @Override
    public boolean isFinished() {
        return m_setpoint > 110 || m_setpoint < -17;

    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.stopManipulator();
        if (PID) {
            m_manipulator.disablePID();
        }
    }


}
