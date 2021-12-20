package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Turret;

public class TurretTrackTarget extends CommandBase {

    private final Turret m_turret;
    private final Joystick m_operatorJoystick;

    private double m_difference; //How much the driver wants it to move

    public TurretTrackTarget(Turret turret, Joystick operatorJoystick) {
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
        m_turret.changeTurretOffset();
        if (Camera.foundTarget()) {
            m_turret.autoTrack();
            System.out.println("Turret Angle:  " + m_turret.getTurretAngle());
        }else{
            m_difference = m_operatorJoystick.getX()*5.0;
            if(m_difference < -2.0 || m_difference > 2.0){
                m_turret.setPIDSetPoint(m_turret.getTurretAngle() + m_difference);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_turret.disablePID();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
