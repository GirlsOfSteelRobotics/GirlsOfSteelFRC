package girlsofsteel.commands;

public class MoveToSetPoint extends CommandBase {

    double distanceToMove;
    
    public MoveToSetPoint(){
        requires(chassis);
    }
    
    protected void initialize() {
        chassis.initRightEncoder();
        chassis.initLeftEncoder();
        chassis.initRightPositionPID();
        chassis.initLeftPositionPID();
    }

    protected void execute() {
        chassis.move(distanceToMove);
    }

    protected boolean isFinished() {
        if(chassis.isMoveFinished(distanceToMove)){
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
