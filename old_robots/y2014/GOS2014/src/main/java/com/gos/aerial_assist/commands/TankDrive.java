package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;

/**
 * @author Jisue
 */
public class TankDrive extends GosCommandBase {
    /*  need values for joysticks
        need joystick here
        inputs are joysticks
        outputs are motors

        */
    private final Joystick m_left;
    private final Joystick m_right;
    private final Chassis m_chassis;

    public TankDrive(Joystick chassisJoystick, Joystick operatorJoystick, Chassis chassis, Driving driving) {
        addRequirements(driving);
        m_chassis = chassis;

        m_left = chassisJoystick;
        m_right = operatorJoystick;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        //System.out.println("Right: " + right.getY() + "\nLeft: " + left.getY());
        m_chassis.tankDrive(m_right.getY(), m_left.getY());

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
    }



}
