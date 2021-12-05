package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveSlowVelocity extends CommandBase {

    private Joystick driverJoystick;

    private double xAxis;
    private double yAxis;

    public DriveSlowVelocity(){
        requires(chassis);
    }

    @Override
    protected void initialize() {
        driverJoystick = oi.getDriverJoystick();
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    @Override
    protected void execute() {
        chassis.setPIDsRate();
        xAxis = driverJoystick.getX();
        yAxis = driverJoystick.getY();
        chassis.driveSlowVelocity(xAxis, yAxis);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.disableRatePIDs();
        chassis.endEncoders();
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
