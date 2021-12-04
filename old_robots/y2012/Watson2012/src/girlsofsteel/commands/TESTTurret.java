package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTTurret extends CommandBase {

    double speed;

    double pulses;
    boolean inDegrees;

    public TESTTurret(){
        requires(turret);
        SmartDashboard.putNumber("Turret Jags Speed", 0.0);
        SmartDashboard.putNumber("Turret Encoder Pulses", 0.0);
        SmartDashboard.putBoolean("Turret Encoder in Degrees",false);
    }

    @Override
    protected void initialize() {
        turret.disablePID();
    }

    @Override
    protected void execute() {
        speed = SmartDashboard.getNumber("Turret Jags Speed", 0.0);
        turret.setJagSpeed(speed);
        pulses = SmartDashboard.getNumber("Turret Encoder Pulses", 0.0);
        inDegrees = SmartDashboard.getBoolean("Turret Encoder in Degrees",false);
        turret.setEncoderUnit(pulses, inDegrees);
        SmartDashboard.putNumber("Turret Encoder", turret.getEncoderDistance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        turret.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
