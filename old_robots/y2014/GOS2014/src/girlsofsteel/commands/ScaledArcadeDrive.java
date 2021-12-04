package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Jisue
 */
public class ScaledArcadeDrive extends CommandBase {

    private double scale;
    Joystick joystick;
    private double x;
    private double y;

    public ScaledArcadeDrive(double scale) {
        requires(chassis);
        this.scale = scale;
    }

    protected void initialize() {
        joystick = oi.getChassisJoystick();
        //SmartDashboard.putNumber("Scale", 1);

    }

    protected void execute() {
        x = joystick.getX();
        y = joystick.getY();
        //scale = SmartDashboard.getNumber("Scale", 0);
        chassis.scaleArcadeDrive(x, y, scale);
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
