package com.gos.recycle_rush.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RTButton extends Trigger {
    public RTButton(Joystick operatorJoystick) {
        super(() -> operatorJoystick.getRawAxis(3) > 0.1);
    }
}
