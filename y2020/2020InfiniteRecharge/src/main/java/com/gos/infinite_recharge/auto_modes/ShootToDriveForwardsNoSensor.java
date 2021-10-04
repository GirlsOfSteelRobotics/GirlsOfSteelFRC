/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package com.gos.infinite_recharge.auto_modes;

import com.gos.infinite_recharge.commands.MovePiston;
import com.gos.infinite_recharge.commands.StopShooter;
import com.gos.infinite_recharge.subsystems.Chassis;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.infinite_recharge.commands.autonomous.AutoShoot;
import com.gos.infinite_recharge.commands.autonomous.DriveDistanceSmartMotion;
import com.gos.infinite_recharge.commands.autonomous.SingleShoot;
import frc.robot.subsystems.*;
import com.gos.infinite_recharge.subsystems.ShooterIntake;

public class ShootToDriveForwardsNoSensor extends SequentialCommandGroup {

    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public ShootToDriveForwardsNoSensor(Chassis chassis,
                                        Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake,
                                        boolean useSensor, double rpm) {

        if (useSensor) {
            addCommands(new SingleShoot(shooter, shooterConveyor, rpm));
            addCommands(new SingleShoot(shooter, shooterConveyor, rpm));
            addCommands(new SingleShoot(shooter, shooterConveyor, rpm));
        }
        else {
            addCommands(new AutoShoot(shooter, shooterConveyor, rpm, 5));
        }

        addCommands(new DriveDistanceSmartMotion(chassis, -3 * 12, 1).alongWith(new StopShooter(shooter)));
        addCommands(new MovePiston(shooterIntake, true));
    }
}

