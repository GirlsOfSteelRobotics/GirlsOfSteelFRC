package girlsofsteel.commands;

public class ResetEncoder extends CommandBase{

    @Override
    protected void initialize() {
        chassis.initEncoders();
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
