package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraShooterTest extends CommandBase {

    double speed;
    
    public CameraShooterTest() {
        requires(shooter);
        requires(collector);
        SmartDashboard.putDouble("CST,speed", 0.0);
        SmartDashboard.putBoolean("shoot", false);
        SmartDashboard.putBoolean("top rollers", false);
        SmartDashboard.putBoolean("collectors", false);
        SmartDashboard.putDouble("CST,P", 0.0);
        SmartDashboard.putDouble("CST,I", 0.0);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
        shooter.setPIDValues(SmartDashboard.getDouble("CST,P",0.0), SmartDashboard.getDouble("CST,I",0.0),0.0);
        speed = SmartDashboard.getDouble("CST,speed", 0.0);
        if(SmartDashboard.getBoolean("collectors", false)){
            collector.reverseBrush();
            collector.reverseMiddleConveyor();
        }else{
            collector.stopBrush();
            collector.stopMiddleConveyor();
        }
        if (SmartDashboard.getBoolean("top rollers", false)) {
            shooter.topRollersForward();
        } else {
            shooter.topRollersOff();
        }
        if(SmartDashboard.getBoolean("shoot", false)){
            shooter.setPIDSpeed(speed);
        }else{
            shooter.setPIDSpeed(0.0);
        }
        SmartDashboard.putBoolean("ready to shoot", shooter.isWithinSetPoint(speed));
        SmartDashboard.putDouble("Enc rate shoot: ", shooter.getEncoderRate());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.disablePID();
    }

    protected void interrupted() {
        end();
    }
}
