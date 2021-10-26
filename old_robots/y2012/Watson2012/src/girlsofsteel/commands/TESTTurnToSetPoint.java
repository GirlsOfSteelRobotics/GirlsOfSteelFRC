package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTTurnToSetPoint extends CommandBase {

    double degreesToTurn;

    public TESTTurnToSetPoint(){
        requires(chassis);
        SmartDashboard.putDouble("Turn,degrees", 0.0);
    }

    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    protected void execute() {
        degreesToTurn = SmartDashboard.getDouble("Turn,degrees", 0.0);
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
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    protected void interrupted() {
        end();
    }

}
