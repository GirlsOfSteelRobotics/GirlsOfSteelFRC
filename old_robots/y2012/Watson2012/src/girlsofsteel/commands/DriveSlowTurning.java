package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveSlowTurning extends CommandBase{

    private Joystick joystick;

    private final double scale;
    private final double turningScale;
    private double xAxis;
    private double yAxis;

    public DriveSlowTurning(double scale, double turningScale){
        requires(chassis);
        this.scale = scale;
        this.turningScale = turningScale;
    }

    @Override
    protected void initialize() {
        joystick = oi.getDriverJoystick();
    }

    @Override
    protected void execute() {
        xAxis = joystick.getX()*scale;
        yAxis = joystick.getY()*scale;
        chassis.driveJagsLinearSlowTurning(xAxis, yAxis,turningScale);
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
