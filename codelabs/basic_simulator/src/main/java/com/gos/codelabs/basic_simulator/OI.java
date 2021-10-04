package com.gos.codelabs.basic_simulator;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import edu.wpi.first.wpilibj.XboxController;

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
