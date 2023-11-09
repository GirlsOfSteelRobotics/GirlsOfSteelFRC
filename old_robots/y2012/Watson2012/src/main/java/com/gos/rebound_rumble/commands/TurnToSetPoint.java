package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;

public class TurnToSetPoint extends GosCommand {

    private final Chassis m_chassis;
    private final double m_degreesToTurn;

    public TurnToSetPoint(Chassis chassis, double degrees) {
        m_chassis = chassis;
        m_degreesToTurn = degrees;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    public void execute() {
        m_chassis.turn(m_degreesToTurn);
    }

    @Override
    public boolean isFinished() {
        return m_chassis.isTurnFinished(m_degreesToTurn);
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
    }



}
