package com.gos.lib.checklists;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.DoubleSupplier;


public class SolenoidMovesChecklist extends Command {

    private final DoubleSupplier m_pressureSupplier;

    private final Solenoid m_solenoid;

    private double m_startPressure;

    private final Timer m_timer;

    private final Alert m_alert;

    public SolenoidMovesChecklist(Subsystem subsystem, DoubleSupplier pressureSupplier, Solenoid solenoid, String label) {
        addRequirements(subsystem);
        m_pressureSupplier = pressureSupplier;
        m_solenoid = solenoid;
        m_startPressure = 0;
        m_alert = new Alert(label, AlertType.kError);
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
