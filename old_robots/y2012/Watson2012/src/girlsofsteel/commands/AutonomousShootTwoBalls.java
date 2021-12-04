package girlsofsteel.commands;

public class AutonomousShootTwoBalls extends CommandBase {

    double timeToShootTwoBalls = 5.0; //TODO find how long shooting 2 balls takes
    double cameraDistance;

    public AutonomousShootTwoBalls(){
        requires(shooter);
        requires(collector);
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        turret.initEncoder();
        turret.enablePID();
        cameraDistance = shooter.getDistance();
    }

    @Override
    protected void execute() {
        turret.autoTrack();
        shooter.autoShoot(cameraDistance);
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
