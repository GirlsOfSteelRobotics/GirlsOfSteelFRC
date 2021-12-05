package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTShooterPID extends CommandBase {

    private boolean autoShoot;
    private boolean bank;

    private double speed;
    private double setpoint;

    private double p;
    private double i;
    private double d;

    private double rate;
    private double difference;

    private double[] rateList;

    public TESTShooterPID() {
        requires(shooter);
        SmartDashboard.putNumber("Shooter Setpoint", 0.0);
        SmartDashboard.putNumber("Shooter,p", 0.0);
        SmartDashboard.putNumber("Shooter,i", 0.0);
//        SmartDashboard.putNumber("Shooter,d", 0.0);
//        SmartDashboard.putBoolean("Auto Shoot?", false);
//        SmartDashboard.putBoolean("Bank?", false);
        SmartDashboard.putNumber("Shooter Encoder", shooter.getEncoderRate());
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    @Override
    protected void execute() {
//        autoShoot = SmartDashboard.getBoolean("Auto Shoot?", false);
//        bank = SmartDashboard.getBoolean("Bank?", false);
//        SmartDashboard.putNumber("Shooter Encoder", shooter.getEncoderRate());
//        if(!autoShoot){
            p = SmartDashboard.getNumber("Shooter,p", 0.0);
            i = SmartDashboard.getNumber("Shooter,i",0.0);
//            d = SmartDashboard.getNumber("Shooter,d",0.0);
            shooter.setPIDValues(p, i, d);
            setpoint = SmartDashboard.getNumber("Shooter Setpoint", 0.0);
            shooter.setPIDSpeed(setpoint);
//        }else{
//            if(bank){
//                shooter.autoShootBank();
//            }else{
//                shooter.autoShoot();
//            }
//        }
            SmartDashboard.putNumber("Shooter Encoder", shooter.getEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.disablePID();
        shooter.stopEncoder();
        shooter.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
