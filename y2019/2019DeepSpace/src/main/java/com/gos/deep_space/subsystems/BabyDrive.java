/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.deep_space.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class BabyDrive extends Subsystem {

    private final WPI_TalonSRX babyDriveTalon;

    public BabyDrive() {
        babyDriveTalon = new WPI_TalonSRX(RobotMap.BABY_DRIVE_TALON);

        babyDriveTalon.setInverted(true);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void babyDriveSetSpeed(double speed) {
        babyDriveTalon.set(speed);
    }

    public void babyDriveStop() {
        babyDriveTalon.stopMotor();
    }
}
