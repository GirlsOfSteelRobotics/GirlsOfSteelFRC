package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.subsystems.Chassis;

public class DriveSlowVelocity extends CommandBase {
    private final Chassis m_chassis;
    private final Joystick m_driverJoystick;

    private double m_xAxis;
    private double m_yAxis;

    public DriveSlowVelocity(Chassis chassis, Joystick driverJoystick) {
        m_chassis = chassis;
        this.m_driverJoystick = driverJoystick;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    @Override
    protected void execute() {
        m_chassis.setPIDsRate();
        m_xAxis = m_driverJoystick.getX();
        m_yAxis = m_driverJoystick.getY();
        m_chassis.driveSlowVelocity(m_xAxis, m_yAxis);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.disableRatePIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
