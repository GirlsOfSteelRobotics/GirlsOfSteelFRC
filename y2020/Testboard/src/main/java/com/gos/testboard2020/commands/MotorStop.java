/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.testboard2020.commands;

import com.gos.testboard2020.subsystems.Motor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MotorStop extends CommandBase {

    private final Motor m_motor;

    public MotorStop(Motor motor) {
        m_motor = motor;

        // Use requires() here to declare subsystem dependencies
        super.addRequirements(m_motor);
    }


    @Override
    public void initialize() {
        System.out.println("init MotorStop");
    }


    @Override
    public void execute() {
        m_motor.stop();
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("end MotorStop");
    }
}
