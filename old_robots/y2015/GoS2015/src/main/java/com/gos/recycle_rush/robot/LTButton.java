package com.gos.recycle_rush.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LTButton extends Trigger {
    /*
     * This is the LT button that when pressed returns positive values
     */
    private final Joystick m_operator;

    public LTButton(Joystick operatorJoystick) {
        m_operator = operatorJoystick;
    }

    @Override
    public boolean get() {
        SmartDashboard.putNumber("Z Left Value", m_operator.getZ());
        return m_operator.getZ() > 0.1;

    }

}
