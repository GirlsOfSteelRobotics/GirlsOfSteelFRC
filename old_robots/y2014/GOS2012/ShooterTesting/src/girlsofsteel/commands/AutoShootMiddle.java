package girlsofsteel.commands;

public class AutoShootMiddle extends CommandBase {
    
    double xDistance;
    
    public AutoShootMiddle(){
        requires(shooter);
    }
    
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
        //get xDistance from Camera here -> meters
        shooter.shootAuto(xDistance,shooter.MIDDLE_HOOP);
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