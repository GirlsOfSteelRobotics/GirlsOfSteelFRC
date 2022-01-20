package com.gos.infinite_recharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.gos.infinite_recharge.Constants;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterIntake extends SubsystemBase {

    private static final double INTAKE_SPEED = 0.5;

    private final WPI_TalonSRX m_motor;
    private final DoubleSolenoid m_piston;

    private final NetworkTable m_customNetworkTable;

    public ShooterIntake() {
        m_motor = new WPI_TalonSRX(Constants.SHOOTER_INTAKE_TALON);
        m_motor.configFactoryDefault();
        m_motor.setInverted(true);
        m_piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.DOUBLE_SOLENOID_SHOOTER_INTAKE_FORWARD,
                Constants.DOUBLE_SOLENOID_SHOOTER_INTAKE_BACKWARD);

        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/ShooterIntake");
    }

    @Override
    public void periodic() {
        m_customNetworkTable.getEntry("Speed").setDouble(m_motor.getMotorOutputPercent());
        m_customNetworkTable.getEntry("Position").setBoolean(m_piston.get() == Value.kForward);
    }

    public void collectCells() {
        m_motor.set(ControlMode.PercentOutput, INTAKE_SPEED);
    }

    public void decollectCells() {
        m_motor.set(ControlMode.PercentOutput, -INTAKE_SPEED);
    }

    public void stop() {
        m_motor.set(ControlMode.PercentOutput, 0);
    }

    public void moveToCollect() {
        m_piston.set(Value.kForward);
    }

    public void retract() {
        m_piston.set(Value.kReverse);
    }
}
