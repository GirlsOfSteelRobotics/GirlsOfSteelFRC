/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import com.gos.offense2019.OI;
import com.gos.offense2019.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriveByJoystick extends Command {
    private final Chassis m_chassis;
    private final OI m_oi;

    public DriveByJoystick(Chassis chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;
        requires(m_chassis);
    }


    @Override
    protected void initialize() {
        System.out.println("DriveByJoystick init");
    }


    @Override
    protected void execute() {
        // 4 is the axis number right x on the gamepad
        m_chassis.driveByJoystick(m_oi.getLeftUpAndDown(), m_oi.getRightSideToSide());
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        System.out.println("DriveByJoystick end");
    }
}
