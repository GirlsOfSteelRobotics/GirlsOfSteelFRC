/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.objects.Camera;

/**
 * @author Sylvie
 */
public class IsGoalHot extends GosCommandBaseBase {

    private final Camera m_camera;
    private double[] m_hots = new double[10];
    private int m_bool;
    private double m_average;
    private int m_i;

    public IsGoalHot(Camera camera) {
        m_camera = camera;
    }

    @Override
    public void initialize() {
        m_i = 0;
    }

    @Override
    public void execute() {
        if (Camera.isGoalHot()) {
            m_bool = 1;
        } else {
            m_bool = 0;
        }
        m_hots[m_i] = m_bool;
        m_i++;
    }

    @Override
    public boolean isFinished() {
        return m_i > m_hots.length;
    }

    @Override
    public void end(boolean interrupted) {
        for (double hot : m_hots) {
            m_average += hot;
        }
        m_average /= m_hots.length;
        if (m_average >= 0.5) { //If it's dead even, just say that it's HOT
            m_camera.setIsHot(true);
        } else {
            m_camera.setIsHot(false);
        }
    }


}
