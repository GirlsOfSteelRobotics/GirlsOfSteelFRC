package girlsofsteel.commands;

public class ToggleGyro extends CommandBase {

    @Override
    protected void initialize() {
        if(chassis.isGyroEnabled())
        {
            chassis.stopGyro();
        }else{
            chassis.resetGyro();
        }
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
        end();
    }

}
