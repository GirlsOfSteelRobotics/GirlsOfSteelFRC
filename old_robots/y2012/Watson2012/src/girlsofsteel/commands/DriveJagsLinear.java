package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveJagsLinear extends CommandBase{

    Joystick joystick;

    double scale;
    double xAxis;
    double yAxis;

    public DriveJagsLinear(double scale){
        requires(chassis);
        this.scale = scale;
    }

    @Override
    protected void initialize() {
        joystick = oi.getDriverJoystick();
    }

    @Override
    protected void execute() {
        xAxis = joystick.getX() * scale;
        yAxis = joystick.getY() * scale;
        chassis.driveJagsLinear(xAxis, yAxis);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
