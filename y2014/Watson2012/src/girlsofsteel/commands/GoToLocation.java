package girlsofsteel.commands;

public class GoToLocation extends CommandBase {

    double x;
    double y;
    double degreesToFace; //degree change relative to beginning position of the
    //robot to end facing
    
    public GoToLocation(double x, double y, double degreesToFace){
        this.x = x;
        this.y = y;
        this.degreesToFace = degreesToFace;
    }

    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    protected void execute() {
        chassis.goToLocation(x, y, degreesToFace);
    }

    protected boolean isFinished() {
        return chassis.isGoToLocationFinished(x, y);
    }

    protected void end() {
        chassis.disableRatePIDs();
        chassis.endEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
}
