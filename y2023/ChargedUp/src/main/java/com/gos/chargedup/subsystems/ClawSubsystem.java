package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {

    private static final double CLAW_SPEED = 0.2;

    private final SimableCANSparkMax m_rightIntakeLead;
    private final SimableCANSparkMax m_leftIntake; //NOPMD


    public ClawSubsystem() {
        m_rightIntakeLead = new SimableCANSparkMax(Constants.CLAW_INTAKE_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftIntake = new SimableCANSparkMax(Constants.CLAW_INTAKE_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftIntake.follow(m_rightIntakeLead, true);

    }

    //intake in

    public void moveClawIntakeIn() {
        m_rightIntakeLead.set(CLAW_SPEED);
    }

    //intake out

    public void moveClawIntakeOut() {
        m_rightIntakeLead.set(-CLAW_SPEED);
    }

    //set motors to 0

    public void stopClawIntake() {
        m_rightIntakeLead.set(0);
    }

    public Command createMoveClawIntakeInCommand() {
        return this.startEnd(this::moveClawIntakeIn, this::stopClawIntake);
    }

    public Command createMoveClawIntakeOutCommand() {
        return this.startEnd(this::moveClawIntakeOut, this::stopClawIntake);
    }
}

