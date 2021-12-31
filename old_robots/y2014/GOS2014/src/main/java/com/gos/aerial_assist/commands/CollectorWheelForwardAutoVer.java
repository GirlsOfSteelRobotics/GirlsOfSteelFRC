/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Timer;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * Moves the collector wheel forward for autonomous and has an is finished case
 *
 * @author Heather, Jisue
 */
public class CollectorWheelForwardAutoVer extends CommandBase {


    private static final double m_time = 2.5; //when 3 seconds pass it will drop the ball

    private final Collector m_collector;
    private final Camera m_camera;
    private double m_startTime;

    public CollectorWheelForwardAutoVer(Collector collector, Camera camera) {
        m_collector = collector;
        m_camera = camera;
    }

    /**
     * There is nothing in this method.
     *
     * @author Sophia, Sonia
     */
    @Override
    protected void initialize() {
        m_startTime = Timer.getFPGATimestamp();
        m_camera.setIsHot(hotAtLeastOnce()); //CommandBase.camera.isGoalHot(); //Get is hot here
        System.out.println("CAMERA IS HOT?::: " + m_camera.isHot());

    }

    /**
     * This rolls the collector wheel forward.
     * It can be used to bring the ball into the trident.
     *
     * @author Sophia, Sonia
     */
    @Override
    protected void execute() {
        m_collector.collectorWheelFoward();
    } //This rolls the wheel forward to bring the ball into the trident

    /**
     * This returns false when we want to move the wheel forward .
     *
     * @return false always
     * @author Sophia, Sonia
     */
    @Override
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - m_startTime > m_time;
        //return CommandBase.collector.isCollectorEngaged();
    }

    /**
     * This stops the collector wheel.
     *
     * @author Sophia, Sonia
     */
    @Override
    protected void end() {
        m_collector.stopCollectorWheel();
        //The wheel stops moving once the collector is engaged and has the ball in its grip
    }

    /**
     * This calls the end() method to stop the collector wheel
     *
     * @author Sophia, Sonia
     */
    @Override
    protected void interrupted() {
        end();
    }

    /*
    If the method sees hot at least one time, it will return true
    */
    private boolean hotAtLeastOnce() {
        for (int i = 0; i < 30; i++) {
            if (Camera.isGoalHot()) {
                return true;
            }
        }
        return false;
    }
}
