package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RTButton extends Trigger {
    /*
     * This is the RT button that when pressed returns negative  values
     */
    private final Joystick operator;

    public RTButton(Joystick operatorJoystick){
        operator = operatorJoystick;
    }
    @Override
    public boolean get(){
        SmartDashboard.putNumber("Z Right Value", operator.getRawAxis(3));
        return operator.getRawAxis(3) > 0.1 ;

    }

}
