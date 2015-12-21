package girlsofsteel.commands;

public class AutoShootTop extends CommandBase {
    
    double xDistance;
    
    public AutoShootTop(){
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
        //get xDistance from Camera here -> meters
        shooter.shootAuto(xDistance,shooter.TOP_HOOP);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.setPIDSpeed(0.0);
        shooter.disablePID();
        shooter.stopEncoder();
    }

    protected void interrupted() {
        end();
    }
    
}