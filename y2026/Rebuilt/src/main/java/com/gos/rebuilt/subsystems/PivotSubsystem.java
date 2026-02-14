package com.gos.rebuilt.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.rebuilt.Constants;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

public class PivotSubsystem extends SubsystemBase {

    private final SparkFlex m_pivotMotor;
    private static final double GEAR_RATIO = 45.0;
    private final RelativeEncoder m_encoder;
    private final SparkMaxAlerts m_pivotMotorAlerts;
    private final LoggingUtil m_networkTableEntries;


    private SingleJointedArmSimWrapper m_pivotSimulator;


    public PivotSubsystem() {


        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR, MotorType.kBrushless);
        m_encoder = m_pivotMotor.getEncoder();

        m_pivotMotorAlerts = new SparkMaxAlerts(m_pivotMotor, "pivotMotor");


        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig.idleMode(IdleMode.kBrake);
        pivotConfig.smartCurrentLimit(60);
        pivotConfig.inverted(false);

        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeoVortex(1);
            SingleJointedArmSim armSim = new SingleJointedArmSim(gearbox, GEAR_RATIO, .01,
                0.381, Units.degreesToRadians(-220), Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor, gearbox),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }

        m_pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries = new LoggingUtil("Pivot Subsystem");

        m_networkTableEntries.addDouble("Pivot Position", this::getPosition);
        m_networkTableEntries.addDouble("Pivot Velocity", this::getVelocity);
        m_networkTableEntries.addDouble("Applied Output", m_pivotMotor::getAppliedOutput);


    }

    public double getPosition() {
        return m_encoder.getPosition();
    }

    public double getVelocity() {
        return m_encoder.getVelocity();
    }

    @Override
    public void periodic() {
        m_pivotMotorAlerts.checkAlerts();
    }

    public void setSpeed(double pow) {
        m_pivotMotor.set(pow);
    }

    public void stop() {
        m_pivotMotor.stopMotor();
    }

    public double getAngle() {
        return m_encoder.getPosition();
    }

    @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
    }



}

