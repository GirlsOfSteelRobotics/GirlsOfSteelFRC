/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Feeder;
import girlsofsteel.subsystems.Shooter;

/**
 *
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
        requires(m_feeder);
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        m_shot = false;
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Encoder Rate", m_shooter.getEncoderRate());
        if(m_shooter.isTimeToShoot()){
            m_feeder.pushShooter();
            m_time = timeSinceInitialized();
            m_shot = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return m_shot && timeSinceInitialized() - m_time > 0.2;
    }

    @Override
    protected void end() {
        m_feeder.pullShooter();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
