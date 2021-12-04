package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Jisue
 */
public class ScaledArcadeDrive extends CommandBase {

    private final double scale;
    Joystick joystick;
    private double x;
    private double y;

    public ScaledArcadeDrive(double scale) {
        requires(chassis);
        this.scale = scale;
    }

    @Override
    protected void initialize() {
        joystick = oi.getChassisJoystick();
        //SmartDashboard.putNumber("Scale", 1);

    }

    @Override
    protected void execute() {
        x = joystick.getX();
        y = joystick.getY();
        //scale = SmartDashboard.getNumber("Scale", 0);
        chassis.scaleArcadeDrive(x, y, scale);
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
