package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTTurnToSetPoint extends CommandBase {

    double degreesToTurn;

    public TESTTurnToSetPoint(){
        requires(chassis);
        SmartDashboard.putNumber("Turn,degrees", 0.0);
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        degreesToTurn = SmartDashboard.getNumber("Turn,degrees", 0.0);
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
