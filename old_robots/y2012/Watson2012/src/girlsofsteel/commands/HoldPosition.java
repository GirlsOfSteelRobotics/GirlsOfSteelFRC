package girlsofsteel.commands;

public class HoldPosition extends CommandBase {

    public HoldPosition(){
        requires(chassis);
    }
    
    protected void initialize() {
        chassis.initEncoders();
        chassis.initHoldPosition();
    }

    protected void execute() {
        chassis.holdPosition();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.disablePositionPIDs();
        chassis.endEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}
