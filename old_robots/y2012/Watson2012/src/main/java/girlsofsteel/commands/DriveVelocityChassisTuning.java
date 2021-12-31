package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Chassis;

public class DriveVelocityChassisTuning extends CommandBase {
    private final Chassis m_chassis;

    public DriveVelocityChassisTuning(Chassis chassis) {
        m_chassis = chassis;
        SmartDashboard.putNumber("DVCT,p", 0.0);
        SmartDashboard.putNumber("DVCT,i", 0.0);
        // SmartDashboard.putNumber("setpoint", 0.0);
        SmartDashboard.putNumber("encoder rate right", 0.0);
        SmartDashboard.putNumber("encoder rate left", 0.0);
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    @Override
    protected void execute() {
        m_chassis.setRatePIDValues(SmartDashboard.getNumber("DVCT,p", 0.0), SmartDashboard.getNumber("DVCT,i", 0.0), 0.0);
        m_chassis.setRatePIDSetPoint(/*joystick.getY());*/SmartDashboard.getNumber("setpoint", 0.0));
        SmartDashboard.putNumber("encoder rate right", m_chassis.getRightEncoderRate());
        SmartDashboard.putNumber("encoder rate left", m_chassis.getLeftEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.disableRatePIDs();
        m_chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
