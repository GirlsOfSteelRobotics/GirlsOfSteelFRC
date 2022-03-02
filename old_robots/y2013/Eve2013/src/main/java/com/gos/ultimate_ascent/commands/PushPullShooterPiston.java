/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Sylvie
 * If used in a commandgroup, the entire commandgroup will become uninterruptible
 */
public class PushPullShooterPiston extends CommandBase {

    private final Feeder m_feeder;
    private final Shooter m_shooter;
    private double m_time;
    private boolean m_shot;

    public PushPullShooterPiston(Feeder feeder, Shooter shooter) {
        m_feeder = feeder;
        m_shooter = shooter;
        addRequirements(m_feeder);
        setInterruptible(false);
    }

    @Override
    public void initialize() {
        m_shot = false;
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Encoder Rate", m_shooter.getEncoderRate());
        if (m_shooter.isTimeToShoot()) {
            m_feeder.pushShooter();
            m_time = timeSinceInitialized();
            m_shot = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_shot && timeSinceInitialized() - m_time > 0.2;
    }

    @Override
    public void end(boolean interrupted) {
        m_feeder.pullShooter();
    }


}
