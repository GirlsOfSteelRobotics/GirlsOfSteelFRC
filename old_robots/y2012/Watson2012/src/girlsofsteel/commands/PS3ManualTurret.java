package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.subsystems.Turret;

public class PS3ManualTurret extends CommandBase {

    private final Turret m_turret;
    private final Joystick m_operatorJoystick;

    private double m_speed;

    public PS3ManualTurret(Turret turret, Joystick operatorJoystick) {
        m_turret = turret;
        m_operatorJoystick = operatorJoystick;
        requires(m_turret);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_speed = m_operatorJoystick.getX() * .5;
        m_turret.setJagSpeed(m_speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
