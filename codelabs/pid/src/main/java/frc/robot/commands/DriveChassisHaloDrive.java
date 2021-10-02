package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.ChassisSubsystem;

public class DriveChassisHaloDrive extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final OI m_oi;

    public DriveChassisHaloDrive(ChassisSubsystem chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        m_chassis.setSpeedAndSteer(m_oi.getDriverThrottle(), m_oi.getDriverSpin());
    }
}
