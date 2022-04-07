package com.gos.rapidreact.subsystems;


import com.gos.lib.DeadbandHelper;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
import com.gos.rapidreact.ShooterLookupTable;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

@SuppressWarnings("PMD.TooManyFields")
public class ShooterSubsystem extends SubsystemBase {

    //variables for the two NEO Brushless Motors
    public static final double SHOOTER_ALLOWABLE_ERROR = 50.0;
    public static final double FENDER_RPM_LOW = 700;
    public static final double TARMAC_EDGE_RPM_LOW = 800;
    public static final double TARMAC_EDGE_RPM_HIGH = 1750;
    public static final double DEFAULT_SHOOTER_RPM = FENDER_RPM_LOW;
    public static final double MAX_SHOOTER_RPM = 3200;

    private static final double ROLLER_ALLOWABLE_ERROR = 50.0;
    private static final double ROLLER_SHOOTER_RPM_PROPORTION = 1.3;

    private final SimableCANSparkMax m_leader;
    private final RelativeEncoder m_shooterEncoder;
    private final PidProperty m_shooterPid;
    private final SparkMaxPIDController m_shooterPidController;
    private final ShooterLookupTable m_shooterTable;
    private double m_shooterGoalRpm = Double.MAX_VALUE;
    private double m_backspinGoalRpm = Double.MAX_VALUE;

    private final SimableCANSparkMax m_roller;
    private final RelativeEncoder m_rollerEncoder;
    private final PidProperty m_rollerPid;
    private final SparkMaxPIDController m_rollerPidController;

    private ISimWrapper m_shooterSimulator;
    private ISimWrapper m_backspinSimulator;

    private final DeadbandHelper m_counter;

    // Logging
    private final NetworkTableEntry m_shooterRpmEntry;
    private final NetworkTableEntry m_backspinRpmEntry;
    private final NetworkTableEntry m_shooterGoalRpmEntry;
    private final NetworkTableEntry m_backspinGoalRpmEntry;
    private final NetworkTableEntry m_shooterAtSpeedEntry;

    public ShooterSubsystem() {
        m_leader = new SimableCANSparkMax(Constants.SHOOTER_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leader.restoreFactoryDefaults();
        m_leader.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_roller = new SimableCANSparkMax(Constants.SHOOTER_ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_roller.restoreFactoryDefaults();
        m_roller.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_roller.setInverted(true);

        //true because the motors are facing each other and in order to do the same thing, they would have to spin in opposite directions
        m_shooterEncoder = m_leader.getEncoder();
        m_rollerEncoder = m_roller.getEncoder();

        m_shooterPidController = m_leader.getPIDController();
        m_shooterPid = new RevPidPropertyBuilder("Shooter PID", false, m_shooterPidController, 0)
            .addP(0.003)
            .addD(0.000055)
            .addFF(0.000176)
            .build();

        m_rollerPidController = m_roller.getPIDController();
        m_rollerPid = new RevPidPropertyBuilder("Roller PID", false, m_rollerPidController, 0)
            .addP(0)
            .addD(0)
            .addFF(0)
            .build();

        m_shooterTable = new ShooterLookupTable();

        m_leader.burnFlash();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Shooter");
        m_shooterRpmEntry = loggingTable.getEntry("Shooter RPM");
        m_backspinRpmEntry = loggingTable.getEntry("Backspin RPM");
        m_shooterGoalRpmEntry = loggingTable.getEntry("Shooter Goal");
        m_backspinGoalRpmEntry = loggingTable.getEntry("Backspin Goal");
        m_shooterAtSpeedEntry = loggingTable.getEntry("At Speed");

        if (RobotBase.isSimulation()) {
            FlywheelSim shooterFlywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1, 0.01);
            m_shooterSimulator = new FlywheelSimWrapper(shooterFlywheelSim,
                new RevMotorControllerSimWrapper(m_leader),
                RevEncoderSimWrapper.create(m_leader));

            FlywheelSim backspinFlywheelSim = new FlywheelSim(DCMotor.getNeo550(2), 1, 0.01);
            m_backspinSimulator = new FlywheelSimWrapper(backspinFlywheelSim,
                new RevMotorControllerSimWrapper(m_roller),
                RevEncoderSimWrapper.create(m_roller));
        }

        m_counter = new DeadbandHelper(10);
    }

    @Override
    public void periodic() {
        m_shooterPid.updateIfChanged();
        m_rollerPid.updateIfChanged();
        double shooterError = Math.abs(m_shooterGoalRpm - getEncoderVelocity());
        double rollerError = Math.abs(ROLLER_SHOOTER_RPM_PROPORTION * m_shooterGoalRpm - getRollerVelocity());
        m_counter.setIsGood(shooterError < SHOOTER_ALLOWABLE_ERROR && rollerError < ROLLER_ALLOWABLE_ERROR);


        m_shooterRpmEntry.setNumber(getEncoderVelocity());
        m_backspinRpmEntry.setNumber(getRollerVelocity());
        m_shooterGoalRpmEntry.setNumber(m_shooterGoalRpm);
        m_backspinGoalRpmEntry.setNumber(m_backspinGoalRpm);
        m_shooterAtSpeedEntry.setBoolean(m_counter.isFinished());
    }

    public void setShooterRpmPIDSpeed(double rpm) {
        m_shooterGoalRpm = rpm;
        m_backspinGoalRpm = rpm * ROLLER_SHOOTER_RPM_PROPORTION;

        m_shooterPidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
        m_rollerPidController.setReference(ROLLER_SHOOTER_RPM_PROPORTION * rpm, CANSparkMax.ControlType.kVelocity);

    }

    public boolean isShooterAtSpeed() {
        return m_counter.isFinished();
    }

    public double getRollerVelocity() {
        return m_rollerEncoder.getVelocity();
    }

    public void setShooterSpeed(double speed) {
        m_leader.set(speed);
        m_roller.set(speed * ROLLER_SHOOTER_RPM_PROPORTION);

        m_shooterGoalRpm = Double.MAX_VALUE;
        m_backspinGoalRpm = Double.MAX_VALUE;
    }

    public double getEncoderVelocity() {
        return m_shooterEncoder.getVelocity();
    }

    public double getShooterSpeed() {
        return m_leader.getAppliedOutput();
    }

    public void rpmForDistance(double distance) {
        setShooterRpmPIDSpeed(m_shooterTable.getVelocityTable(distance));
    }

    @Override
    public void simulationPeriodic() {
        m_shooterSimulator.update();
        m_backspinSimulator.update();
    }

}

