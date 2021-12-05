package girlsofsteel.commands;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;

public class DriveVelocityLinear extends CommandBase{

    private Joystick joystick;

    private double xAxis;
    private double yAxis;

    private final double scale;

    public DriveVelocityLinear(double scale){
        requires(chassis);
        this.scale = scale;
    }

    @Override
    protected void initialize() {
        joystick = oi.getDriverJoystick();
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    @Override
    protected void execute() {
        chassis.setPIDsRate();
        xAxis = joystick.getX()*scale;
        yAxis = joystick.getY()*scale;
        chassis.driveVelocityLinear(xAxis, yAxis);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "R:" + chassis.getRightEncoderRate());
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "L:" + chassis.getLeftEncoderRate());
        DriverStationLCD.getInstance().updateLCD();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.disableRatePIDs();
        chassis.resetEncoders();
        chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
