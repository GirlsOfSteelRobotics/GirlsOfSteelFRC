package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveVelocityChassisTuning extends CommandBase {
            private Joystick joystick;
    public DriveVelocityChassisTuning(){
        SmartDashboard.putNumber("DVCT,p", 0.0);
        SmartDashboard.putNumber("DVCT,i", 0.0);
       // SmartDashboard.putNumber("setpoint", 0.0);
        SmartDashboard.putNumber("encoder rate right", 0.0);
        SmartDashboard.putNumber("encoder rate left", 0.0);
                requires(chassis);
}

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initRatePIDs();
        joystick = oi.getOperatorJoystick();
    }

    @Override
    protected void execute() {
        chassis.setRatePIDValues(SmartDashboard.getNumber("DVCT,p", 0.0), SmartDashboard.getNumber("DVCT,i", 0.0),0.0);
        chassis.setRatePIDSetPoint(/*joystick.getY());*/SmartDashboard.getNumber("setpoint", 0.0));
        SmartDashboard.putNumber("encoder rate right", chassis.getRightEncoderRate());
        SmartDashboard.putNumber("encoder rate left", chassis.getLeftEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.disableRatePIDs();
        chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
