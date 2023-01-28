package com.gos.chargedup.subsystems;

import com.gos.chargedup.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private static final double HOPPER_SPEED = 0.5;
    private final Solenoid m_intakeSolenoidLeft;
    private final Solenoid m_intakeSolenoidRight;
    private final SimableCANSparkMax m_hopper;


    public IntakeSubsystem() {
        m_intakeSolenoidRight = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_LEFT_PISTON);
        m_intakeSolenoidLeft = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_RIGHT_PISTON);
        m_hopper = new SimableCANSparkMax(Constants.HOPPER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void extend() {
        m_intakeSolenoidRight.set(true);
        m_intakeSolenoidLeft.set(true);

    }

    public void retract() {
        m_intakeSolenoidRight.set(false);
        m_intakeSolenoidLeft.set(false);
    }

    //    in out stop
    public void hopperIn() {
        m_hopper.set(HOPPER_SPEED);
    }

    public void hopperOut() {
        m_hopper.set(-HOPPER_SPEED);
    }

    public void hopperStop() {
        m_hopper.set(0);
    }


    public Command createExtendSolenoidCommand() {
        return this.runOnce(this::extend);
    }

    public Command createRetractSolenoidCommand() {
        return this.runOnce(this::retract);
    }

    public Command createHopperInMotorCommand() {
        return this.startEnd(this::hopperIn, this::hopperStop);
    }

    public Command createHopperOutMotorCommand() {
        return this.startEnd(this::hopperOut, this::hopperStop);
    }

}
