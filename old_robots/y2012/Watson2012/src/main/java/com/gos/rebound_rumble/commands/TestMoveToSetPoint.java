package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TestMoveToSetPoint extends GosCommandBase {

    private final Chassis m_chassis;
    private double m_distanceToMove;

    public TestMoveToSetPoint(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        SmartDashboard.putNumber("Move,distance", 0.0);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    public void execute() {
        m_chassis.setPIDsPosition();
        m_distanceToMove = SmartDashboard.getNumber("Move,distance", 0.0);
        m_chassis.move(m_distanceToMove);
        SmartDashboard.putNumber("Right Encoder Position", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder Position", m_chassis.getLeftEncoderDistance());
    }

    @Override
    public boolean isFinished() {
        return m_chassis.isMoveFinished(m_distanceToMove);
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
    }


}
