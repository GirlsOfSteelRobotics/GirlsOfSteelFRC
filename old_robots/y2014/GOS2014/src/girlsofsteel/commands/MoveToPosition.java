/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Driving;

/**
 * THIS IS IN METERS
 * <p>
 * Requires driving because it should override arcade drive
 *
 * @author Sylvie
 */
public class MoveToPosition extends CommandBase {

    private static final double m_offBy = 0.03;

    private final Chassis m_chassis;
    private double m_distance;

    public MoveToPosition(Chassis chassis, Driving driving) {
        this(chassis, driving, 0.0);
    }

    public MoveToPosition(Chassis chassis, Driving driving, double distance) {
        m_chassis = chassis;
        requires(driving);
        this.m_distance = distance;
    }

    @Override
    protected void initialize() {
        m_chassis.initPositionPIDS();
        m_chassis.resetPositionPIDError();
        m_chassis.initEncoders();
        //SmartDashboard.putNumber("Distance", 0);
    }

    @Override
    protected void execute() {
        //distance = SmartDashboard.getNumber("Distance", 0);
        m_chassis.setPosition(m_distance);
        SmartDashboard.putNumber("Left Encoder: ", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Right Encoder: ", m_chassis.getRightEncoderDistance());
    }

    @Override
    protected boolean isFinished() {
        //Is finished when our position is within the "off by" range of the setpoint

        return (Math.abs((m_chassis.getLeftEncoderDistance() - m_distance)) < m_offBy);
    }

    @Override
    protected void end() {
        m_chassis.disablePositionPID();
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
