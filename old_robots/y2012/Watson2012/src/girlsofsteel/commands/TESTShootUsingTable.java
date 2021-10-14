package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTShootUsingTable extends CommandBase {

    double addition;
    double speed;
    double cameraDistance;
    double distance;

    public TESTShootUsingTable() {
        requires(shooter);
//        SmartDashboard.putDouble("Bank Addition", 0.0);
        SmartDashboard.putDouble("Distance", 0.0);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        cameraDistance = shooter.getDistance();
    }

    protected void execute() {
//        addition = SmartDashboard.getDouble("Bank Addition", 0.0);
//        shooter.TESTAutoShootBank(addition,cameraDistance);
        distance = SmartDashboard.getDouble("Distance", 0.0);
        shooter.autoShoot(distance);
        SmartDashboard.putDouble("Shooter Encoder",shooter.getEncoderRate());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        if(!oi.areTopRollersOverriden()){
            shooter.topRollersOff();
        }
        shooter.disablePID();
        shooter.stopEncoder();
    }

    protected void interrupted() {
        end();
    }
}
