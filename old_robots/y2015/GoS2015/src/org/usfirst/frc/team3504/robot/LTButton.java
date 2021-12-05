package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LTButton extends Trigger{
    /*
     * This is the LT button that when pressed returns positive values
     */
    private final Joystick operator;

    public LTButton(Joystick operatorJoystick){
        operator = operatorJoystick;
    }
    @Override
    public boolean get(){
        SmartDashboard.putNumber("Z Left Value", operator.getZ());
        return operator.getZ() > 0.1 ;

    }

}
