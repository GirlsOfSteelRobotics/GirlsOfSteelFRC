package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;

public class HoldPosition extends CommandBase {
    private final Chassis m_chassis;

    public HoldPosition(Chassis chassis){
        m_chassis = chassis;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initHoldPosition();
    }

    @Override
    protected void execute() {
        m_chassis.holdPosition();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
