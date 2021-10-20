package girlsofsteel.commands;

public class IncrementShoot extends CommandBase {

    double sliderValue;
    double incrementValue;
    double speed;

    public IncrementShoot() {
        requires(shooter);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

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

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.topRollersOff();
        shooter.disablePID();
    }

    protected void interrupted() {
        end();
    }
}
