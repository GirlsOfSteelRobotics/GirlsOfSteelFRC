package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.reefscape.Constants;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
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
    private final RelativeEncoder m_relativeEncoder;
    private final AbsoluteEncoder m_absoluteEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_checkAlerts;
    private SingleJointedArmSimWrapper m_pivotSimulator;

    public PivotSubsystem() {
        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR_ID, MotorType.kBrushless);
        m_relativeEncoder = m_pivotMotor.getEncoder();
        m_absoluteEncoder = m_pivotMotor.getAbsoluteEncoder();

        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig.idleMode(IdleMode.kBrake);
        pivotConfig.smartCurrentLimit(60);
        pivotConfig.inverted(false);
        m_pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries = new LoggingUtil("Pivot");
        m_networkTableEntries.addDouble("Speed", this::getSpeed);
        m_networkTableEntries.addDouble("Relative Angle", this::getRelativeAngle);
        m_networkTableEntries.addDouble("Absolute Angle", this::getAbsoluteAngle);


        m_checkAlerts = new SparkMaxAlerts(m_pivotMotor, "Pivot Alert");

        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(1);
            SingleJointedArmSim armSim = new SingleJointedArmSim(gearbox, 252, 1,
                0.381, Units.degreesToRadians(-2), Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor, gearbox),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }
    }

    @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
    }


    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_checkAlerts.checkAlerts();
    }

    public double getSpeed() {
        return m_pivotMotor.getAppliedOutput();
    }

    public void stop() {
        m_pivotMotor.set(0);
    }

    public void setSpeed(double speed) {
        m_pivotMotor.set(speed);
    }

    public double getRelativeAngle() {
        return m_relativeEncoder.getPosition();
    }

    public double getAbsoluteAngle() {
        return m_absoluteEncoder.getPosition();
    }
}

