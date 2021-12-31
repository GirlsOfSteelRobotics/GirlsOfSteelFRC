/*

 Uses the LSPB PID planner to position pid
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.objects.LSPBPIDPlanner;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Driving;

/**
 * @author Sylvie
 */
public class ChassisLSPBPlanner extends CommandBase {

    private final Chassis m_chassis;
    private final LSPBPIDPlanner m_leftChassisPlanner;
    private final LSPBPIDPlanner m_rightChassisPlanner;
    private double m_startTime; //milliseconds
    private double m_changeInTime;
    private double m_setPoint;
    private boolean m_graphed;

    public ChassisLSPBPlanner(Chassis chassis, Driving driving) {
        m_chassis = chassis;
        m_leftChassisPlanner = m_chassis.getLeftChassisPlanner();
        m_rightChassisPlanner = m_chassis.getRightChassisPlanner();
        requires(driving);
    }

    @Override
    protected void initialize() {
        m_graphed = false;
        m_startTime = System.currentTimeMillis();
        m_chassis.initEncoders();
        m_chassis.initPositionPIDS();
        m_chassis.resetPositionPIDError();
        SmartDashboard.putNumber("LSPB Setpoint", 0.0);
    }

    @Override
    protected void execute() {
        m_setPoint = SmartDashboard.getNumber("LSPB Setpoint", 0);
        if (m_setPoint != 0) {
            if (!m_graphed) {
                m_startTime = System.currentTimeMillis();
                m_leftChassisPlanner.calculateVelocityGraph(m_setPoint);
                m_rightChassisPlanner.calculateVelocityGraph(m_setPoint);
                m_graphed = true;
            }

            SmartDashboard.putNumber("LSPB Left Encoder", m_chassis.getLeftEncoderDistance());
            SmartDashboard.putNumber("LSPB Right Encoder", m_chassis.getRightEncoderDistance());
            System.out.print("\tRight Encoder: " + m_chassis.getRightEncoderDistance());
            // if ((int)changeInTime % 10 == 0) {
            m_changeInTime = System.currentTimeMillis() - m_startTime;
            m_chassis.setPositionSeparate(m_leftChassisPlanner.getDesiredPosition(m_changeInTime), m_rightChassisPlanner.getDesiredPosition(m_changeInTime));
            // }
        }
    }

    @Override
    protected boolean isFinished() {
        boolean ret = (/*Math.abs(chassis.getLeftEncoderDistance() - setPoint) < 0.05) || */
            ((Math.abs(m_chassis.getRightEncoderDistance() - m_setPoint) < 0.01)
                || (Math.abs(m_chassis.getRightEncoderDistance()) > Math.abs(m_setPoint)))
                && m_graphed); //Right encoder/pid is flipped TODO configuration
        if (ret) {
            System.out.println("finished ");
        }

        return ret;
    }

    @Override
    protected void end() {
        m_chassis.stopJags();
        m_chassis.disablePositionPID();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
