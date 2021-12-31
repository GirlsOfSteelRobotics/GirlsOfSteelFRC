/**
 * A command to tune the chassis position PID.
 * <p>
 * <p>
 * DIFFERENT P'S ON THE PRACTICE BOT AND COMPETITION BOT
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Driving;

public class ManualPositionPIDTuner extends CommandBase {

    private final Chassis m_chassis;
    private double m_setpoint;
    private double m_p;
    private double m_i;
    private double m_d;
    private static final double m_offBy = 0.05;
    private boolean m_reset;
    private static final boolean m_pid = false;

    public ManualPositionPIDTuner(Chassis chassis, Driving driving) {
        m_chassis = chassis;
        requires(driving);

    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.resetEncoders();
        if (m_pid) {
            m_chassis.initPositionPIDS();
            m_chassis.resetPositionPIDError();
            SmartDashboard.putNumber("Chassis Position setpoint", 0);
            SmartDashboard.putNumber("Position P: ", 0);
            SmartDashboard.putNumber("Position I: ", 0);
            SmartDashboard.putNumber("Position D: ", 0);
        }
        SmartDashboard.putBoolean("Resetencoder", false);
    }

    @Override
    protected void execute() {
        if (m_pid) {
            m_setpoint = SmartDashboard.getNumber("Chassis Position setpoint", 0);
            m_p = SmartDashboard.getNumber("Position P: ", 0);
            m_i = SmartDashboard.getNumber("Position I: ", 0);
            m_d = SmartDashboard.getNumber("Position D: ", 0);
        }

        m_reset = SmartDashboard.getBoolean("Resetencoder", false);

        if (m_reset) {
            m_chassis.resetEncoders();
        }

        SmartDashboard.putNumber("leftencoder chassis", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Left encoder rate", m_chassis.getLeftEncoderRate());
        SmartDashboard.putNumber("Left Encoder Get Chassis", m_chassis.getLeftEncoder());
        SmartDashboard.putNumber("Left Encoder Get Raw", m_chassis.getLeftRaw());
        SmartDashboard.putNumber("Right Encoder Get Raw", m_chassis.getRightRaw());
        SmartDashboard.putNumber("Right Encoder Get Chassis", m_chassis.getRightEncoder());
        SmartDashboard.putNumber("rightencoder chassis", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("right encoder rate", m_chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Right encoder rate chassis", m_chassis.getRateRightEncoder());
        SmartDashboard.putNumber("Left encoder rate chassis", m_chassis.getRateLeftEncoder());

        System.out.println(m_chassis.getRightEncoderRate() + "\t" + m_chassis.getLeftEncoderRate());

        System.out.println("Get left chassis raw: " + m_chassis.getLeftRaw());
        System.out.println("Get right chassis raw: " + m_chassis.getRightRaw());

        if (m_pid && m_p != 0 && m_setpoint != 0) {
            System.out.println("Here ---------------------------------");
            m_chassis.setLeftPositionPIDValues(m_p, m_i, m_d);
            m_chassis.setRightPositionPIDValues(m_p, m_i, m_d);
            m_chassis.setPositionSeparate(m_setpoint, m_setpoint);

        }
    }

    @Override
    protected boolean isFinished() {
        boolean finished = (Math.abs((m_chassis.getLeftEncoderDistance() - m_setpoint)) < m_offBy || Math.abs((m_chassis.getRightEncoderDistance() - m_setpoint)) < m_offBy) && (m_setpoint != 0);
        System.out.println("Position PID is finished: " + finished);
        return finished;
    }

    @Override
    protected void end() {
        if (m_pid) {
            m_chassis.disablePositionPID();
        }
    }

    @Override
    protected void interrupted() {
        end();
    }
}
