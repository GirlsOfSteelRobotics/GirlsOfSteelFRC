package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraShooterTest extends CommandBase {

    private double speed;

    public CameraShooterTest() {
        requires(shooter);
        requires(collector);
        SmartDashboard.putNumber("CST,speed", 0.0);
        SmartDashboard.putBoolean("shoot", false);
        SmartDashboard.putBoolean("top rollers", false);
        SmartDashboard.putBoolean("collectors", false);
        SmartDashboard.putNumber("CST,P", 0.0);
        SmartDashboard.putNumber("CST,I", 0.0);
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    @Override
    protected void execute() {
        shooter.setPIDValues(SmartDashboard.getNumber("CST,P",0.0), SmartDashboard.getNumber("CST,I",0.0),0.0);
        speed = SmartDashboard.getNumber("CST,speed", 0.0);
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
        SmartDashboard.putNumber("Enc rate shoot: ", shooter.getEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.disablePID();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
