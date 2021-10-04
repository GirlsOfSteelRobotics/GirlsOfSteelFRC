package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimedDriveStraight extends CommandBase {

    private final Chassis m_chassis;
    private final Timer m_timer;
    private final double m_waitTime;
    private final double m_speed;

    public TimedDriveStraight(Chassis chassis, int waitTime, double speed) {
        m_chassis = chassis;
        m_waitTime = waitTime;
        m_speed = speed;
        m_timer = new Timer();

        addRequirements(chassis);
    }

    @Override
    public void initialize() {
        m_timer.start();
    }

    @Override
    public void execute() {
        m_chassis.setSpeed(m_speed);
    }

    @Override
    public boolean isFinished() {
        return m_timer.get() > m_waitTime;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }
}
