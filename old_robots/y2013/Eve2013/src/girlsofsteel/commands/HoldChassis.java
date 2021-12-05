package girlsofsteel.commands;

public class HoldChassis extends CommandBase {

    public HoldChassis(){
        requires(drive);
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initHoldPosition();
    }

    @Override
    protected void execute() {
        chassis.holdPosition();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.disablePositionPIDs();
        chassis.stopEncoders();
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
