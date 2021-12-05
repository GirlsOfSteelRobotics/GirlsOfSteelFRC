package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TestShooter extends CommandBase {

    private double speed;

    public TestShooter(){
        SmartDashboard.putNumber("Speed", 0.0);
        SmartDashboard.putBoolean("Shoot", false);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        speed = SmartDashboard.getNumber("Speed",0.0);
        if(SmartDashboard.getBoolean("Shoot", false)){
            shooter.setJags(speed);
        }
        else{
            shooter.stopJags();
            shooter.setShootFalse();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.stopJags();
        shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
