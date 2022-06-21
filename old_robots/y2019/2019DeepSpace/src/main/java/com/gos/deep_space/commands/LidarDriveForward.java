/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.BabyDrive;
import com.gos.deep_space.subsystems.Chassis;
import com.gos.lib.sensors.LidarLite;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LidarDriveForward extends CommandBase {

    private static final double LIDAR_TOLERANCE = 1.0;
    private static final double DRIVE_SPEED = 0.4;
    private static final double BABYDRIVE_SPEED = 0.3;
    private final double m_goalLidar;
    private final Chassis m_chassis;
    private final BabyDrive m_babyDrive;
    private final LidarLite m_lidar;

    public LidarDriveForward(Chassis chassis, LidarLite lidar, double goalLidar) {
        this.m_chassis = chassis;
        this.m_lidar = lidar;
        this.m_babyDrive = null; // NOPMD
        this.m_goalLidar = goalLidar;

        addRequirements(m_chassis);
    }

    public LidarDriveForward(BabyDrive babyDrive, LidarLite lidar, double goalLidar) {
        this.m_chassis = null; // NOPMD
        this.m_lidar = lidar;
        this.m_babyDrive = babyDrive;
        this.m_goalLidar = goalLidar;

        addRequirements(m_babyDrive);
    }


    @Override
    public void initialize() {
        System.out.println("LidarDriveForward init, goal lidar: " + m_goalLidar + ", chassis bool: " + m_chassis);
    }


    @Override
    public void execute() {
        if (m_chassis != null) {
            m_chassis.setSpeed(DRIVE_SPEED);
        } else {
            m_babyDrive.babyDriveSetSpeed(BABYDRIVE_SPEED);
        }
        System.out.println("LidarDriveForward lidar distance: " + m_lidar.getDistance());
    }


    @Override
    public boolean isFinished() {
        double error = m_lidar.getDistance() - m_goalLidar;
        System.out.println("LidarDriveForward error: " + error);
        return error <= LIDAR_TOLERANCE;
    }


    @Override
    public void end(boolean interrupted) {
        if (m_chassis != null) {
            m_chassis.stop();
        } else {
            m_babyDrive.babyDriveStop();
        }
        System.out.println("LidarDriveForward end");
    }
}
