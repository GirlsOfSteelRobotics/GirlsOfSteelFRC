/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import com.gos.offense2019.subsystems.Chassis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriveByJoystick extends CommandBase {
    private final Chassis m_chassis;
    private final Joystick m_drivingPad;

    private final int m_slowSpeedButton;
    private final double m_speedHigh;
    private final double m_speedLow;

    public DriveByJoystick(Chassis chassis, Joystick drivingPad) {
        m_chassis = chassis;
        m_drivingPad = drivingPad;
        addRequirements(m_chassis);

        m_slowSpeedButton = 3;
        m_speedHigh = 1.0;
        m_speedLow = 0.77;
    }


    @Override
    public void initialize() {
        System.out.println("DriveByJoystick init");
    }


    @Override
    public void execute() {
        // 4 is the axis number right x on the gamepad
        m_chassis.driveByJoystick(getLeftUpAndDown(), getRightSideToSide());
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("DriveByJoystick end");
    }

    private double getLeftUpAndDown() {
        return squaredInput(m_drivingPad.getY()) * drivingSpeed();
    }

    private double getRightSideToSide() {
        return squaredInput(-m_drivingPad.getRawAxis(4)) * drivingSpeed();
    }

    private double drivingSpeed() {
        return m_drivingPad.getRawAxis(m_slowSpeedButton) < 0.1 ? m_speedHigh : m_speedLow;
    }

    private double squaredInput(double inSpeed) {
        double sign = inSpeed / Math.abs(inSpeed);

        return inSpeed * inSpeed * sign;
    }
}
