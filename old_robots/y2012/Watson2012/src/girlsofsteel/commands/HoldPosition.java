package girlsofsteel.commands;

public class HoldPosition extends CommandBase {

    public HoldPosition(){
        requires(chassis);
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
        chassis.endEncoders();
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
