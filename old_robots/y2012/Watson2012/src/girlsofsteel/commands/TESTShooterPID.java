package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTShooterPID extends CommandBase {

    boolean autoShoot;
    boolean bank;

    double speed;
    double setpoint;

    double p;
    double i;
    double d;

    double rate;
    double difference;

    double[] rateList;

    public TESTShooterPID() {
        requires(shooter);
        SmartDashboard.putDouble("Shooter Setpoint", 0.0);
        SmartDashboard.putDouble("Shooter,p", 0.0);
        SmartDashboard.putDouble("Shooter,i", 0.0);
//        SmartDashboard.putDouble("Shooter,d", 0.0);
//        SmartDashboard.putBoolean("Auto Shoot?", false);
//        SmartDashboard.putBoolean("Bank?", false);
        SmartDashboard.putDouble("Shooter Encoder", shooter.getEncoderRate());
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
//        autoShoot = SmartDashboard.getBoolean("Auto Shoot?", false);
//        bank = SmartDashboard.getBoolean("Bank?", false);
//        SmartDashboard.putDouble("Shooter Encoder", shooter.getEncoderRate());
//        if(!autoShoot){
            p = SmartDashboard.getDouble("Shooter,p", 0.0);
            i = SmartDashboard.getDouble("Shooter,i",0.0);
//            d = SmartDashboard.getDouble("Shooter,d",0.0);
            shooter.setPIDValues(p, i, d);
            setpoint = SmartDashboard.getDouble("Shooter Setpoint", 0.0);
            shooter.setPIDSpeed(setpoint);
//        }else{
//            if(bank){
//                shooter.autoShootBank();
//            }else{
//                shooter.autoShoot();
//            }
//        }
            SmartDashboard.putDouble("Shooter Encoder", shooter.getEncoderRate());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.disablePID();
        shooter.stopEncoder();
        shooter.stopJags();
    }

    protected void interrupted() {
        end();
    }

}
