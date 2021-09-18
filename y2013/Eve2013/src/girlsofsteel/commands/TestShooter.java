package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TestShooter extends CommandBase {
    
    double speed;
    
    public TestShooter(){
        SmartDashboard.putNumber("Speed", 0.0);
        SmartDashboard.putBoolean("Shoot", false);
    }
    
    protected void initialize() {
    }
    
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

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.stopJags();
        shooter.stopEncoder();
    }

    protected void interrupted() {
        end();
    }
    
}