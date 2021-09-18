package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTDriveSlowVelocity extends CommandBase {

    Joystick driverJoystick;
    
    double xAxis;
    double yAxis;
    
    double p;
    double i;
    
    public TESTDriveSlowVelocity(){
        requires(chassis);
        SmartDashboard.putDouble("Chassis P", 0.0);
        SmartDashboard.putDouble("Chassis I", 0.0);
    }
    
    protected void initialize() {
        driverJoystick = oi.getDriverJoystick();
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    protected void execute() {
        p = SmartDashboard.getDouble("Chassis P", 0.0);
        i = SmartDashboard.getDouble("Chassis I", 0.0);
        chassis.setRatePIDValues(p,i,0.0);
        xAxis = driverJoystick.getX();
        yAxis = driverJoystick.getY();
        chassis.driveVelocityLinear(xAxis, yAxis);
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
