/*

 Uses the LSPB PID planner to position pid
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.objects.LspbPidPlanner;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;

/**
 * @author Sylvie
 */
public class ChassisLspbplanner extends GosCommandBase {

    private final Chassis m_chassis;
    private final LspbPidPlanner m_leftChassisPlanner;
    private final LspbPidPlanner m_rightChassisPlanner;
    private double m_startTime; //milliseconds
    private double m_changeInTime;
    private double m_setPoint;
    private boolean m_graphed;

    public ChassisLspbplanner(Chassis chassis, Driving driving) {
        m_chassis = chassis;
        m_leftChassisPlanner = m_chassis.getLeftChassisPlanner();
        m_rightChassisPlanner = m_chassis.getRightChassisPlanner();
        addRequirements(driving);
    }

    @Override
    public void initialize() {
        m_graphed = false;
        m_startTime = System.currentTimeMillis();
        m_chassis.initEncoders();
        m_chassis.initPositionPIDS();
        m_chassis.resetPositionPIDError();
        SmartDashboard.putNumber("LSPB Setpoint", 0.0);
    }

    @Override
    public void execute() {
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
    public boolean isFinished() {
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
    public void end(boolean interrupted) {
        m_chassis.stopJags();
        m_chassis.disablePositionPID();
    }



}
