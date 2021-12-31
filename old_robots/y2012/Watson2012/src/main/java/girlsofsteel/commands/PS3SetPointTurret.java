package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.subsystems.Turret;

public class PS3SetPointTurret extends CommandBase {

    private final Turret m_turret;
    private final Joystick m_operatorJoystick;
    private double m_angle;

    public PS3SetPointTurret(Turret turret, Joystick operatorJoystick) {
        m_turret = turret;
        this.m_operatorJoystick = operatorJoystick;
        requires(m_turret);
    }

    @Override
    protected void initialize() {
        m_turret.initEncoder();
        m_turret.enablePID();
    }

    @Override
    protected void execute() {
        m_angle = m_operatorJoystick.getX() * 5.0;
        if (m_angle < -0.5 || m_angle > 0.5) {
            m_turret.setPIDSetPoint(m_turret.getEncoderDistance() + m_angle);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_turret.disablePID();
        m_turret.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
