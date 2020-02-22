/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.auto_modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.AutomatedConveyorIntake;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.DriveToPoint;
import frc.robot.commands.autonomous.SetStartingPosition;
import frc.robot.commands.autonomous.TurnToAngle;
import frc.robot.subsystems.*;

public class ShootAndDriveToTrench extends SequentialCommandGroup {

    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public ShootAndDriveToTrench(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake) {

        //cell intake runs until handoff break sensor is true (a ball has been collected)
        addCommands(new SetStartingPosition(chassis, 122, -98, 0));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 10)); //Shoot pre-loaded cells
        // addCommands(new DriveDistance(chassis, 12, 1).alongWith(new RunShooterRPM(shooter, Constants.DEFAULT_RPM))); 
        addCommands(new DriveToPoint(chassis, 207, -31, 1));
        addCommands(new TurnToAngle(chassis, 0, 1));
        addCommands(new AutomatedConveyorIntake(shooterIntake, shooterConveyor).alongWith(new DriveToPoint(chassis, 328, -29, 1)));
        addCommands(new TurnToAngle(chassis, 0, 1));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 8));
    }
}
