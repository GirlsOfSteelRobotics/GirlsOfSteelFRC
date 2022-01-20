package com.gos.codelabs.basic_simulator.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.codelabs.basic_simulator.Constants;
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
    private final RelativeEncoder m_liftEncoder;
    private final DigitalInput m_lowerLimitSwitch;
    private final DigitalInput m_upperLimitSwitch;

    private ISimWrapper m_elevatorSim;

    public ElevatorSubsystem() {
        m_liftMotor = new SimableCANSparkMax(Constants.CAN_LIFT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);
        m_liftEncoder = m_liftMotor.getEncoder();

        m_lowerLimitSwitch = new DigitalInput(Constants.DIO_LIFT_LOWER_LIMIT);
        m_upperLimitSwitch = new DigitalInput(Constants.DIO_LIFT_UPPER_LIMIT);

        if (RobotBase.isSimulation()) {
            m_elevatorSim = new ElevatorSimWrapper(Constants.ElevatorSimConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_liftMotor),
                    RevEncoderSimWrapper.create(m_liftMotor));
        }
    }

    @Override
    public void periodic() {
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

    @Override
    public void simulationPeriodic() {
        m_elevatorSim.update();
    }
}
