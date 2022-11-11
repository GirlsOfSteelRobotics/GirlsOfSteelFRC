/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019;

import com.gos.offense2019.commands.DriveByJoystick;
import com.gos.offense2019.commands.HatchCollect;
import com.gos.offense2019.commands.Shift;
import com.gos.offense2019.subsystems.Chassis;
import com.gos.offense2019.subsystems.HatchCollector;
import com.gos.offense2019.subsystems.Shifters;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private final Joystick m_drivingPad;

    public OI(Chassis chassis, Shifters shifters, HatchCollector hatch) {
        m_drivingPad = new Joystick(0);
        //operatingPad = new Joystick(1);


        chassis.setDefaultCommand(new DriveByJoystick(chassis, m_drivingPad));

        POVButton shiftUp = new POVButton(m_drivingPad, 0);
        POVButton shiftDown = new POVButton(m_drivingPad, 180);

        JoystickButton hatchRelease = new JoystickButton(m_drivingPad, 5);
        JoystickButton hatchGrab = new JoystickButton(m_drivingPad, 6);


        shiftUp.onTrue(new Shift(shifters, Shifters.Speed.HIGH));
        shiftDown.onTrue(new Shift(shifters, Shifters.Speed.LOW));

        hatchRelease.onTrue(new HatchCollect(hatch, HatchCollector.HatchState.RELEASE));
        hatchGrab.onTrue(new HatchCollect(hatch, HatchCollector.HatchState.GRAB));
    }
}
