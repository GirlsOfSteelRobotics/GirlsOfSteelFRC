
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import com.gos.aerial_assist.commands.GosCommandBaseBase;
import com.gos.aerial_assist.subsystems.UltrasonicSensor;

/**
 * @author
 */
public class TestingUltrasonicSensor extends GosCommandBaseBase {

    private final UltrasonicSensor m_ultra;

    public TestingUltrasonicSensor(UltrasonicSensor ultra) {
        m_ultra = ultra;
    }

    @Override
    public void initialize() {
        // ultra.enable();
    }

    @Override
    public void execute() {
        System.out.println(m_ultra.getRangeInInches());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }


}
