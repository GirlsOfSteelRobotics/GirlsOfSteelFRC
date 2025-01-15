package com.gos.reefscape.subsystems;


import com.gos.reefscape.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;
import org.snobotv2.module_wrappers.BaseDigitalInputWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;

public class ElevatorSubsystem extends SubsystemBase {

    public static final double K_ELEVATOR_GEARING = 10.0;
    public static final double K_CARRIAGE_MASS = 4.0; // kg
    public static final double K_MIN_ELEVATOR_HEIGHT = -5.0;
    public static final double K_MAX_ELEVATOR_HEIGHT = Units.inchesToMeters(120);
    public static final DCMotor K_ELEVATOR_GEARBOX = DCMotor.getVex775Pro(4);
    public static final double K_ELEVATOR_DRUM_RADIUS = Units.inchesToMeters(2.0);

    private final SparkFlex m_elevatorMotor;
    private final RelativeEncoder m_encoder;
    private final DigitalInput m_botLimitSwitch;
    private final DigitalInput m_topLimitSwitch;

    private ElevatorSimWrapper m_simulator;

    public ElevatorSubsystem() {


        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.

        m_elevatorMotor = new SparkFlex(Constants.ELEVATOR_MOTOR_ID, MotorType.kBrushless);
        m_encoder = m_elevatorMotor.getEncoder();
        m_botLimitSwitch = new DigitalInput(Constants.BOTLIMITSWICTH_ID);
        m_topLimitSwitch = new DigitalInput(Constants.TOPLIMITSWITCH_ID);



        if (RobotBase.isSimulation()) {
            ElevatorSim sim = new ElevatorSim(
                K_ELEVATOR_GEARBOX,
                K_ELEVATOR_GEARING,
                K_CARRIAGE_MASS,
                K_ELEVATOR_DRUM_RADIUS,
                K_MIN_ELEVATOR_HEIGHT,
                K_MAX_ELEVATOR_HEIGHT, true, 0);

            m_simulator = new ElevatorSimWrapper(sim,
                new RevMotorControllerSimWrapper(m_elevatorMotor, K_ELEVATOR_GEARBOX),
                RevEncoderSimWrapper.create(m_elevatorMotor));
            m_simulator.setLowerLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_botLimitSwitch)::setValue));
            m_simulator.setUpperLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_topLimitSwitch)::setValue));
        }
    }

    public double getHeight() {
        return m_encoder.getPosition();
    }

    public void setSpeed(double speed) {
        m_elevatorMotor.set(speed);
    }

    public boolean isAtBottom() {
        return m_botLimitSwitch.get();
    }

    public boolean isAtTop() {
        return m_topLimitSwitch.get();
    }

    public void stop() {
        m_elevatorMotor.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Elevator Height", getHeight());
        SmartDashboard.putBoolean("Top Limit Switch", isAtTop());
        SmartDashboard.putBoolean("Bot Limit Switch", isAtBottom());

    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}

