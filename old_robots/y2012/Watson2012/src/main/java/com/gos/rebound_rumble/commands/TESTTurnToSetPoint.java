package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TESTTurnToSetPoint extends CommandBase {

    private final Chassis m_chassis;
    private double m_degreesToTurn;

    public TESTTurnToSetPoint(Chassis chassis) {
        m_chassis = chassis;
        requires(m_chassis);
        SmartDashboard.putNumber("Turn,degrees", 0.0);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        m_degreesToTurn = SmartDashboard.getNumber("Turn,degrees", 0.0);
        m_chassis.turn(m_degreesToTurn);
    }

    @Override
    protected boolean isFinished() {
        return m_chassis.isTurnFinished(m_degreesToTurn);
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
