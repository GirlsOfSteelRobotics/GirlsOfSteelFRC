package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GetLeftEncoderRate extends CommandBase {

    double previousTime;
    double currentTime;
    double previousPosition;
    double currentPosition;
    
    protected void initialize() {
        chassis.initLeftEncoder( 1.0/250.0);
        currentTime = System.currentTimeMillis()/1000.0;
        currentPosition = chassis.leftEncoderDistance();
    }

    protected void execute() {
        previousTime = currentTime;
        previousPosition = currentPosition;
        currentTime = System.currentTimeMillis()/1000.0;
        currentPosition = chassis.leftEncoderDistance();
        double encoderRate = (currentPosition - previousPosition)/(currentTime - previousTime);
        SmartDashboard.putDouble("Encoder Rate",encoderRate);
        SmartDashboard.putDouble("Rate", chassis.getLeftRate());
        //allows you to tune the PID & print values using the SmartDashBoard
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.endLeftEncoder();
    }

    protected void interrupted() {
        end();
    }
    
}
