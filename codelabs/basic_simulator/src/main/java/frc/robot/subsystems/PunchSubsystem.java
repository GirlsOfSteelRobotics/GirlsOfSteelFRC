package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SmartDashboardNames;

public class PunchSubsystem extends SubsystemBase {

    private final Solenoid m_punchSolenoid;

    private final NetworkTableEntry m_isPunchExtendedEntry;

    public PunchSubsystem() {
        m_punchSolenoid = new Solenoid(Constants.SOLENOID_PUNCH);

        NetworkTable table = NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME + "/" + SmartDashboardNames.PUNCH_TABLE_NAME);
        m_isPunchExtendedEntry = table.getEntry(SmartDashboardNames.PUNCH_IS_EXTENDED);
    }

    @Override
    public void periodic() {
        m_isPunchExtendedEntry.setBoolean(isExtended());
    }

    public boolean isExtended() {
        // TODO implement
        return false;
    }

    public void extend() {
        // TODO implement
    }

    public void retract() {
        // TODO implement
    }
}
