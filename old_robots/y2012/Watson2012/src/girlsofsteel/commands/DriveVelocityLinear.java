package girlsofsteel.commands;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;

public class DriveVelocityLinear extends CommandBase{

    Joystick joystick;

    double xAxis;
    double yAxis;

    double scale;

    public DriveVelocityLinear(double scale){
        requires(chassis);
        this.scale = scale;
    }

    protected void initialize() {
        joystick = oi.getDriverJoystick();
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    protected void execute() {
        chassis.setPIDsRate();
        xAxis = joystick.getX()*scale;
        yAxis = joystick.getY()*scale;
        chassis.driveVelocityLinear(xAxis, yAxis);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "R:" + chassis.getRightEncoderRate());
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "L:" + chassis.getLeftEncoderRate());
        DriverStationLCD.getInstance().updateLCD();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.disableRatePIDs();
        chassis.resetEncoders();
        chassis.endEncoders();
    }

    protected void interrupted() {
        end();
    }

}
