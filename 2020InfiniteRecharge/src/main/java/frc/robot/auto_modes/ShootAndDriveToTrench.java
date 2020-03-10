/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.auto_modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.SetStartingPosition;
import frc.robot.commands.autonomous.TurnToAngle;
import frc.robot.subsystems.*;
import frc.robot.trajectory_modes.TrajectoryModeFactory;

public class ShootAndDriveToTrench extends SequentialCommandGroup {

    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public ShootAndDriveToTrench(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake, TrajectoryModeFactory trajectoryFactory,
                                 boolean useSensor, Limelight limelight) {

        //cell intake runs until handoff break sensor is true (a ball has been collected
        addCommands(new SetStartingPosition(chassis, 122, -98, 0));
        addCommands(new MovePiston(shooterIntake, true));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 2)); //Shoot pre-loaded cells
        addCommands(trajectoryFactory.getTrajectoryAutoLineToFrontOfTrench(chassis));
        if (useSensor) {
            SequentialCommandGroup intake = new SequentialCommandGroup();
            intake.addCommands(new AutomatedConveyorIntake(shooterIntake, shooterConveyor),
                    new AutomatedConveyorIntake(shooterIntake, shooterConveyor),
                    new AutomatedConveyorIntake(shooterIntake, shooterConveyor));
            addCommands(intake.raceWith(trajectoryFactory.getTrajectoryFrontOfTrenchToControlPanel(chassis)));
        }
        else {
            addCommands(new ConveyorWhileHeld(shooterConveyor, true)
                    .raceWith(new IntakeCells(shooterIntake, true)
                    .raceWith(trajectoryFactory.getTrajectoryFrontOfTrenchToControlPanel(chassis))));
        }
        addCommands(new ConveyorWhileHeld(shooterConveyor, false).withTimeout(.15)
                .raceWith(new IntakeCells(shooterIntake, true)).withTimeout(.5));
        addCommands(trajectoryFactory.getTrajectoryControlPanelToAutoLine(chassis));
        addCommands(new TurnToAngle(chassis, 0, 12));
        addCommands(new AlignLeftRight(chassis, limelight)
                .withTimeout(1));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 3));
    }
}
