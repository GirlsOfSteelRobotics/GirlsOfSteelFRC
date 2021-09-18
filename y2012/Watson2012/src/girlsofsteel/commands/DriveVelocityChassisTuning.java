package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveVelocityChassisTuning extends CommandBase {
            Joystick joystick;
    public DriveVelocityChassisTuning(){
        SmartDashboard.putDouble("DVCT,p", 0.0);
        SmartDashboard.putDouble("DVCT,i", 0.0);
       // SmartDashboard.putDouble("setpoint", 0.0);
        SmartDashboard.putDouble("encoder rate right", 0.0);
        SmartDashboard.putDouble("encoder rate left", 0.0);
                requires(chassis);
}

    protected void initialize() {
        chassis.initEncoders();
        chassis.initRatePIDs();
        joystick = oi.getOperatorJoystick();
    }

    protected void execute() {
        chassis.setRatePIDValues(SmartDashboard.getDouble("DVCT,p", 0.0), SmartDashboard.getDouble("DVCT,i", 0.0),0.0);
        chassis.setRatePIDSetPoint(/*joystick.getY());*/SmartDashboard.getDouble("setpoint", 0.0));
        SmartDashboard.putDouble("encoder rate right", chassis.getRightEncoderRate());
        SmartDashboard.putDouble("encoder rate left", chassis.getLeftEncoderRate());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.disableRatePIDs();
        chassis.endEncoders();
    }

    protected void interrupted() {
        end();
    }
}
