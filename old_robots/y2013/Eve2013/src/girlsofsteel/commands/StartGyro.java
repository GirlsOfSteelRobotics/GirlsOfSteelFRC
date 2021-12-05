package girlsofsteel.commands;

public class StartGyro extends CommandBase {

    private int angle;

    public StartGyro(int angle){
        this.angle = angle;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        angle -= chassis.getGyroAngle();
        chassis.setFieldAdjustment(angle);
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
