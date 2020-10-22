package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SmartDashboardNames;

public class ElevatorSubsystem extends SubsystemBase {
    public static final double ALLOWABLE_POSITION_ERROR = .25;
    private static final double KP = .2;

    private final CANSparkMax m_liftMotor;
    private final CANEncoder m_liftEncoder;
    private final DigitalInput m_lowerLimitSwitch;
    private final DigitalInput m_upperLimitSwitch;

    private final NetworkTableEntry m_motorSpeedEntry;
    private final NetworkTableEntry m_heightEntry;
    private final NetworkTableEntry m_lowerLimitEntry;
    private final NetworkTableEntry m_upperLimitEntry;

    public ElevatorSubsystem() {
        m_liftMotor = new CANSparkMax(Constants.CAN_LIFT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);
        m_liftEncoder = m_liftMotor.getEncoder();

        m_lowerLimitSwitch = new DigitalInput(Constants.DIO_LIFT_LOWER_LIMIT);
        m_upperLimitSwitch = new DigitalInput(Constants.DIO_LIFT_UPPER_LIMIT);

        NetworkTable table = NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME + "/" + SmartDashboardNames.LIFTING_TABLE_NAME);
        m_motorSpeedEntry = table.getEntry(SmartDashboardNames.LIFTING_MOTOR_SPEED);
        m_heightEntry = table.getEntry(SmartDashboardNames.LIFTING_HEIGHT);
        m_lowerLimitEntry = table.getEntry(SmartDashboardNames.LIFTING_LOWER_LIMIT_SWITCH);
        m_upperLimitEntry = table.getEntry(SmartDashboardNames.LIFTING_UPPER_LIMIT_SWITCH);
    }

    @Override
    public void periodic() {
        m_motorSpeedEntry.setNumber(m_liftMotor.get());
        m_heightEntry.setNumber(getHeight());
        m_lowerLimitEntry.setBoolean(isAtLowerLimit());
        m_upperLimitEntry.setBoolean(isAtUpperLimit());
    }

    public boolean goToPosition(double position) {
        // TODO implement
        return false;
    }

    public boolean isAtLowerLimit() {

        // TODO implement
        return false;
    }

    public boolean isAtUpperLimit() {
        // TODO implement
        return false;
    }

    public void setSpeed(double speed) {
        // TODO implement
    }

    public double getHeight() {
        // TODO implement
        return 0;
    }
}
