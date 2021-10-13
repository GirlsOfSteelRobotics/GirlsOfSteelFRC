package girlsofsteel.commands;

public class AutoShootBottom extends CommandBase {
    
    double xDistance;
    
    public AutoShootBottom(){
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
        //get xDistance from Camera here -> meters
        shooter.shootAuto(xDistance,shooter.BOTTOM_HOOP);
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