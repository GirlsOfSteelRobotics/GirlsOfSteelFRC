package com.gos.recycle_rush.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RTButton extends Trigger {
    /*
     * This is the RT button that when pressed returns negative  values
     */
    private final Joystick m_operator;

    public RTButton(Joystick operatorJoystick) {
        m_operator = operatorJoystick;
    }

    @Override
    public boolean get() {
        SmartDashboard.putNumber("Z Right Value", m_operator.getRawAxis(3));
        return m_operator.getRawAxis(3) > 0.1;

    }

}
