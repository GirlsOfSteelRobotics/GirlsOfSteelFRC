/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.objects.LSPBPIDPlanner;
import com.gos.aerial_assist.subsystems.Kicker;

/**
 * @author Sylvie
 */
public class MoveKicker extends CommandBase {
    private static final double m_allowedOffBy = 0.05; //Degrees
    private static final double m_loaded = 0.0; //Starting position is loaded pos
    private static final double m_shoot = 0.111111; //The angle degree needed to kick

    private final Kicker m_kicker;
    private final LSPBPIDPlanner m_kickerPlanner;
    private double m_encoderValue350Modded;
    private double m_setpoint;
    //This is one tooth away from kicking
    private final int m_pos;
    private boolean m_firstTime;
    private double m_startTime;
    private double m_changeInTime;
    private double m_setPointToSendToPID;

    public MoveKicker(Kicker kicker, int pos) {
        m_kicker = kicker;
        m_kickerPlanner = m_kicker.getKickerPlanner();
        m_pos = pos;
    }

    @Override
    protected void initialize() {
        m_firstTime = true;
    }

    @Override
    protected void execute() {
        if (m_firstTime) {
            switch (m_pos) {
            case 0: //loading
                m_setpoint = m_loaded;
                break;
            case 1: //kick
                m_setpoint = m_shoot;
                break;
            default:
                System.out.println("Error! Not a valid input parameter");
                break;
            }
            m_kickerPlanner.calculateVelocityGraph(m_setpoint);
            m_startTime = System.currentTimeMillis(); //MILLISECONDS
            m_firstTime = false;
        }
        m_changeInTime = System.currentTimeMillis() - m_startTime;
        m_setPointToSendToPID = m_kickerPlanner.getDesiredPosition(m_changeInTime);
        m_kicker.setPIDPosition(m_setPointToSendToPID);
        m_encoderValue350Modded = m_kicker.getEncoderDistance() % 360;
    }

    @Override
    protected boolean isFinished() {
        m_encoderValue350Modded = m_kicker.getEncoderDistance() % 360;
        boolean thereYet = Math.abs(m_encoderValue350Modded - m_setpoint) < m_allowedOffBy;
        boolean over = Math.abs(m_encoderValue350Modded) > Math.abs(m_setpoint);
        return thereYet || over;
    }

    @Override
    protected void end() {
        m_kicker.holdPosition();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
