package com.gos.lib.rev.checklists;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.gos.lib.alerts.AlertFactory;
import org.wpilib.util.Alert;
import org.wpilib.system.Timer;
import org.wpilib.command2.Command;
import org.wpilib.command2.Subsystem;

public class SparkMaxMotorsMoveChecklist extends Command {

    private final RelativeEncoder m_encoder;

    private final SparkBase m_motor;

    private final Timer m_timer;

    private double m_startPosMotor;

    private final String m_label;

    private final double m_expectedDist;

    private final Alert m_alert;

    public SparkMaxMotorsMoveChecklist(Subsystem subsystem, SparkBase motor, String label, double expectedDist) {
        addRequirements(subsystem);
        m_encoder = motor.getEncoder();
        m_motor = motor;
        m_startPosMotor = 0;
        m_timer = new Timer();
        m_label = label;
        m_expectedDist = expectedDist;
        m_alert = AlertFactory.create(m_label, Alert.Level.HIGH);

    }

    @Override
    public void initialize() {
        m_motor.setThrottle(0);
        m_startPosMotor = m_encoder.getPosition().get();
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {
        m_motor.setThrottle(0.25);
    }

    @Override
    public boolean isFinished() {
        return (m_timer.get() >= 1);
    }

    @Override
    public void end(boolean interrupted) {
        m_motor.setThrottle(0);
        boolean isRobotAtPos = m_encoder.getPosition().get() - m_startPosMotor >= m_expectedDist;
        m_alert.set(!isRobotAtPos);
        m_timer.stop();
    }
}
