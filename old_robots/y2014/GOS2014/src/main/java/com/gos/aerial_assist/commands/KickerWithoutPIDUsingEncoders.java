/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Kicker;

/**
 * @author < ( *0* ) >
 */
public class KickerWithoutPIDUsingEncoders extends CommandBase {

    private static final double LOADING_ENCODER_POSITION = .45; // this value is "about"; currentEncoderValue % 360

    private final Kicker m_kicker;

    //0 = loading; 1 = shooting
    @SuppressWarnings("PMD.UnusedFormalParameter")
    public KickerWithoutPIDUsingEncoders(Kicker kicker, int loadingOrShooting) {
        m_kicker = kicker;
        addRequirements(m_kicker);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_kicker.getEncoder() % 360 < LOADING_ENCODER_POSITION) {
            m_kicker.setJag(1.0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_kicker.stopJag();
    }



}
