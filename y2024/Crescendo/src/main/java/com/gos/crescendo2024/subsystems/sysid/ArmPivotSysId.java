package com.gos.crescendo2024.subsystems.sysid;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.MutableMeasure;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import java.util.function.Consumer;

import static edu.wpi.first.units.MutableMeasure.mutable;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Volts;

public class ArmPivotSysId {
    private final SysIdRoutine m_routine;
    private final ArmPivotSubsystem m_armPivot;

    private final MutableMeasure<Voltage> m_appliedVoltage = mutable(Volts.of(0));
    private final MutableMeasure<Angle> m_angle = mutable(Degrees.of(0));
    private final MutableMeasure<Velocity<Angle>> m_velocity = mutable(DegreesPerSecond.of(0));

    public ArmPivotSysId(ArmPivotSubsystem armPivot) {
        m_armPivot = armPivot;
        m_routine = new SysIdRoutine(
            new SysIdRoutine.Config(
                Units.Volts.of(.25).per(Units.Seconds.of(1.0)),
                Units.Volts.of(2.5),
                Units.Seconds.of(10.0)),
            new SysIdRoutine.Mechanism(this.voltageMotors(), this.logMotors(), armPivot)
        );
    }

    private Consumer<Measure<Voltage>> voltageMotors() {
        return (Measure<Voltage> volts) -> {
            m_armPivot.setVoltageLeadAndFollow(volts.in(Volts));
        };
    }

    private Consumer<SysIdRoutineLog> logMotors() {
        return log -> {
            log.motor("armPivot")
                .voltage(
                    m_appliedVoltage.mut_replace(
                        m_armPivot.replaceVoltage(), Volts))
                .angularPosition(m_angle.mut_replace(m_armPivot.getEncoderPos(), Degrees))
                .angularVelocity(
                    m_velocity.mut_replace(m_armPivot.getEncoderVel(), DegreesPerSecond));
        };
    }

    ///////////////////////
    // Command Factories
    ///////////////////////
    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_routine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return m_routine.dynamic(direction);
    }
}
