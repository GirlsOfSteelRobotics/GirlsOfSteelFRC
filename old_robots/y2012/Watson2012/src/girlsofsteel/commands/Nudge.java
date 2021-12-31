package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.subsystems.Chassis;

public class Nudge extends CommandBase {

    private final Chassis m_chassis;
    private final Joystick m_driverJoystick;

    private double m_xValue;

    public Nudge(Chassis chassis, Joystick driverJoystick) {
        m_chassis = chassis;
        this.m_driverJoystick = driverJoystick;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initHoldPosition();
    }

    @Override
    protected void execute() {
        m_xValue = m_driverJoystick.getX();
        if (-0.2 > m_xValue || m_xValue > 0.2) {
            m_chassis.nudge(m_xValue);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
