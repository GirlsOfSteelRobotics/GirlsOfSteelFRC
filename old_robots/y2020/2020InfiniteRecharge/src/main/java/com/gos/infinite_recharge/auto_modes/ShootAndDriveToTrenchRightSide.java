/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package com.gos.infinite_recharge.auto_modes;

import com.gos.infinite_recharge.Constants;
import com.gos.infinite_recharge.commands.AlignLeftRight;
import com.gos.infinite_recharge.commands.ConveyorWhileHeld;
import com.gos.infinite_recharge.commands.IntakeCells;
import com.gos.infinite_recharge.subsystems.Chassis;
import com.gos.infinite_recharge.subsystems.Limelight;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;
import com.gos.infinite_recharge.subsystems.ShooterIntake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.infinite_recharge.commands.autonomous.AutoShoot;
import com.gos.infinite_recharge.commands.autonomous.SetStartingPosition;
import com.gos.infinite_recharge.commands.autonomous.TurnToAngle;
import com.gos.infinite_recharge.trajectory_modes.TrajectoryModeFactory;

public class ShootAndDriveToTrenchRightSide extends SequentialCommandGroup {

    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public ShootAndDriveToTrenchRightSide(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake, TrajectoryModeFactory trajectoryFactory, Limelight limelight) {

        double allowableErrorAngle;
        allowableErrorAngle = 10;

        //cell intake runs until handoff break sensor is true (a ball has been collected)
        addCommands(new SetStartingPosition(chassis, 122, -31, 0)); //start with shooter facing the wall
        addCommands(new TurnToAngle(chassis, 25, 10)); //turn to shoot pre-loaded cells
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 2)); //Shoot pre-loaded cells
        addCommands(new TurnToAngle(chassis, 0, allowableErrorAngle)); //turn back to starting orientation
        //addCommands(new IntakeCells(shooterIntake, true)
        //    .raceWith(new DriveDistanceSmartMotion(chassis, 17 * 12, allowableErrorDrive)
        //    .raceWith(new ConveyorWhileHeld(shooterConveyor, true))));
        // addCommands(trajectoryFactory.getTrajectoryRightSideToControlPanel(chassis).raceWith(new IntakeCells(shooterIntake, true).raceWith(new ConveyorWhileHeld(shooterConveyor, true)))); //collecting cells in trench

        // Drive and pick up
        addCommands(trajectoryFactory.getTrajectoryRightSideToControlPanel(chassis)
                .raceWith(new ConveyorWhileHeld(shooterConveyor, true)
                .raceWith(new IntakeCells(shooterIntake, true))));

        // Lower balls because the intake sucks
        addCommands(new ConveyorWhileHeld(shooterConveyor, false).withTimeout(.15)
                .raceWith(new IntakeCells(shooterIntake, true)).withTimeout(.5));

        addCommands(trajectoryFactory.getTrajectoryControlPanelToRightSide(chassis)); //go back to center autoline
        addCommands(new TurnToAngle(chassis, 25, 12));
        addCommands(new AlignLeftRight(chassis, limelight)
                .withTimeout(1));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 3));

    }
}
