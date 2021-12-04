package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveVelocityChassisTuning extends CommandBase {
            Joystick joystick;
    public DriveVelocityChassisTuning(){
        SmartDashboard.putNumber("DVCT,p", 0.0);
        SmartDashboard.putNumber("DVCT,i", 0.0);
       // SmartDashboard.putNumber("setpoint", 0.0);
        SmartDashboard.putNumber("encoder rate right", 0.0);
        SmartDashboard.putNumber("encoder rate left", 0.0);
                requires(chassis);
}

    protected void initialize() {
        chassis.initEncoders();
        chassis.initRatePIDs();
        joystick = oi.getOperatorJoystick();
    }

    protected void execute() {
        chassis.setRatePIDValues(SmartDashboard.getNumber("DVCT,p", 0.0), SmartDashboard.getNumber("DVCT,i", 0.0),0.0);
        chassis.setRatePIDSetPoint(/*joystick.getY());*/SmartDashboard.getNumber("setpoint", 0.0));
        SmartDashboard.putNumber("encoder rate right", chassis.getRightEncoderRate());
        SmartDashboard.putNumber("encoder rate left", chassis.getLeftEncoderRate());
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
