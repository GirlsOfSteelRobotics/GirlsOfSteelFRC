package girlsofsteel.commands;

public class StartGyro extends CommandBase {

    int angle;

    public StartGyro(int angle){
        this.angle = angle;
    }

    protected void initialize() {
    }

    protected void execute() {
        angle -= chassis.getGyroAngle();
        chassis.setFieldAdjustment(angle);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}
