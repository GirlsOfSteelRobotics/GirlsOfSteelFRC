package com.gos.chargedup.subsystems;

import com.gos.chargedup.Constants;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.gos.lib.checklists.SolenoidMovesChecklist;
import com.gos.lib.rev.SparkMaxAlerts;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class IntakeSubsystem extends SubsystemBase {

    private static final double HOPPER_SPEED = 0.5;
    private static final double INTAKE_SPEED = 1;
    private final Solenoid m_intakeSolenoidLeft;
    private final Solenoid m_intakeSolenoidRight;
    private final SimableCANSparkMax m_hopperMotor;
    private final SimableCANSparkMax m_intakeMotor;

    private final SparkMaxAlerts m_intakeMotorErrorAlert;
    private final SparkMaxAlerts m_hopperMotorErrorAlert;



    public IntakeSubsystem() {
        m_intakeSolenoidRight = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_LEFT_PISTON);
        m_intakeSolenoidLeft = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_RIGHT_PISTON);
        m_hopperMotor = new SimableCANSparkMax(Constants.HOPPER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_intakeMotor = new SimableCANSparkMax(Constants.INTAKE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_hopperMotor.restoreFactoryDefaults();
        m_intakeMotor.restoreFactoryDefaults();

        m_hopperMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_intakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_hopperMotor.burnFlash();
        m_intakeMotor.burnFlash();

        m_intakeMotorErrorAlert = new SparkMaxAlerts(m_intakeMotor, "intake motor ");
        m_hopperMotorErrorAlert = new SparkMaxAlerts(m_hopperMotor, "hopper motor ");
    }

    @Override
    public void periodic() {
        m_intakeMotorErrorAlert.checkAlerts();
        m_hopperMotorErrorAlert.checkAlerts();
    }

    public boolean intakeMotorError() {
        return m_intakeMotor.getFaults() != 0;

    }

    public boolean hopperMotorError() {
        return m_hopperMotor.getFaults() != 0;
    }

    public void extend() {
        m_intakeSolenoidRight.set(true);
        m_intakeSolenoidLeft.set(true);
        m_intakeMotor.set(INTAKE_SPEED);
    }

    public boolean isIntakeDown() {
        return m_intakeSolenoidLeft.get();
    }

    public void retract() {
        m_intakeSolenoidRight.set(false);
        m_intakeSolenoidLeft.set(false);
        m_intakeMotor.set(0);
    }

    public double getHopperSpeed() {
        return m_hopperMotor.get();
    }

    //    in out stop
    public void hopperIn() {
        m_hopperMotor.set(HOPPER_SPEED);
    }

    public void hopperOut() {
        m_hopperMotor.set(-HOPPER_SPEED);
    }

    public void hopperStop() {
        m_hopperMotor.set(0);
    }

    public boolean getIntakeOut() {
        return m_intakeSolenoidLeft.get() && m_intakeSolenoidRight.get();
    }

    /////////////////////
    // Command Factories
    /////////////////////
    public CommandBase createIntakeOutCommand() {
        return this.runOnce(this::extend);
    }

    public CommandBase createIntakeInCommand() {
        return this.runOnce(this::retract);
    }

    public CommandBase createHopperInMotorCommand() {
        return this.runEnd(this::hopperIn, this::hopperStop);
    }

    public CommandBase createHopperOutMotorCommand() {
        return this.runEnd(this::hopperOut, this::hopperStop);
    }

    ///////////////
    // Checklists
    ///////////////
    public CommandBase createIsHopperMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_hopperMotor, "Intake: Hopper motor", 1.0);
    }

    public CommandBase createIsIntakeMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_intakeMotor, "Intake: Intake motor", 1.0);
    }

    public CommandBase createIsIntakeLeftPneumaticMoving(DoubleSupplier pressureSupplier) {
        return new SolenoidMovesChecklist(this, pressureSupplier, m_intakeSolenoidLeft, "Intake: Left Piston");
    }

    public CommandBase createIsIntakeRightPneumaticMoving(DoubleSupplier pressureSupplier) {
        return new SolenoidMovesChecklist(this, pressureSupplier, m_intakeSolenoidRight, "Intake: Right Piston");
    }

}
