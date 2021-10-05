/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019;

import com.gos.offense2019.commands.HatchCollect;
import com.gos.offense2019.commands.Shift;
import com.gos.offense2019.subsystems.HatchCollector;
import com.gos.offense2019.subsystems.Shifters;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private final Joystick m_drivingPad;

    private final int m_slowSpeedButton;
    private final double m_speedHigh;
    private final double m_speedLow;

    public OI() {
        m_drivingPad = new Joystick(0);
        //operatingPad = new Joystick(1);

        POVButton shiftUp = new POVButton(m_drivingPad, 0);
        POVButton shiftDown = new POVButton(m_drivingPad, 180);

        Button hatchRelease = new JoystickButton(m_drivingPad, 5);
        Button hatchGrab = new JoystickButton(m_drivingPad, 6);

        m_slowSpeedButton = 3;
        m_speedHigh = 1.0;
        m_speedLow = 0.77;

        shiftUp.whenPressed(new Shift(Shifters.Speed.kHigh));
        shiftDown.whenPressed(new Shift(Shifters.Speed.kLow));

        hatchRelease.whenPressed(new HatchCollect(HatchCollector.HatchState.kRelease));
        hatchGrab.whenPressed(new HatchCollect(HatchCollector.HatchState.kGrab));
    }

    public double getLeftUpAndDown() {
        return squaredInput(m_drivingPad.getY()) * drivingSpeed();
    }

    public double getRightSideToSide() {
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
