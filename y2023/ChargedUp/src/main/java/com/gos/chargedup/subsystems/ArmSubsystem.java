package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

    private static final double ARM_MOTOR_SPEED = 0.2;

    private final SimableCANSparkMax m_pivotMotor;

    private final Solenoid m_outerPiston;

    private final Solenoid m_innerPiston;

    public ArmSubsystem() {
        m_pivotMotor = new SimableCANSparkMax(Constants.PIVOT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_outerPiston = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.FIRST_STAGE_PISTON);
        m_innerPiston = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.SECOND_STAGE_PISTON);
    }

    public void pivotArmUp() {
        m_pivotMotor.set(ARM_MOTOR_SPEED);
    }

    public void pivotArmDown() {
        m_pivotMotor.set(-ARM_MOTOR_SPEED);
    }

    public void pivotArmStop() {
        m_pivotMotor.set(0);
    }

    public void fullRetract() {
        m_outerPiston.set(true);
        m_innerPiston.set(false);
    }

    public void middleRetract() {
        m_outerPiston.set(false);
        m_innerPiston.set(false);
    }

    public void out() {
        m_outerPiston.set(false);
        m_innerPiston.set(true);
    }

    public Command commandPivotArmUp() {
        return this.startEnd(this::pivotArmUp, this::pivotArmStop);
    }

    public Command commandPivotArmDown() {
        return this.startEnd(this::pivotArmDown, this::pivotArmStop);
    }
}

