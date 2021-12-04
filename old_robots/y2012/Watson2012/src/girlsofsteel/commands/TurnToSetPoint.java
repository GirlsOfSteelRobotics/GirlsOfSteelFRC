package girlsofsteel.commands;

public class TurnToSetPoint extends CommandBase {

    double degreesToTurn;

    public TurnToSetPoint(double degrees){
        degreesToTurn = degrees;
        requires(chassis);
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        chassis.turn(degreesToTurn);
    }

    @Override
    protected boolean isFinished() {
        return chassis.isTurnFinished(degreesToTurn);
    }

    @Override
    protected void end() {
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
