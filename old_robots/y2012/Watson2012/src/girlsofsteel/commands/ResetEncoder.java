package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;

public class ResetEncoder extends CommandBase{

    private final Chassis m_chassis;

    public ResetEncoder(Chassis chassis) {
        m_chassis = chassis;
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {

    }

}
