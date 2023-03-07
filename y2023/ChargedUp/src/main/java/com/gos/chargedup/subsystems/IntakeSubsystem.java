package com.gos.chargedup.subsystems;

import com.gos.chargedup.Constants;
import com.gos.lib.checklists.DoubleSolenoidMovesChecklist;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.gos.lib.rev.SparkMaxAlerts;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class IntakeSubsystem extends SubsystemBase {
    public static boolean intakeNoWork;
    public static boolean intakeReverse;

    private static final double INTAKE_SPEED = 0.5;
    private final DoubleSolenoid m_intakeSolenoid;
    private final SimableCANSparkMax m_intakeMotor;

    private final SparkMaxAlerts m_intakeMotorErrorAlert;



    public IntakeSubsystem() {
        intakeReverse = false;
        intakeNoWork = false;

        m_intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_PISTON_FORWARD, Constants.INTAKE_PISTON_REVERSE);
        m_intakeMotor = new SimableCANSparkMax(Constants.INTAKE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_intakeMotor.restoreFactoryDefaults();
        m_intakeMotor.setInverted(true);

        m_intakeMotor.setSmartCurrentLimit(30);
        m_intakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_intakeMotor.burnFlash();

        m_intakeMotorErrorAlert = new SparkMaxAlerts(m_intakeMotor, "intake motor ");
    }

    @Override
    public void periodic() {
        m_intakeMotorErrorAlert.checkAlerts();
    }

    public void extendAndRoll() {
        extend();
        intakeRollersIn();
    }

    public void retractAndStopRoll() {
        retract();
        m_intakeMotor.set(0);
    }

    public void extend() {
        m_intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public boolean isIntakeDown() {
        return m_intakeSolenoid.get().equals(DoubleSolenoid.Value.kReverse);
    }

    public void intakeRollersIn() {
        if(!intakeNoWork && !intakeReverse) {
            m_intakeSolenoid.set(DoubleSolenoid.Value.kForward);
            m_intakeMotor.set(INTAKE_SPEED);
        } else if(intakeReverse) {
            m_intakeSolenoid.set(DoubleSolenoid.Value.kForward);
            m_intakeMotor.set(0);
        }
    }

    public void intakeRollersOut() {

        m_intakeMotor.set(-INTAKE_SPEED);
    }

    public void retract() {
        if (!intakeNoWork && !intakeReverse) {
            m_intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
            m_intakeMotor.set(0);
        } else if (intakeReverse) {
            m_intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
            m_intakeMotor.set(INTAKE_SPEED);
        }
    }
    public void intakeRollersStop() {
        m_intakeMotor.set(0);
    }

    public double getIntakeRollerSpeed() {
        return m_intakeMotor.getAppliedOutput();
    }

    /////////////////////
    // Command Factories
    /////////////////////
    public CommandBase createIntakeOutAndRollCommand() {
        return this.runOnce(this::extendAndRoll);
    }

    public CommandBase createIntakeInAndStopRollCommand() {
        return this.runOnce(this::retractAndStopRoll);
    }

    public CommandBase createIntakeOut() {
        return this.runEnd(this::intakeRollersOut, this::intakeRollersStop).withName("intake out");
    }

    public CommandBase createIntakeIn() {
        return this.runEnd(this::intakeRollersIn, this::intakeRollersStop).withName("intake in");
    }

    public CommandBase createIntakeExtend() {
        return this.run(this::extend).withName("intake extend");
    }

    public CommandBase createIntakeRetract() {
        return this.run(this::retract).withName("intakeRetract");
    }

    ///////////////
    // Checklists
    ///////////////
    public CommandBase createIsIntakeMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_intakeMotor, "Intake: Intake motor", 1.0);
    }

    public CommandBase createIsIntakePneumaticMoving(DoubleSupplier pressureSupplier) {
        return new DoubleSolenoidMovesChecklist(this, pressureSupplier, m_intakeSolenoid, "Intake: Piston");
    }
}
