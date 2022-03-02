package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.aerial_assist.OI;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;

/**
 * @author Jisue
 */
public class TankDrive extends CommandBase {
    /*  need values for joysticks
        need joystick here
        inputs are joysticks
        outputs are motors

        */
    private final Joystick m_left;
    private final Joystick m_right;
    private final Chassis m_chassis;

    public TankDrive(OI oi, Chassis chassis, Driving driving) {
        addRequirements(driving);
        m_chassis = chassis;

        m_left = oi.getChassisJoystick();
        m_right = oi.getOperatorJoystick();
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
