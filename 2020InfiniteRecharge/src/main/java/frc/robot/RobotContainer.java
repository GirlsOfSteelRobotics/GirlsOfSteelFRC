/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.DriveByJoystick;
import frc.robot.commands.autonomous.TimedDriveStraight;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;
import frc.robot.subsystems.ShooterIntake;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Chassis chassis;
  private final ControlPanel controlPanel;
  private final Limelight limelight;
  private final Shooter shooter;
  private final ShooterConveyor shooterConveyor;
  private final ShooterIntake shooterIntake;
  private final OI oi;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    //Add subsystems in this section:
    chassis = new Chassis();
    controlPanel = new ControlPanel();
    limelight = new Limelight();
    shooter = new Shooter();
    shooterConveyor = new ShooterConveyor();
    shooterIntake = new ShooterIntake();


    // This line has to be after all of the subsystems are created!
    oi = new OI(chassis, controlPanel, limelight, shooter, shooterIntake, shooterConveyor);

    chassis.setDefaultCommand(new DriveByJoystick(chassis, oi));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new TimedDriveStraight(chassis, 5, 10);
  }
}
