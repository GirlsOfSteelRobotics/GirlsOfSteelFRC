package girlsofsteel.commands;

public class IncrementShoot extends CommandBase {

    private double sliderValue;
    private double incrementValue;
    private double speed;

    public IncrementShoot() {
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
        incrementValue = shooter.getIncrementValue(sliderValue);
//        speed = shooter.getEncoderRate() + incrementValue;
        speed = shooter.getPIDSetPoint() + incrementValue;
        shooter.setPIDSpeed(speed);
        if (shooter.isWithinSetPoint(speed) && !oi.areTopRollersOverriden()) {
            shooter.topRollersForward();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.topRollersOff();
        shooter.disablePID();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
