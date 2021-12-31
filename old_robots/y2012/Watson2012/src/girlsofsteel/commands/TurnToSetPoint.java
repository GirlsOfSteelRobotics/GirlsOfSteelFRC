package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;

public class TurnToSetPoint extends CommandBase {

    private final Chassis m_chassis;
    private final double m_degreesToTurn;

    public TurnToSetPoint(Chassis chassis, double degrees) {
        m_chassis = chassis;
        m_degreesToTurn = degrees;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
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
