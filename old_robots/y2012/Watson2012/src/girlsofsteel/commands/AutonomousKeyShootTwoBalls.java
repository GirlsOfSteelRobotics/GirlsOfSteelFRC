package girlsofsteel.commands;

public class AutonomousKeyShootTwoBalls extends CommandBase {

    boolean autoTrack = false;
    double timeToShootTwoBalls = 7.0;
    double xDistance;
    double velocity;

    public AutonomousKeyShootTwoBalls(boolean autoTrack){
        requires(shooter);
        requires(collector);
        requires(turret);
        requires(chassis);
        this.autoTrack = autoTrack;
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    @Override
    protected void execute() {
        shooter.setPIDValues();
        if(autoTrack){
            turret.autoTrack();
        }
        velocity = 24.0;
        shooter.setPIDSpeed(velocity);
        if(shooter.isWithinSetPoint(velocity)){
            shooter.topRollersForward();
            collector.forwardBrush();
            collector.forwardMiddleConveyor();
        }
    }

    @Override
    protected boolean isFinished() {
        return timeSinceInitialized() > timeToShootTwoBalls;
    }

    @Override
    protected void end() {
        shooter.disablePID();
        shooter.topRollersOff();
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
