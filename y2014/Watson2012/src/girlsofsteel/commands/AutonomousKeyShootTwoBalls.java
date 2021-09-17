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
    
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

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

    protected boolean isFinished() {
        if(timeSinceInitialized() > timeToShootTwoBalls){
            return true;
        }else{
            return false;
        }
    }

    protected void end() {
        shooter.disablePID();
        shooter.topRollersOff();
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    protected void interrupted() {
        end();
    }
    
}