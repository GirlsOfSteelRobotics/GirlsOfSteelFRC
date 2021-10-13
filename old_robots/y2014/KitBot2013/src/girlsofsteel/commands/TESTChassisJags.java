package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTChassisJags extends CommandBase {
    
    double speed;
    
    public TESTChassisJags(){
        requires(chassisPID);
        SmartDashboard.putBoolean("Right Jags", false);
        SmartDashboard.putBoolean("Left Jags", false);
        SmartDashboard.putDouble("Jag speed", 0.0);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        speed = SmartDashboard.getDouble("Jag speed", 0.0);
        if(SmartDashboard.getBoolean("Right Jags", false))
            chassisPID.setRightJags(speed);
        if(SmartDashboard.getBoolean("Left Jags", false))
            chassisPID.setLeftJags(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassisPID.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}