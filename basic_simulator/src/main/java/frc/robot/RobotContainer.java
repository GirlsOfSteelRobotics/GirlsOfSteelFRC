/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auton.AutonFactory;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.PunchSubsystem;
import frc.robot.subsystems.SpinningWheelSubsystem;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.SingularField")
public class RobotContainer {
    // Subsystems
    private final ChassisSubsystem m_chassisSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final PunchSubsystem m_punchSubsystem;
    private final SpinningWheelSubsystem m_spinningWheelSubsystem;

    private final AutonFactory m_autonFactory;

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        m_chassisSubsystem = new ChassisSubsystem();
        m_elevatorSubsystem = new ElevatorSubsystem();
        m_punchSubsystem = new PunchSubsystem();
        m_spinningWheelSubsystem = new SpinningWheelSubsystem();

        new OI(this);

        m_autonFactory = new AutonFactory(this);

        // Configure the button bindings
        configureButtonBindings();

        // Setup the superstructure table for use with the custom Shuffleboard Widget
        NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME).getEntry(".type").setString(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME);
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return m_autonFactory.getAutonMode();
    }

    public ElevatorSubsystem getElevator() {
        return m_elevatorSubsystem;
    }

    public ChassisSubsystem getChassis() {
        return m_chassisSubsystem;
    }

    public PunchSubsystem getPunch() {
        return m_punchSubsystem;
    }

    public SpinningWheelSubsystem getSpinningWheel() {
        return m_spinningWheelSubsystem;
    }
}
