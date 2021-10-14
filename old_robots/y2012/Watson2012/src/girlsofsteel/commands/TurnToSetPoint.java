package girlsofsteel.commands;

public class TurnToSetPoint extends CommandBase {

    double degreesToTurn;

    public TurnToSetPoint(double degrees){
        degreesToTurn = degrees;
        requires(chassis);
    }

    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    protected void execute() {
        chassis.turn(degreesToTurn);
    }

    protected boolean isFinished() {
        return chassis.isTurnFinished(degreesToTurn);
    }

    protected void end() {
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    protected void interrupted() {
        end();
    }

}
