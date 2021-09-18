package girlsofsteel.commands;

public class DisableRotation extends CommandBase {

    protected void initialize() {
        chassis.startAutoRotation();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return !chassis.isAutoRotating();
    }

    protected void end() {
        chassis.stopAutoRotation();
        chassis.startManualRotation();
    }

    protected void interrupted() {
        end();
    }
    
}
