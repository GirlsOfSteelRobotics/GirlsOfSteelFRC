package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTShootUsingTable extends CommandBase {

    double addition;
    double speed;
    double cameraDistance;
    double distance;

    public TESTShootUsingTable() {
        requires(shooter);
//        SmartDashboard.putNumber("Bank Addition", 0.0);
        SmartDashboard.putNumber("Distance", 0.0);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        cameraDistance = shooter.getDistance();
    }

    protected void execute() {
//        addition = SmartDashboard.getNumber("Bank Addition", 0.0);
//        shooter.TESTAutoShootBank(addition,cameraDistance);
        distance = SmartDashboard.getNumber("Distance", 0.0);
        shooter.autoShoot(distance);
        SmartDashboard.putNumber("Shooter Encoder",shooter.getEncoderRate());
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
