package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class DrivePositionChassisTuning extends CommandBase {
    private final Chassis m_chassis;

    public DrivePositionChassisTuning(Chassis chassis) {
        m_chassis = chassis;
        requires(m_chassis);
        SmartDashboard.putNumber("Right P", 0.0);
        SmartDashboard.putNumber("Right D", 0.0);
        SmartDashboard.putNumber("Left P", 0.0);
        SmartDashboard.putNumber("Left D", 0.0);
        SmartDashboard.putNumber("DPST,setpoint", 0.0);
        SmartDashboard.putNumber("Right Encoder Position", 0.0);
        SmartDashboard.putNumber("Left Encoder Position", 0.0);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        m_chassis.setPositionPIDValues(SmartDashboard.getNumber("Right P", 0.0),
            SmartDashboard.getNumber("Right D", 0.0),
            SmartDashboard.getNumber("Left P", 0.0),
            SmartDashboard.getNumber("Left D", 0.0));
        m_chassis.setPositionPIDSetPoint(SmartDashboard.getNumber("DPST,setpoint", 0.0));
        SmartDashboard.putNumber("Right Encoder Position", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder Position", m_chassis.getLeftEncoderDistance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
