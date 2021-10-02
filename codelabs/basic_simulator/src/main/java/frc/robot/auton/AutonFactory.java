package frc.robot.auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.AutoDriveStraightDistanceCommand;
import frc.robot.commands.AutoDriveStraightTimedCommand;
import frc.robot.commands.ElevatorToPositionCommand;
import frc.robot.commands.MovePunchCommand;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.PunchSubsystem;

public class AutonFactory {

    private static final boolean TEST_MODE = true;

    private final SendableChooser<Command> m_sendableChooser;

    public AutonFactory(RobotContainer robotContainer) {
        m_sendableChooser = new SendableChooser<>();

        // Alias for readability
        ChassisSubsystem chassis = robotContainer.getChassis();
        ElevatorSubsystem lift = robotContainer.getElevator();
        PunchSubsystem punch = robotContainer.getPunch();

        // Add a test case for each of our individual (non-teleop) commands
        if (TEST_MODE) {
            // Driving
            m_sendableChooser.addOption("Test Timed Drive Forwards", new AutoDriveStraightTimedCommand(chassis, .5, 2));
            m_sendableChooser.addOption("Test Timed Drive Backwards", new AutoDriveStraightTimedCommand(chassis, -.5, 2));
            m_sendableChooser.addOption("Test Drive Distance Forwards", new AutoDriveStraightDistanceCommand(chassis, 60.0));
            m_sendableChooser.addOption("Test Drive Distance Backwards", new AutoDriveStraightDistanceCommand(chassis, -60.0));

            // Lift
            m_sendableChooser.addOption("Test Lift To Position Low", new ElevatorToPositionCommand(lift, 0.0));
            m_sendableChooser.addOption("Test Lift To Position Mid", new ElevatorToPositionCommand(lift, 20.0));
            m_sendableChooser.addOption("Test Lift To Position High", new ElevatorToPositionCommand(lift, 30.0));

            // Punch
            m_sendableChooser.addOption("Test Extend Punch", new MovePunchCommand(punch, true));
            m_sendableChooser.addOption("Test Retract Punch", new MovePunchCommand(punch, false));
        }

        // Add all of the "real", full autonomous modes
    }

    public Command getAutonMode() {
        return m_sendableChooser.getSelected();
    }
}
