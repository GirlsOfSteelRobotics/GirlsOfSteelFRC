package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {

    private final SimableCANSparkMax m_clawMotor;
    private static final double CLAW_SPEED = .2; //Todo  Get actual claw speed
    private final SparkMaxAlerts m_clawMotorErrorAlerts;

    public ClawSubsystem() {
        m_clawMotor = new SimableCANSparkMax(Constants.CLAW_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_clawMotor.restoreFactoryDefaults();
        m_clawMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_clawMotor.setSmartCurrentLimit(40);
        m_clawMotor.burnFlash();
        m_clawMotorErrorAlerts = new SparkMaxAlerts(m_clawMotor, "claw motor");
    }

    //intake close
    public void moveClawIntakeIn() {
        m_clawMotor.set(CLAW_SPEED); //Todo  test
    }

    //intake open
    public void moveClawIntakeOut() {
        m_clawMotor.set(-CLAW_SPEED);
    }

    @Override
    public void periodic() {
        m_clawMotorErrorAlerts.checkAlerts();
    }

    /////////////////////
    // Command Factories
    /////////////////////
    public CommandBase createMoveClawIntakeInCommand() {
        return this.run(this::moveClawIntakeIn).withName("ClawIntakeIn");
    }

    public CommandBase createMoveClawIntakeOutCommand() {
        return this.run(this::moveClawIntakeOut).withName("ClawIntakeOut");
    }

    //////////////
    // Checklists
    //////////////
    public CommandBase createIsClawMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_clawMotor, "Claw: Motor", 1.0);
    }
}
