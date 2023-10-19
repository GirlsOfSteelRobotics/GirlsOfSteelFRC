package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Shooter;

public class AutoShootMany extends GosCommandBase {

    public static final double WAIT_TIME = 1.0;

    private final int m_shots;
    private final double m_desiredVoltage;

    private final Shooter m_shooter;
    private final Feeder m_feeder;

    private int m_counter;
    private boolean m_pushed;
    private double m_time;

    public AutoShootMany(Shooter shooter, Feeder feeder, int numShots, double desiredVoltage) {
        m_shooter = shooter;
        m_feeder = feeder;
        addRequirements(m_shooter);
        addRequirements(m_feeder);
        m_shots = numShots;
        this.m_desiredVoltage = desiredVoltage;
    }

    @Override
    public void initialize() {
        m_counter = 0;
        m_pushed = false;
        m_shooter.setJags(m_desiredVoltage);
        m_time = timeSinceInitialized();
        while (timeSinceInitialized() - m_time < 4) { // NOPMD(EmptyWhileStmt)
            //overall wait time is 4 + WAIT_TIME = 5
        }
        m_time = timeSinceInitialized();
    }

    @Override
    public void execute() {
        if (timeSinceInitialized() - m_time > WAIT_TIME) {
            if (!m_pushed) {
                m_feeder.pushShooter();
                m_pushed = true;
            } else {
                m_feeder.pullShooter();
                m_pushed = false;
                //                counter++;
            }
            m_time = timeSinceInitialized();
        }
    }

    @Override
    public boolean isFinished() {
        return m_counter >= m_shots; //counter is never changed -> continue to shoot
        //just in case a frisbee got stuck
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopJags();
    }



}
