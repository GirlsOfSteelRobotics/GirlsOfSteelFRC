package girlsofsteel.commands;

public class ManualShoot extends CommandBase {

    double sliderValue;
    double shooterSpeed;

    public ManualShoot() {
        requires(shooter);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
        sliderValue = oi.getShooterSliderValue();
        shooterSpeed = shooter.manualShooterSpeedConverter(sliderValue);
        shooter.setPIDSpeed(shooterSpeed);
        if(shooter.isWithinSetPoint(shooterSpeed) && !oi.areTopRollersOverriden()){
            shooter.topRollersForward();
        }
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
