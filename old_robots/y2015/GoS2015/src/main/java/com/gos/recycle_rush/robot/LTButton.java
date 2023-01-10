package com.gos.recycle_rush.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class LTButton extends Trigger {
    public LTButton(Joystick operatorJoystick) {
        super(() -> operatorJoystick.getZ() > 0.1);
    }
}
