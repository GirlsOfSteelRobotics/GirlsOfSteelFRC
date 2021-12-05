
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class Drive extends CommandBase {

    private final double turningScale;
    private final double scale;

    private Joystick joystick;
    private double x;
    private double y;
    private double th;
    private final boolean gyroOn;

    public Drive(double scale, double turningScale, boolean gyroOn) {
        requires(drive);
        this.scale = scale;
        this.turningScale = turningScale;
        this.gyroOn = gyroOn;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        joystick = oi.getDrivingJoystick();
        chassis.resetGyro();
        chassis.startManualRotation();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        x = joystick.getX()*scale;
        y = joystick.getY()*scale;
        th = joystick.getZ()*turningScale;
        chassis.driveVoltage(x, y, th, 1.0, gyroOn);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        chassis.stopJags();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
