package girlsofsteel.commands;

public class MoveToSetPoint extends CommandBase {

    double distanceToMove;

    public MoveToSetPoint(double distance) {
        distanceToMove = distance;
        requires(chassis);
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
        timeFinished = -1;
    }

    @Override
    protected void execute() {
        chassis.setPIDsPosition();
        chassis.move(distanceToMove);
        if (timeFinished == -1 && chassis.isMoveFinished(distanceToMove)) {
            timeFinished = timeSinceInitialized();
        }
//        if(timeSinceInitialized() > 3){
//            System.out.println("Move to Set Point Timed Out");
//            end();
//        }
    }

    @Override
    protected boolean isFinished() {
        if (timeFinished != -1 && timeSinceInitialized() - timeFinished > 0.5) {
            System.out.println("Move to Set Point Done");
            return true;
        }
        return false;
    }

    @Override
    protected void end() {
        System.out.println("Done with moving");
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }
    private double timeFinished = -1;
}
