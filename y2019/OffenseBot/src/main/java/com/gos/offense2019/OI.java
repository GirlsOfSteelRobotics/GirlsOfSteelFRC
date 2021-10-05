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
    private final Joystick drivingPad;
    private final POVButton shiftUp;
    private final POVButton shiftDown;
    private final Button hatchRelease;
    private final Button hatchGrab;

    private final int slowSpeedButton;
    private final double speedHigh;
    private final double speedLow;

    public OI() {
        drivingPad = new Joystick(0);
        //operatingPad = new Joystick(1);

        shiftUp = new POVButton(drivingPad, 0);
        shiftDown = new POVButton(drivingPad, 180);

        hatchRelease = new JoystickButton(drivingPad, 5);
        hatchGrab = new JoystickButton(drivingPad, 6);

        slowSpeedButton = 3;
        speedHigh = 1.0;
        speedLow = 0.77;

        shiftUp.whenPressed(new Shift(Shifters.Speed.kHigh));
        shiftDown.whenPressed(new Shift(Shifters.Speed.kLow));

        hatchRelease.whenPressed(new HatchCollect(HatchCollector.HatchState.kRelease));
        hatchGrab.whenPressed(new HatchCollect(HatchCollector.HatchState.kGrab));
    }

    public double getLeftUpAndDown() {
        return squaredInput(drivingPad.getY()) * drivingSpeed();
    }

    public double getRightSideToSide() {
        return squaredInput(-drivingPad.getRawAxis(4)) * drivingSpeed();
    }

    private double drivingSpeed() {
        return (drivingPad.getRawAxis(slowSpeedButton) < 0.1 ? speedHigh : speedLow);
    }

    private double squaredInput(double inSpeed) {
        double sign = inSpeed / Math.abs(inSpeed);

        return inSpeed * inSpeed * sign;
    }
}
