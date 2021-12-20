package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.DriveFlag;

public class HoldChassis extends CommandBase {

    private final Chassis m_chassis;

    public HoldChassis(Chassis chassis, DriveFlag drive){
        requires(drive);
        m_chassis = chassis;
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
        m_chassis.stopEncoders();
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
