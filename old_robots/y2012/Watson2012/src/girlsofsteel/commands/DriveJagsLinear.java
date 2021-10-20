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

    protected void initialize() {
        joystick = oi.getDriverJoystick();
    }

    protected void execute() {
        xAxis = joystick.getX() * scale;
        yAxis = joystick.getY() * scale;
        chassis.driveJagsLinear(xAxis, yAxis);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }

}
