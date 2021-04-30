package frc.robot.lib;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public final class CtrePidPropertyBuilder extends PidProperty.Builder implements IPidPropertyBuilder {
    private final BaseMotorController m_motor;
    private final int m_slot;

    public CtrePidPropertyBuilder(String baseName, boolean isConstant, BaseMotorController motor, int slot) {
        super(baseName, isConstant);
        m_motor = motor;
        m_slot = slot;
    }

    @Override
    public IPidPropertyBuilder addP(double defaultValue) {
        addP(defaultValue, (double gain) -> m_motor.config_kP(m_slot, gain));
        return this;
    }

    @Override
    public IPidPropertyBuilder addI(double defaultValue) {
        addI(defaultValue, (double gain) -> m_motor.config_kI(m_slot, gain));
        return this;
    }

    @Override
    public IPidPropertyBuilder addD(double defaultValue) {
        addD(defaultValue, (double gain) -> m_motor.config_kD(m_slot, gain));
        return this;
    }

    @Override
    public IPidPropertyBuilder addFF(double defaultValue) {
        addFF(defaultValue, (double gain) -> m_motor.config_kF(m_slot, gain));
        return this;
    }

    @Override
    public IPidPropertyBuilder addMaxVelocity(double defaultValue) {
        addMaxVelocity(defaultValue, m_motor::configMotionCruiseVelocity);
        return this;
    }

    @Override
    public IPidPropertyBuilder addMaxAcceleration(double defaultValue) {
        addMaxAcceleration(defaultValue, m_motor::configMotionAcceleration);
        return this;
    }
}
