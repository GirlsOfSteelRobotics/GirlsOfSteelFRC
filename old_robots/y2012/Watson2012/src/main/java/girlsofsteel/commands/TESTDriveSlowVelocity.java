package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Chassis;

public class TESTDriveSlowVelocity extends CommandBase {

    private final Chassis m_chassis;
    private final Joystick m_driverJoystick;

    private double m_xAxis;
    private double m_yAxis;

    private double m_p;
    private double m_i;

    public TESTDriveSlowVelocity(Chassis chassis, Joystick operatorJoystick) {
        m_chassis = chassis;
        this.m_driverJoystick = operatorJoystick;
        requires(m_chassis);
        SmartDashboard.putNumber("Chassis P", 0.0);
        SmartDashboard.putNumber("Chassis I", 0.0);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    @Override
    protected void execute() {
        m_p = SmartDashboard.getNumber("Chassis P", 0.0);
        m_i = SmartDashboard.getNumber("Chassis I", 0.0);
        m_chassis.setRatePIDValues(m_p, m_i, 0.0);
        m_xAxis = m_driverJoystick.getX();
        m_yAxis = m_driverJoystick.getY();
        m_chassis.driveVelocityLinear(m_xAxis, m_yAxis);
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
