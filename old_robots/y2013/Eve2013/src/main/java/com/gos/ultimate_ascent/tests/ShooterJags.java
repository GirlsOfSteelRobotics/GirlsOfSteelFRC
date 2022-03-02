package com.gos.ultimate_ascent.tests;

import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.commands.CommandBase;

public class ShooterJags extends CommandBase {

    private static final double WAIT_TIME = 1.0;

    private final Shooter m_shooter;
    private final Feeder m_feeder;

    private double m_speed;

    private boolean m_pushed;
    private double m_time;

    public ShooterJags(Feeder feeder, Shooter shooter) {
        m_feeder = feeder;
        m_shooter = shooter;
        SmartDashboard.putBoolean("Shooter Jags", false);
        SmartDashboard.putNumber("Jag Speed", 0.0);
        SmartDashboard.putBoolean("Click When Done Testing Shooter Jags", false);
    }

    @Override
    public void initialize() {
        m_speed = SmartDashboard.getNumber("Jag Speed", 0.0);
        m_pushed = false;
        m_shooter.setJags(m_speed);
        m_time = timeSinceInitialized();
        while (timeSinceInitialized() - m_time < 4) { // NOPMD(EmptyWhileStmt)
            // Wait for init
        } //overall wait time is 4 + WAIT_TIME = 5
        m_time = timeSinceInitialized();
    }

    @Override
    @SuppressWarnings("PMD.CollapsibleIfStatements")
    protected void execute() {
        if (SmartDashboard.getBoolean("Shooter Jags", false)) {
            //            shooter.setJags(speed);
            //            shooter.setShootTrue();
            //        }else{
            //            shooter.setJags(0.0);
            //        }
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
    }

    @Override
    public boolean isFinished() {
        return SmartDashboard.getBoolean("Click When Done Testing Shooter Jags",
            false);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.setJags(0.0);
    }



}
