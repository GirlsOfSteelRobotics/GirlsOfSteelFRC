package girlsofsteel.commands;

public class DisableRotation extends CommandBase {

    @Override
    protected void initialize() {
        chassis.startAutoRotation();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return !chassis.isAutoRotating();
    }

    @Override
    protected void end() {
        chassis.stopAutoRotation();
        chassis.startManualRotation();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
