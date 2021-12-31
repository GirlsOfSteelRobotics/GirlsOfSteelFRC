package girlsofsteel.commands;

import girlsofsteel.OI;
import girlsofsteel.subsystems.Shooter;

public class ManualShoot extends CommandBase {

    private final Shooter m_shooter;
    private final OI m_oi;
    private double m_sliderValue;
    private double m_shooterSpeed;

    public ManualShoot(Shooter shooter, OI oi) {
        m_oi = oi;
        m_shooter = shooter;
        requires(m_shooter);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    protected void execute() {
        m_sliderValue = m_oi.getShooterSliderValue();
        m_shooterSpeed = m_shooter.manualShooterSpeedConverter(m_sliderValue);
        m_shooter.setPIDSpeed(m_shooterSpeed);
        if (m_shooter.isWithinSetPoint(m_shooterSpeed) && !m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersForward();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        if (!m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersOff();
        }
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
