package girlsofsteel.commands;

public class TurnToSetPoint extends CommandBase {
    
    double degreesToTurn;
    
    public TurnToSetPoint(){
        requires(chassis);
    }
    
    protected void initialize() {
        chassis.initRightEncoder();
        chassis.initLeftEncoder();
        chassis.initRightPositionPID();
        chassis.initLeftPositionPID();
    }

    protected void execute() {
        chassis.turn(degreesToTurn);
    }

    protected boolean isFinished() {
        if(chassis.isTurnFinished(degreesToTurn)) {
            return true;
        }else{
            return false;
        }
    }

    protected void end() {
        chassis.disableRightPositionPID();
        chassis.disableLeftPositionPID();
        chassis.endRightEncoder();
        chassis.endLeftEncoder();
    }

    protected void interrupted() {
        end();
    }

    
    
}
