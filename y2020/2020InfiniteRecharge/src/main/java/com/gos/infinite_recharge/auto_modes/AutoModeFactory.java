/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package com.gos.infinite_recharge.auto_modes;

import com.gos.infinite_recharge.Constants;
import com.gos.infinite_recharge.commands.AlignLeftRight;
import com.gos.infinite_recharge.commands.AutomatedConveyorIntake;
import com.gos.infinite_recharge.commands.TuneRPM;
import com.gos.infinite_recharge.subsystems.Chassis;
import com.gos.infinite_recharge.subsystems.Limelight;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;
import com.gos.infinite_recharge.subsystems.ShooterIntake;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.infinite_recharge.commands.autonomous.AutoShoot;
import com.gos.infinite_recharge.commands.autonomous.DriveAtVelocity;
import com.gos.infinite_recharge.commands.autonomous.DriveDistance;
import com.gos.infinite_recharge.commands.autonomous.DriveDistanceSmartMotion;
import com.gos.infinite_recharge.commands.autonomous.GoToPosition;
import com.gos.infinite_recharge.commands.autonomous.SetStartingPosition;
import com.gos.infinite_recharge.commands.autonomous.SingleShoot;
import com.gos.infinite_recharge.commands.autonomous.TimedDriveStraight;
import com.gos.infinite_recharge.commands.autonomous.TurnToAngle;
import com.gos.infinite_recharge.commands.autonomous.TurnToAngleProfiled;
import com.gos.infinite_recharge.trajectory_modes.TrajectoryModeFactory;


public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<Command> m_sendableChooser;
    private static final boolean TEST_MODE = true;

    private static final boolean ENABLE_AUTO_SELECTION = true;

    private final Command m_defaultCommand;



    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake, Limelight limelight) {
       
        m_sendableChooser = new SendableChooser<>();
        TrajectoryModeFactory trajectoryModeFactory = new TrajectoryModeFactory();
        
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
            m_sendableChooser.addOption("Test. Turn To Angle Positive", new SetStartingPosition(chassis, 0, 0, 0).andThen(new TurnToAngle(chassis, 3, 1)));
            m_sendableChooser.addOption("Test. Turn To Angle Negative", new SetStartingPosition(chassis, 0, 0, 0).andThen(new TurnToAngle(chassis, -3, 1)));
            m_sendableChooser.addOption("Test. Drive Distance Forward", new DriveDistance(chassis, 5 * 12, 1));
            m_sendableChooser.addOption("Test. Drive Distance Backward", new DriveDistance(chassis, -5 * 12, 1));
            m_sendableChooser.addOption("Test. Drive Distance Smart Motion", new DriveDistanceSmartMotion(chassis, 8 * 12, 5));
            m_sendableChooser.addOption("Test. Timed Drive Straight Forward", new TimedDriveStraight(chassis, 2, 0.5));
            m_sendableChooser.addOption("Test. Timed Drive Straight Backward", new TimedDriveStraight(chassis, 2, -0.5));
            m_sendableChooser.addOption("Test. TuneRPM", new TuneRPM(shooter));
            m_sendableChooser.addOption("Test. Start Intake", new AutomatedConveyorIntake(shooterIntake, shooterConveyor));
            m_sendableChooser.addOption("Test. Start Shooter", new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 3));
            m_sendableChooser.addOption("Test. Set Starting Position", new SetStartingPosition(chassis, 0, 0, 0));
            m_sendableChooser.addOption("Test.SingleShot", new SingleShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM));
            m_sendableChooser.addOption("Test. Drive At Veloctity", new DriveAtVelocity(chassis, 72));
            m_sendableChooser.addOption("Shoot and Drive to Trench", new ShootAndDriveToTrench(chassis, shooter, shooterConveyor, shooterIntake, trajectoryModeFactory, false, limelight));
            m_sendableChooser.addOption("Turn to Angle Profiled", new SetStartingPosition(chassis, 0, 0, 0).andThen(new TurnToAngleProfiled(90, chassis)));
            m_sendableChooser.addOption("Limelight", new AlignLeftRight(chassis, limelight));
        }
        m_sendableChooser.addOption("ShootAndDriveToRendezvous",
                new ShootAndDriveToRendezvous(chassis, shooter, shooterConveyor, shooterIntake, trajectoryModeFactory));
        m_sendableChooser.addOption("ShootToDriveToTargetNoSensorCenterOrRight", 
            new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, false, Constants.DEFAULT_RPM));
        m_sendableChooser.addOption("ShootToDriveToTargetWithSensorCenterOrRight", 
            new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, true, Constants.DEFAULT_RPM));
        m_sendableChooser.addOption("ShootToDriveToTargetNoSensorLeft", 
            new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, false, Constants.DEFAULT_RPM_LEFT));
        m_sendableChooser.addOption("ShootToDriveToTargetWithSensorLeft", 
            new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, true, Constants.DEFAULT_RPM_LEFT));

           
           
        m_sendableChooser.addOption("ShootAndDriveToTrench", new ShootAndDriveToTrench(chassis, shooter, shooterConveyor, shooterIntake, trajectoryModeFactory, false, limelight));
        m_sendableChooser.addOption("ShootAndDriveToTrenchRightSide", new ShootAndDriveToTrenchRightSide(chassis, shooter, shooterConveyor, shooterIntake, trajectoryModeFactory, limelight));
        m_sendableChooser.addOption("ShootAndDriveToOpponentsTrench",  new ShootAndDriveToOpponentsTrenchCommandGroup(chassis, shooter,
                shooterConveyor, shooterIntake, trajectoryModeFactory, false));

        if (ENABLE_AUTO_SELECTION) {
            SmartDashboard.putData("Auto Mode", m_sendableChooser);
        }
        

        m_defaultCommand = new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, true, Constants.DEFAULT_RPM);
        //m_defaultCommand = new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, false, Constants.DEFAULT_RPM);
        //m_defaultCommand = new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, true, Constants.DEFAULT_RPM);
        //m_defaultCommand = new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, false, Constants.DEFAULT_RPM_LEFT);


    }

    private Command createDrivePointCommand(Chassis chassis, double x, double y, double allowableError) {
        return new SetStartingPosition(chassis, 27 * 12, -13.5 * 12, 0).andThen(new GoToPosition(chassis, x, y, allowableError));
    }

    public Command getAutonomousMode() {
        if (ENABLE_AUTO_SELECTION) {
            return m_sendableChooser.getSelected();
        }
        else {
            return m_defaultCommand;
        }
    }
}
