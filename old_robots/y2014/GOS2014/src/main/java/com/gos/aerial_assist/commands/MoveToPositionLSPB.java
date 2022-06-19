/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.Configuration;
import com.gos.aerial_assist.objects.LspbPidPlanner;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;

/**
 * @author Sylvie and Jisue
 */
public class MoveToPositionLSPB extends CommandBase {
    private static final double OFF_BY = 0.05;

    private final Chassis m_chassis;
    private final LspbPidPlanner m_leftChassisPlanner;
    private final LspbPidPlanner m_rightChassisPlanner;
    private double m_setPoint;
    private double m_startTime;
    private double m_changeInTime;

    public MoveToPositionLSPB(Chassis chassis, Driving driving, double setPoint) {
        m_chassis = chassis;
        m_leftChassisPlanner = m_chassis.getLeftChassisPlanner();
        m_rightChassisPlanner = m_chassis.getRightChassisPlanner();
        addRequirements(driving);
        m_setPoint = setPoint;
        System.out.println("Moving Made!");
        //         chassis.initEncoders();
        //        chassis.resetEncoders();
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("Setpoint Before", m_setPoint);
        m_setPoint *= Configuration.SIGN_OF_CHASSIS_POSITION_PID_SETPOINT;

        System.out.println("In initialize");
        m_chassis.initEncoders();
        m_chassis.resetEncoders();
        m_chassis.initPositionPIDS();
        m_chassis.resetPositionPIDError();
        m_startTime = System.currentTimeMillis();
        m_leftChassisPlanner.calculateVelocityGraph(m_setPoint);
        m_rightChassisPlanner.calculateVelocityGraph(m_setPoint);
    }

    @Override
    public void execute() {
        //System.out.println("Left Encoder: " + chassis.getLeftEncoderDistance() + "Right Encoder" + chassis.getRightEncoderDistance());
        m_changeInTime = System.currentTimeMillis() - m_startTime;
        m_chassis.setPositionSeparate(m_leftChassisPlanner.getDesiredPosition(m_changeInTime), m_rightChassisPlanner.getDesiredPosition(m_changeInTime));
        SmartDashboard.putNumber("LSPB Left Encoder", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("LSPB Right Encoder", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("LSPB Left Raw", m_chassis.getLeftRaw());
        SmartDashboard.putNumber("LSPB Right Raw", m_chassis.getRightRaw());
        SmartDashboard.putNumber("LSPB Sent this setpoint: ", m_setPoint);
        System.out.println("Left encoder: " + m_chassis.getLeftEncoderDistance() + "\tRight encoder: " + m_chassis.getRightEncoderDistance());
    }

    @Override
    public boolean isFinished() {
        if (m_changeInTime > 3000) {
            System.out.println("Time Exit Out of Chassis");
            return true;
        }
        boolean ret = ((Math.abs(m_chassis.getLeftEncoderDistance() - m_setPoint) < OFF_BY)
            && (Math.abs(m_chassis.getRightEncoderDistance() - m_setPoint) < OFF_BY))
            || (m_chassis.getLeftEncoderDistance() > Math.abs(m_setPoint))
            || (m_chassis.getRightEncoderDistance() > Math.abs(m_setPoint));

        System.out.println("First: " + (Math.abs(m_chassis.getLeftEncoderDistance() - m_setPoint) < OFF_BY));
        System.out.println("Second: " + (Math.abs(m_chassis.getRightEncoderDistance() - m_setPoint) < OFF_BY));
        System.out.println("Third: " + (m_chassis.getLeftEncoderDistance() > Math.abs(m_setPoint)));
        System.out.println("Fourth: " + (m_chassis.getRightEncoderDistance() > Math.abs(m_setPoint)));
        System.out.println("Return value: " + ret);
        return ret;
        //This is bad. doesn't work for negatve. TODO fix later
    }

    @Override
    public void end(boolean interrupted) {

        m_chassis.disablePositionPID();
        m_chassis.stopJags();
        m_setPoint *= Configuration.SIGN_OF_CHASSIS_POSITION_PID_SETPOINT; //To undo what we did before
    }



}
