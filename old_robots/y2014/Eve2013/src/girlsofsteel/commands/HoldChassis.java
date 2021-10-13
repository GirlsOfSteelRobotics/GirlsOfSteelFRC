package girlsofsteel.commands;

public class HoldChassis extends CommandBase {

    public HoldChassis(){
        requires(drive);
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
        chassis.stopEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}
