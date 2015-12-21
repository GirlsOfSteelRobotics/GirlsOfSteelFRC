package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTShooter extends CommandBase {

    double speed;
    
    public TESTShooter(){
        SmartDashboard.putDouble("Shooter Jag Speed", 0.0);
    }
    
    protected void initialize() {
        shooter.initEncoder();
    }

    protected void execute() {
        speed = SmartDashboard.getDouble("Shooter Jag Speed", 0.0);
        shooter.setJags(speed);
        SmartDashboard.putDouble("Shooter Encoder", shooter.getEncoderRate());
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
