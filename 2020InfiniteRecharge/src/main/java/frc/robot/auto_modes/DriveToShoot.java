/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.auto_modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.RunShooterRPM;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.DriveDistance;
import frc.robot.subsystems.*;

public class DriveToShoot extends SequentialCommandGroup {

    private final Chassis m_chassis;
    private final Shooter m_shooter;
    private final ShooterConveyor m_shooterConveyor;

    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public DriveToShoot(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor) {

        m_chassis = chassis;
        m_shooter = shooter;
        m_shooterConveyor = shooterConveyor;

        //cell intake runs until handoff break sensor is true (a ball has been collected)
        addCommands(new DriveDistance(chassis, 12, 1).alongWith(new RunShooterRPM(shooter, Constants.DEFAULT_RPM))); 
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 10));
    }
}
