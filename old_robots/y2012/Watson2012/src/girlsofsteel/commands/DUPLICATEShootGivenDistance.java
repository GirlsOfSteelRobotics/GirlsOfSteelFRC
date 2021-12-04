package girlsofsteel.commands;

public class DUPLICATEShootGivenDistance extends CommandBase {

    double speed;

    public DUPLICATEShootGivenDistance(double speed) {
        requires(shooter);
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        shooter.initPID();
        shooter.initEncoder();
        System.out.println("Shooting initialized");
    }

    @Override
    protected void execute() {
        shooter.shoot(speed);

    }

    @Override
    protected boolean isFinished() {
        //If this is bridge shooting, wait a long time. Otherwise(key shooting) don't wait a long time
        if(speed > 25){
        return timeSinceInitialized() > 10;
        }
        else{
            return timeSinceInitialized() > 5;
        }
    }

    @Override
    protected void end() {
        System.out.println("Shooting Finished");
        shooter.disablePID();
        shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
