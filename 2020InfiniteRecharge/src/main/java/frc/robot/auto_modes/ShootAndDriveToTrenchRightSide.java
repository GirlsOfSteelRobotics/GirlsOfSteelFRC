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
import frc.robot.commands.Conveyor;
import frc.robot.commands.IntakeCells;
import frc.robot.commands.RunShooterRPM;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.DriveDistance;
import frc.robot.commands.autonomous.DriveDistanceSmartMotion;
import frc.robot.commands.autonomous.GoToPosition;
import frc.robot.commands.autonomous.SetStartingPosition;
import frc.robot.commands.autonomous.TurnToAngle;
import frc.robot.subsystems.*;

public class ShootAndDriveToTrenchRightSide extends SequentialCommandGroup {

    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public ShootAndDriveToTrenchRightSide(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake) {

        double allowableErrorAngle;
        allowableErrorAngle = 1;
        double allowableErrorDrive;
        allowableErrorDrive = 12;

        //cell intake runs until handoff break sensor is true (a ball has been collected)
        addCommands(new SetStartingPosition(chassis, 122, -98, -30));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 2)); //Shoot pre-loaded cells
        addCommands(new TurnToAngle(chassis, 0, allowableErrorAngle));
        addCommands(new IntakeCells(shooterIntake, true)
            .raceWith(new DriveDistanceSmartMotion(chassis, 17 * 12, allowableErrorDrive)
            .raceWith(new Conveyor(shooterConveyor, true))));
        addCommands(new TurnToAngle(chassis, 0, allowableErrorAngle));
        addCommands(new DriveDistanceSmartMotion(chassis, -17 * 12, allowableErrorDrive));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 8));
        }
}

