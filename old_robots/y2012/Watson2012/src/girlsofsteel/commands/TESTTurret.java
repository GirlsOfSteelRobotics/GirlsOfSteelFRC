package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTTurret extends CommandBase {

    double speed;
    
    double pulses;
    boolean inDegrees;
    
    public TESTTurret(){
        requires(turret);
        SmartDashboard.putDouble("Turret Jags Speed", 0.0);
        SmartDashboard.putDouble("Turret Encoder Pulses", 0.0);
        SmartDashboard.putBoolean("Turret Encoder in Degrees",false);
    }
    
    protected void initialize() {
        turret.disablePID();
    }

    protected void execute() {
        speed = SmartDashboard.getDouble("Turret Jags Speed", 0.0);
        turret.setJagSpeed(speed);
        pulses = SmartDashboard.getDouble("Turret Encoder Pulses", 0.0);
        inDegrees = SmartDashboard.getBoolean("Turret Encoder in Degrees",false);
        turret.setEncoderUnit(pulses, inDegrees);
        SmartDashboard.putDouble("Turret Encoder", turret.getEncoderDistance());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        turret.stopJag();
    }

    protected void interrupted() {
        end();
    }
    
}
