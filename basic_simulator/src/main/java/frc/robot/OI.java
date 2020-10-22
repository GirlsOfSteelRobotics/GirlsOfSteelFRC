package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveChassisWithJoystickCommand;
import frc.robot.commands.ElevatorToPositionCommand;
import frc.robot.commands.ElevatorWithJoystickCommand;
import frc.robot.commands.MovePunchCommand;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.PunchSubsystem;

public class OI {

    public static final double ELEVATOR_JOYSTICK_DEADBAND = .01;

    private final XboxController m_driverJoystick;
    private final XboxController m_operatorJoystick;

    @SuppressWarnings("PMD.UnusedFormalParameter")
    public OI(RobotContainer robotContainer) {
        m_driverJoystick = new XboxController(0);
        m_operatorJoystick = new XboxController(1);

        // Alias for readability
        ChassisSubsystem chassis = robotContainer.getChassis();
        ElevatorSubsystem lift = robotContainer.getElevator();
        PunchSubsystem punch = robotContainer.getPunch();

        // TODO implement
    }

}
