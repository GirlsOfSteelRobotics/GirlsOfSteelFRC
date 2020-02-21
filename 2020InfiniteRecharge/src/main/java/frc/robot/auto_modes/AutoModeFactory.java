/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                                                         */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.auto_modes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutomatedConveyorIntake;
import frc.robot.commands.TuneRPM;
import frc.robot.commands.autonomous.DriveDistance;
import frc.robot.commands.autonomous.DriveToPoint;
import frc.robot.commands.autonomous.SetStartingPosition;
import frc.robot.commands.autonomous.TimedDriveStraight;
import frc.robot.commands.autonomous.TurnToAngle;
import frc.robot.subsystems.*;



public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<Command> m_sendableChooser;
    private static final boolean TEST_MODE = true;



    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake) {
       
        m_sendableChooser = new SendableChooser<>();
        
        if (TEST_MODE) {
            double dX = 27 * 12;
            double dY = 13.5 * 12;
            double xOffset = 27 * 12;
            double yOffset = -13.5 * 12;
            m_sendableChooser.addOption("Test. Go To Position (0, 0)", createDrivePointCommand(chassis, 0.0, 0.0, 1));
            m_sendableChooser.addOption("Test. Go To Position Right", createDrivePointCommand(chassis, dX + xOffset, 0.0 + yOffset, 1));
            m_sendableChooser.addOption("Test. Go To Position Left", createDrivePointCommand(chassis, -dX + xOffset, 0.0 + yOffset, 1));
            m_sendableChooser.addOption("Test. Go To Position Up", createDrivePointCommand(chassis, 0.0 + xOffset, dY + yOffset, 1));
            m_sendableChooser.addOption("Test. Go To Position Down", createDrivePointCommand(chassis, 0.0 + xOffset, -dY + yOffset, 1));
            m_sendableChooser.addOption("Test. Go To Position Up-Left", createDrivePointCommand(chassis, -dX + xOffset, dY + yOffset, 1));
            m_sendableChooser.addOption("Test. Go To Position Up-Right", createDrivePointCommand(chassis, dX + xOffset, dY + yOffset, 1));
            m_sendableChooser.addOption("Test. Go To Position Down-Left", createDrivePointCommand(chassis, -dX + xOffset, -dY + yOffset, 1));
            m_sendableChooser.addOption("Test. Go To Position Down-Right", createDrivePointCommand(chassis, dX + xOffset, -dY + yOffset, 1));
            m_sendableChooser.addOption("Test. Turn To Angle Positive", new TurnToAngle(chassis, 90, 1));
            m_sendableChooser.addOption("Test. Turn To Angle Negative", new TurnToAngle(chassis, -90, 1));
            m_sendableChooser.addOption("Test. Drive Distance Forward", new DriveDistance(chassis, 5, 1));
            m_sendableChooser.addOption("Test. Drive Distance Backward", new DriveDistance(chassis, -60, 1));
            m_sendableChooser.addOption("Test. Timed Drive Straight Forward", new TimedDriveStraight(chassis, 2, 0.5));
            m_sendableChooser.addOption("Test. Timed Drive Straight Backward", new TimedDriveStraight(chassis, 2, -0.5));
            m_sendableChooser.addOption("Test. TuneRPM", new TuneRPM(shooter));
            m_sendableChooser.addOption("Test. Start Intake", new AutomatedConveyorIntake(shooterIntake, shooterConveyor));
        }
           
        m_sendableChooser.addOption("DriveToShoot", new DriveToShoot(chassis, shooter, shooterConveyor));
        m_sendableChooser.addOption("ShootAndDriveToTrench", new ShootAndDriveToTrench(chassis, shooter, shooterConveyor, shooterIntake));
        SmartDashboard.putData("Auto Mode", m_sendableChooser);
       
    }

    private Command createDrivePointCommand(Chassis chassis, double x, double y, double allowableError)
    {
        return new SetStartingPosition(chassis, 27 * 12, -13.5 * 12, 0).andThen(new DriveToPoint(chassis, x, y, allowableError));
    }

    public Command getAutonomousMode(){
        return m_sendableChooser.getSelected();
    }
}
