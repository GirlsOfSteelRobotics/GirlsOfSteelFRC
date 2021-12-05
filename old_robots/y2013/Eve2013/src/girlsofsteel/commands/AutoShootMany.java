package girlsofsteel.commands;

public class AutoShootMany extends CommandBase {

    public final static double WAIT_TIME = 1.0;

    private final int shots;
    private final double desiredVoltage;

    private int counter;
    private boolean pushed;
    private double time;

    public AutoShootMany(int numShots, double desiredVoltage){
        requires(shooter);
        requires(feeder);
        shots = numShots;
        this.desiredVoltage = desiredVoltage;
    }

    @Override
    protected void initialize() {
        counter = 0;
        pushed = false;
        shooter.setJags(desiredVoltage);
        time = timeSinceInitialized();
        while(timeSinceInitialized() - time < 4){ // NOPMD(EmptyWhileStmt)
            //overall wait time is 4 + WAIT_TIME = 5
        }
        time = timeSinceInitialized();
    }

    @Override
    protected void execute() {
        if(timeSinceInitialized() - time > WAIT_TIME){
            if(!pushed){
                feeder.pushShooter();
                pushed = true;
            }else{
                feeder.pullShooter();
                pushed = false;
//                counter++;
            }
            time = timeSinceInitialized();
        }
    }

    @Override
    protected boolean isFinished() {
        return counter >= shots;//counter is never changed -> continue to shoot
            //just in case a frisbee got stuck
    }

    @Override
    protected void end() {
        shooter.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
