package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveSlowVelocity extends CommandBase {

    Joystick driverJoystick;

    double xAxis;
    double yAxis;

    public DriveSlowVelocity(){
        requires(chassis);
    }

    protected void initialize() {
        driverJoystick = oi.getDriverJoystick();
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    protected void execute() {
        chassis.setPIDsRate();
        xAxis = driverJoystick.getX();
        yAxis = driverJoystick.getY();
        chassis.driveSlowVelocity(xAxis, yAxis);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.disableRatePIDs();
        chassis.endEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }

}
