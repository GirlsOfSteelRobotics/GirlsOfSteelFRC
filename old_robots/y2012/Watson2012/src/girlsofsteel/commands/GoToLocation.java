package girlsofsteel.commands;

public class GoToLocation extends CommandBase {

    private final double x;
    private final double y;
    private final double degreesToFace; //degree change relative to beginning position of the
    //robot to end facing

    public GoToLocation(double x, double y, double degreesToFace){
        this.x = x;
        this.y = y;
        this.degreesToFace = degreesToFace;
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        chassis.goToLocation(x, y, degreesToFace);
    }

    @Override
    protected boolean isFinished() {
        return chassis.isGoToLocationFinished(x, y);
    }

    @Override
    protected void end() {
        chassis.disableRatePIDs();
        chassis.endEncoders();
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
