package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Shooter;

public class TESTShootUsingTable extends CommandBase {
    private final Shooter m_shooter;
    private final OI m_oi;

    private double m_distance;

    public TESTShootUsingTable(OI oi, Shooter shooter) {
        m_oi = oi;
        m_shooter = shooter;
        requires(m_shooter);
//        SmartDashboard.putNumber("Bank Addition", 0.0);
        SmartDashboard.putNumber("Distance", 0.0);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    protected void execute() {
//        addition = SmartDashboard.getNumber("Bank Addition", 0.0);
//        shooter.TESTAutoShootBank(addition,cameraDistance);
        m_distance = SmartDashboard.getNumber("Distance", 0.0);
        m_shooter.autoShoot(m_distance);
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
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
