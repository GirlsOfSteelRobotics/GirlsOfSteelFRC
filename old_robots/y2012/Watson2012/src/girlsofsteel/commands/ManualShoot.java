package girlsofsteel.commands;

public class ManualShoot extends CommandBase {

    private double sliderValue;
    private double shooterSpeed;

    public ManualShoot() {
        requires(shooter);
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    @Override
    protected void execute() {
        sliderValue = oi.getShooterSliderValue();
        shooterSpeed = shooter.manualShooterSpeedConverter(sliderValue);
        shooter.setPIDSpeed(shooterSpeed);
        if(shooter.isWithinSetPoint(shooterSpeed) && !oi.areTopRollersOverriden()){
            shooter.topRollersForward();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        if(!oi.areTopRollersOverriden()){
            shooter.topRollersOff();
        }
        shooter.disablePID();
        shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
