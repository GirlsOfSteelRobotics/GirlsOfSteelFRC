package com.gos.reefscape.subsystems.sysid;

import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.units.measure.MutAngle;
import edu.wpi.first.units.measure.MutAngularVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

public class PivotSysId {
    private final PivotSubsystem m_pivot;
    private final SysIdRoutine m_routine;

    private final MutVoltage m_appliedVoltage = Volts.mutable(0);
    private final MutAngle m_angle = Degrees.mutable(0);
    private final MutAngularVelocity m_velocity = DegreesPerSecond.mutable(0);

    public PivotSysId(PivotSubsystem pivot) {
        m_pivot = pivot;
        m_routine = new SysIdRoutine(
            new SysIdRoutine.Config(
                Volts.of(0.25).per(Second),
                Volts.of(2.5),
                Seconds.of(10)),
            new SysIdRoutine.Mechanism(this::setVoltage, this::logMotors, pivot)
        );
    }

    private void setVoltage(Voltage volts) {
        m_pivot.setVoltage(volts.in(Volts));
    }

    private void logMotors(SysIdRoutineLog log) {
        log.motor("pivot")
            .voltage(
                m_appliedVoltage.mut_replace(
                    m_pivot.getVoltage(), Volts))
            .angularPosition(m_angle.mut_replace(m_pivot.getRelativeAngle(), Degrees))
            .angularVelocity(
                m_velocity.mut_replace(m_pivot.getRelativeVelocity(), DegreesPerSecond));
    }

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_routine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return m_routine.dynamic(direction);
    }

    public Command createSysidRoutineCommand() {
        return Commands.sequence(
            Commands.print("Waiting to start DynamicForward"),
            Commands.waitSeconds(2),
            sysIdDynamic(SysIdRoutine.Direction.kForward).until(() -> m_pivot.getRelativeAngle() > 80),

            Commands.print("Waiting to start DynamicReverse"),
            Commands.waitSeconds(2),
            sysIdDynamic(SysIdRoutine.Direction.kReverse).until(() -> m_pivot.getRelativeAngle() < -30),

            Commands.print("Waiting to start QuasistaticForward"),
            Commands.waitSeconds(2),
            sysIdQuasistatic(SysIdRoutine.Direction.kForward).until(() -> m_pivot.getRelativeAngle() > 80),

            Commands.print("Waiting to start QuasistaticReverse"),
            Commands.waitSeconds(2),
            sysIdQuasistatic(SysIdRoutine.Direction.kReverse).until(() -> m_pivot.getRelativeAngle() < -30)

        ).withName("Pivot SysID Routine");
    }

}
