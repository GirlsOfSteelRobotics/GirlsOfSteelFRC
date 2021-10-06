/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.testboard2020.commands;

import com.gos.lib.sensors.LidarLite;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReadLidar extends CommandBase {

    private final LidarLite m_lidar;

    public ReadLidar(LidarLite lidar) {
        m_lidar = lidar;
    }


    @Override
    public void initialize() {
        System.out.println("Init ReadLidar");
    }


    @Override
    public void execute() {
        System.out.println("ReadLidar distance: " + m_lidar.getDistance());
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("End  ReadLidar");
    }
}
