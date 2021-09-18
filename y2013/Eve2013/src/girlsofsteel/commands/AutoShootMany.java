package girlsofsteel.commands;

public class AutoShootMany extends CommandBase {

    public final static double WAIT_TIME = 1.0;
    
    int shots;
    double desiredVoltage;
    
    int counter;
    boolean pushed;
    double time;
    
    public AutoShootMany(int numShots, double desiredVoltage){
        requires(shooter);
        requires(feeder);
        shots = numShots;
        this.desiredVoltage = desiredVoltage;
    }
    
    protected void initialize() {
        counter = 0;
        pushed = false;
        shooter.setJags(desiredVoltage);
        time = timeSinceInitialized();
        while(timeSinceInitialized() - time < 4){
        }//overall wait time is 4 + WAIT_TIME = 5
        time = timeSinceInitialized();
    }

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

    protected boolean isFinished() {
        return counter >= shots;//counter is never changed -> continue to shoot
            //just in case a frisbee got stuck
    }

    protected void end() {
        shooter.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}