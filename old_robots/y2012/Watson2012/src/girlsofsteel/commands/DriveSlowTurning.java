package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;

public class DriveSlowTurning extends CommandBase{

    Joystick joystick;
    
    double scale;
    double turningScale;
    double xAxis;
    double yAxis;
    
    public DriveSlowTurning(double scale, double turningScale){
        requires(chassis);
        this.scale = scale;
        this.turningScale = turningScale;
    }
    
    protected void initialize() {
        joystick = oi.getDriverJoystick();
    }

    protected void execute() {
        xAxis = joystick.getX()*scale;
        yAxis = joystick.getY()*scale;
        chassis.driveJagsLinearSlowTurning(xAxis, yAxis,turningScale);
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