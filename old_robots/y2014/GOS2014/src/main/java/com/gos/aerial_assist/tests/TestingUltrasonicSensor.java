
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import com.gos.aerial_assist.commands.CommandBase;
import com.gos.aerial_assist.subsystems.UltrasonicSensor;

/**
 * @author
 */
public class TestingUltrasonicSensor extends CommandBase {

    private final UltrasonicSensor m_ultra;

    public TestingUltrasonicSensor(UltrasonicSensor ultra) {
        m_ultra = ultra;
    }

    @Override
    protected void initialize() {
        // ultra.enable();
    }

    @Override
    protected void execute() {
        System.out.println(m_ultra.getRangeInInches());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
