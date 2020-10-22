package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.PunchSubsystem;

public class OI {

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
