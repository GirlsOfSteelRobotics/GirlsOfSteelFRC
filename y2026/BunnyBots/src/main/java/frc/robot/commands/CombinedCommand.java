package frc.robot.commands;

import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.OuttakeSubsystem;

public class CombinedCommand {

    private final ChassisSubsystem m_chassisSubsystem;
    private final OuttakeSubsystem m_outtakeSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;

    public CombinedCommand(ChassisSubsystem chassis, OuttakeSubsystem outtake, ElevatorSubsystem elevator) {
        m_chassisSubsystem = chassis;
        m_outtakeSubsystem = outtake;
        m_elevatorSubsystem = elevator;
    }

}
