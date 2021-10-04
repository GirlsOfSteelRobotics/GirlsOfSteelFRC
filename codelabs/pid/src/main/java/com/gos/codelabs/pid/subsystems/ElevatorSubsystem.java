package com.gos.codelabs.pid.subsystems;

import com.gos.codelabs.pid.Constants;
import com.gos.codelabs.pid.SmartDashboardNames;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.CANPIDController.ArbFFUnits;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.codelabs.pid.commands.tuning.FindElevatorGravityCompensationCommand;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class ElevatorSubsystem extends SubsystemBase {
    public static final double ALLOWABLE_POSITION_ERROR = .25;

    public enum Positions {
        LOW(Units.inchesToMeters(10)),
        MID(Units.inchesToMeters(35)),
        HIGH(Units.inchesToMeters(45));

        public final double m_heightMeters;

        Positions(double heightMeters) {
            m_heightMeters = heightMeters;
        }
    }


    private final SimableCANSparkMax m_liftMotor;
    private final CANEncoder m_liftEncoder;
    private final CANPIDController m_pidController;
    private final PidProperty m_pidProperty;

    private final NetworkTableEntry m_motorSpeedEntry;
    private final NetworkTableEntry m_desiredHeightEntry;
    private final NetworkTableEntry m_heightEntry;

    private ISimWrapper m_elevatorSim;

    public ElevatorSubsystem() {
        m_liftMotor = new SimableCANSparkMax(Constants.CAN_LIFT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);
        m_liftEncoder = m_liftMotor.getEncoder();
        m_pidController = m_liftMotor.getPIDController();

        NetworkTable table = NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME + "/" + SmartDashboardNames.ELEVATOR_TABLE_NAME);
        m_motorSpeedEntry = table.getEntry(SmartDashboardNames.ELEVATOR_MOTOR_SPEED);
        m_heightEntry = table.getEntry(SmartDashboardNames.ELEVATOR_HEIGHT);
        m_desiredHeightEntry = table.getEntry(SmartDashboardNames.ELEVATOR_DESIRED_HEIGHT);

        m_pidProperty = new RevPidPropertyBuilder("Elevator", false, m_pidController, 0)
                .addP(0)
                .addFF(0)
                .addMaxAcceleration(0)
                .addMaxVelocity(0)
                .build();

        if (RobotBase.isSimulation()) {
            m_elevatorSim = new ElevatorSimWrapper(Constants.ElevatorSimConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_liftMotor),
                    RevEncoderSimWrapper.create(m_liftMotor));
        }
    }

    @Override
    public void periodic() {
        m_pidProperty.updateIfChanged();

        m_motorSpeedEntry.setNumber(m_liftMotor.getAppliedOutput());
        m_heightEntry.setNumber(Units.metersToInches(getHeight()));
    }

    public boolean goToPosition(double position) {
        m_desiredHeightEntry.setNumber(Units.metersToInches(position));
        m_pidController.setReference(position, ControlType.kSmartMotion, 0, FindElevatorGravityCompensationCommand.ELEVATOR_SPEED.getValue(), ArbFFUnits.kPercentOut);
        return false;
    }

    public void setSpeed(double speed) {
        m_liftMotor.set(speed);
    }

    public double getHeight() {
        return m_liftEncoder.getPosition();
    }

    @Override
    public void simulationPeriodic() {
        m_elevatorSim.update();
    }
}
