package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Jisue
 */
public class TankDrive extends CommandBase {
/*  need values for joysticks
    need joystick here
    inputs are joysticks
    outputs are motors

    */
Joystick left;
Joystick right;

public TankDrive() {
  requires(driving);
}
    @Override
    protected void initialize() {
        left = oi.getChassisJoystick();
        right = oi.getOperatorJoystick();
    }

    @Override
    protected void execute() {
        //System.out.println("Right: " + right.getY() + "\nLeft: " + left.getY());
        chassis.tankDrive(right.getY(),left.getY());

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
