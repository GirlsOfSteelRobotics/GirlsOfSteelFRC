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
import frc.robot.commands.RunShooterRPM;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.DriveDistance;
import frc.robot.commands.autonomous.DriveDistanceSmartMotion;
import frc.robot.commands.autonomous.GoToPosition;
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
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 2)); //Shoot pre-loaded cells
        addCommands(new DriveDistance(chassis, 12, 1).alongWith(new RunShooterRPM(shooter, Constants.DEFAULT_RPM))); 
        addCommands(new DriveDistanceSmartMotion(chassis, 10 * 12, 12));
        //GoToPosition(chassis, 207, -31, 12)
        addCommands(new TurnToAngle(chassis, 0, 12));
        addCommands(new AutomatedConveyorIntake(shooterIntake, shooterConveyor)
            .raceWith(new GoToPosition(chassis, 328, -29, 12)));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 8));
        addCommands(new GoToPosition(chassis, 122, -98, 12));
        addCommands(new TurnToAngle(chassis, 0, 12));
    }
}
