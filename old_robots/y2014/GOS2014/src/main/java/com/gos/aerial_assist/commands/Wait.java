/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Sylvie and Heather
 * <p>
 * This waits for the given number of SECONDSSSS
 */
public class Wait extends GosCommandBase {

    private final double m_time;
    private double m_startTime;

    public Wait(double sec) {
        m_time = sec;
    }

    @Override
    public void initialize() {
        m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - m_startTime > m_time;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
