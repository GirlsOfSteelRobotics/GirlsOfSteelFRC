package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TestTurnToSetPoint extends GosCommand {

    private final Chassis m_chassis;
    private double m_degreesToTurn;

    public TestTurnToSetPoint(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        SmartDashboard.putNumber("Turn,degrees", 0.0);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    public void execute() {
        m_degreesToTurn = SmartDashboard.getNumber("Turn,degrees", 0.0);
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
