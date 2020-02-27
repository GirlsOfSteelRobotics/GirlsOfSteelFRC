/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.auto_modes;

import java.util.List;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.AutomatedConveyorIntake;
import frc.robot.commands.TuneRPM;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.DriveAtVelocity;
import frc.robot.commands.autonomous.DriveDistance;
import frc.robot.commands.autonomous.DriveDistanceSmartMotion;
import frc.robot.commands.autonomous.FollowTrajectory;
import frc.robot.commands.autonomous.GoToPosition;
import frc.robot.commands.autonomous.SetStartingPosition;
import frc.robot.commands.autonomous.SingleShoot;
import frc.robot.commands.autonomous.TimedDriveStraight;
import frc.robot.commands.autonomous.TurnToAngle;
import frc.robot.commands.autonomous.FollowTrajectory.AutoConstants;
import frc.robot.commands.autonomous.FollowTrajectory.DriveConstants;
import frc.robot.subsystems.*;
import frc.robot.commands.autonomous.DriveAtVelocity;



public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<Command> m_sendableChooser;
    private static final boolean TEST_MODE = true;

    private Command m_defaultCommand;



    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake) {
       
        m_sendableChooser = new SendableChooser<>();
        
        if (TEST_MODE) {
            double dX = 8 * 12;
            double dY = 8 * 12;
            double xOffset = 27 * 12;
            double yOffset = -13.5 * 12;
            double allowableError = 5;
            m_sendableChooser.addOption("Test. Go To Position (0, 0)", createDrivePointCommand(chassis, 0.0, 0.0, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Right", createDrivePointCommand(chassis, dX + xOffset, 0.0 + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Left", createDrivePointCommand(chassis, -dX + xOffset, 0.0 + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Up", createDrivePointCommand(chassis, 0.0 + xOffset, dY + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Down", createDrivePointCommand(chassis, 0.0 + xOffset, -dY + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Up-Left", createDrivePointCommand(chassis, -dX + xOffset, dY + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Up-Right", createDrivePointCommand(chassis, dX + xOffset, dY + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Down-Left", createDrivePointCommand(chassis, -dX + xOffset, -dY + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Go To Position Down-Right", createDrivePointCommand(chassis, dX + xOffset, -dY + yOffset, allowableError));
            m_sendableChooser.addOption("Test. Turn To Angle Positive", new TurnToAngle(chassis, 90, 1));
            m_sendableChooser.addOption("Test. Turn To Angle Negative", new TurnToAngle(chassis, -90, 1));
            m_sendableChooser.addOption("Test. Drive Distance Forward", new DriveDistance(chassis, 5 * 12, 1));
            m_sendableChooser.addOption("Test. Drive Distance Backward", new DriveDistance(chassis, -5 * 12, 1));
            m_sendableChooser.addOption("Test. Drive Distance Smart Motion", new DriveDistanceSmartMotion(chassis, 8 * 12, 5));
            m_sendableChooser.addOption("Test. Timed Drive Straight Forward", new TimedDriveStraight(chassis, 2, 0.5));
            m_sendableChooser.addOption("Test. Timed Drive Straight Backward", new TimedDriveStraight(chassis, 2, -0.5));
            m_sendableChooser.addOption("Test. TuneRPM", new TuneRPM(shooter));
            m_sendableChooser.addOption("Test. Start Intake", new AutomatedConveyorIntake(shooterIntake, shooterConveyor));
            m_sendableChooser.addOption("Test. Start Shooter", new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 3));
            m_sendableChooser.addOption("Test. Set Starting Position", new SetStartingPosition(chassis, 0, 0, 0));
            m_sendableChooser.addOption("Test. Get Trajectory", createTrajectoryCommand(chassis));
            m_sendableChooser.addOption("Test.SingleShot", new SingleShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM));
            m_sendableChooser.addOption("Test. Drive At Veloctity", new DriveAtVelocity(chassis, 60));
        }
           
        m_sendableChooser.addOption("DriveToShoot", new DriveToShoot(chassis, shooter, shooterConveyor));
        m_sendableChooser.addOption("ShootAndDriveToTrench", new ShootAndDriveToTrench(chassis, shooter, shooterConveyor, shooterIntake));
        m_sendableChooser.addOption("ShootAndDriveToTrenchRightSide", new ShootAndDriveToTrenchRightSide(chassis, shooter, shooterConveyor, shooterIntake));
        m_sendableChooser.addOption("ShootToDriveNoSensor", new ShootToDriveNoSensor(chassis, shooter, shooterConveyor));

        //SmartDashboard.putData("Auto Mode", m_sendableChooser);

        m_defaultCommand = new DriveToShoot(chassis, shooter, shooterConveyor);
       
    }

    private Command createDrivePointCommand(Chassis chassis, double x, double y, double allowableError) {
        return new SetStartingPosition(chassis, 27 * 12, -13.5 * 12, 0).andThen(new GoToPosition(chassis, x, y, allowableError));
    }

    private Command createTrajectoryCommand(Chassis chassis) {
        var autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                        DriveConstants.kvVoltSecondsPerMeter,
                                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics,
                10);

        TrajectoryConfig config =
            new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                            AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DriveConstants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(),
            //new Translation2d(2, -1)
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(Units.inchesToMeters(5 * 12), 0, new Rotation2d(0)),
            // Pass config
            config
        );

        return new SetStartingPosition(chassis, 0, 0, 0).andThen(new FollowTrajectory(exampleTrajectory, chassis));
    }

    public Command getAutonomousMode() {
        // return m_sendableChooser.getSelected();
        return m_defaultCommand;
    }
}
