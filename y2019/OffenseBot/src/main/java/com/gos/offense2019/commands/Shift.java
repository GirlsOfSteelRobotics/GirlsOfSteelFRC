/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import com.gos.offense2019.subsystems.Shifters;
import com.gos.offense2019.subsystems.Shifters.Speed;
import edu.wpi.first.wpilibj.command.Command;

public class Shift extends Command {
    private final Speed m_speed;
    private final Shifters m_shifters;

    public Shift(Shifters shifters, Speed speed) {
        m_shifters = shifters;
        requires(m_shifters);
        this.m_speed = speed;
    }


    @Override
    protected void initialize() {
        System.out.println("Shift(" + m_speed + ") init");
        m_shifters.shiftGear(m_speed);
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
    }
}
