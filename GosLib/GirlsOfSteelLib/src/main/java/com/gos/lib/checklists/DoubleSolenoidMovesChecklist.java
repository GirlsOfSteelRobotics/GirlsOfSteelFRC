package com.gos.lib.checklists;

import com.gos.lib.alerts.AlertFactory;
import org.wpilib.util.Alert;
import org.wpilib.hardware.pneumatic.DoubleSolenoid;
import org.wpilib.system.Timer;
import org.wpilib.command2.Command;
import org.wpilib.command2.Subsystem;

import java.util.function.DoubleSupplier;


/**
 * Checklist for testing a double solenoid. Will extend and retract the solenoid for a certain amount of time and make sure the pressure in the tanks has dropped.
 */
public class DoubleSolenoidMovesChecklist extends Command {

    private final DoubleSupplier m_pressureSupplier;

    private final DoubleSolenoid m_solenoid;

    private double m_startPressure;

    private final Timer m_timer;

    private final Alert m_alert;

    public DoubleSolenoidMovesChecklist(Subsystem subsystem, DoubleSupplier pressureSupplier, DoubleSolenoid solenoid, String label) {
        addRequirements(subsystem);
        m_pressureSupplier = pressureSupplier;
        m_solenoid = solenoid;
        m_startPressure = 0;
        m_alert = AlertFactory.create(label, Alert.Level.HIGH);
        m_timer = new Timer();
    }

    @Override
    public void initialize() {
        m_startPressure = m_pressureSupplier.getAsDouble();
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {
        m_solenoid.toggle();
    }

    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_timer.stop();
        double endPressure = m_pressureSupplier.getAsDouble();
        boolean didSolenoidMove = (m_startPressure < endPressure);
        m_alert.set(!didSolenoidMove);
    }
}
