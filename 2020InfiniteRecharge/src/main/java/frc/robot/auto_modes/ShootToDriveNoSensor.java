/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.auto_modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.StopShooter;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.DriveDistanceSmartMotion;
import frc.robot.commands.autonomous.SingleShoot;
import frc.robot.subsystems.*;

public class ShootToDriveNoSensor extends SequentialCommandGroup {

    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public ShootToDriveNoSensor(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor) {

        //cell intake runs until handoff break sensor is true (a ball has been collected)
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 5));
        addCommands(new DriveDistanceSmartMotion(chassis, 3 * 12, 1).alongWith(new StopShooter(shooter))); 
    }
}
