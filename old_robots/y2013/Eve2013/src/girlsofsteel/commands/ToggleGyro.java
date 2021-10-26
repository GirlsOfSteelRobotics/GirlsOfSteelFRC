package girlsofsteel.commands;

public class ToggleGyro extends CommandBase {

    protected void initialize() {
        if(chassis.isGyroEnabled())
        {
            chassis.stopGyro();
        }else{
            chassis.resetGyro();
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
