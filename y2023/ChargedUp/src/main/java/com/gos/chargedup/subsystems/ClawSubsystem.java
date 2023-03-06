package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.GosIntProperty;
import com.gos.lib.properties.HeavyIntegerProperty;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
    private static final GosDoubleProperty CLAW_IN_SPEED = new GosDoubleProperty(false, "ClawSpeed", 0.5);
    private static final GosDoubleProperty CLAW_OUT_SPEED = new GosDoubleProperty(false, "ClawSpeed", 0.75);
    private static final GosIntProperty CLAW_CURRENT_LIMIT = new GosIntProperty(false, "ClawCurrentLimit", 10);

    private final SimableCANSparkMax m_clawMotor;
    private final RelativeEncoder m_clawEncoder;
    private final SparkMaxAlerts m_clawMotorErrorAlerts;
    private final HeavyIntegerProperty m_currentLimit;

    private final NetworkTableEntry m_currentEntry;
    private final NetworkTableEntry m_outputEntry;
    private final NetworkTableEntry m_velocityEntry;

    public ClawSubsystem() {
        m_clawMotor = new SimableCANSparkMax(Constants.CLAW_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_clawMotor.restoreFactoryDefaults();

        m_clawMotor.setInverted(true);
        m_clawEncoder = m_clawMotor.getEncoder();
        m_clawMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_clawMotor.setSmartCurrentLimit(10);
        m_clawMotor.burnFlash();
        m_clawMotorErrorAlerts = new SparkMaxAlerts(m_clawMotor, "claw motor");

        m_currentLimit = new HeavyIntegerProperty(m_clawMotor::setSmartCurrentLimit, CLAW_CURRENT_LIMIT);

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("ClawSubsystem");
        m_currentEntry = loggingTable.getEntry("CurrentAmps");
        m_outputEntry = loggingTable.getEntry("Output");
        m_velocityEntry = loggingTable.getEntry("Velocity (RPM)");
    }

    //intake close
    public void moveClawIntakeIn() {
        m_clawMotor.set(CLAW_IN_SPEED.getValue());
    }

    //intake open
    public void moveClawIntakeOut() {
        m_clawMotor.set(-CLAW_OUT_SPEED.getValue());
    }

    public void stopIntake() {
        m_clawMotor.set(0);
    }

    @Override
    public void periodic() {
        m_currentLimit.updateIfChanged();
        m_clawMotorErrorAlerts.checkAlerts();

        m_currentEntry.setNumber(m_clawMotor.getOutputCurrent());
        m_outputEntry.setNumber(m_clawMotor.getAppliedOutput());
        m_velocityEntry.setNumber(m_clawEncoder.getVelocity());
    }

    /////////////////////
    // Command Factories
    /////////////////////
    public CommandBase createMoveClawIntakeInCommand() {
        return this.runEnd(this::moveClawIntakeIn, this::stopIntake).withName("ClawIntakeIn");
    }

    public CommandBase createMoveClawIntakeOutCommand() {
        return this.runEnd(this::moveClawIntakeOut, this::stopIntake).withName("ClawIntakeOut");
    }

    //////////////
    // Checklists
    //////////////
    public CommandBase createIsClawMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_clawMotor, "Claw: Motor", 1.0);
    }
}
