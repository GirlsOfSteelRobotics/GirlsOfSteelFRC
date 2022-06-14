/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.deep_space.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class BabyDrive extends SubsystemBase {

    private final WPI_TalonSRX m_babyDriveTalon;

    public BabyDrive() {
        m_babyDriveTalon = new WPI_TalonSRX(RobotMap.BABY_DRIVE_TALON);

        m_babyDriveTalon.setInverted(true);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void babyDriveSetSpeed(double speed) {
        m_babyDriveTalon.set(speed);
    }

    public void babyDriveStop() {
        m_babyDriveTalon.stopMotor();
    }
}
