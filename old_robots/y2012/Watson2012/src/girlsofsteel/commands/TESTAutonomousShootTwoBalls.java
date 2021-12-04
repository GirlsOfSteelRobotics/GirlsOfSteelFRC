package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTAutonomousShootTwoBalls extends CommandBase {

    double timeToShootTwoBalls;
    double xDistance;
    double velocity;

    double range = shooter.VELOCITY_ERROR_RANGE; //shooter wheel needs to be within this range for rates before it will shoot

    public TESTAutonomousShootTwoBalls(){
        SmartDashboard.putNumber("ASTB,speed", 0.0);
        SmartDashboard.putNumber("ASTB,time",0.0);
        requires(shooter);
        requires(chassis);
        requires(collector);
        requires(turret);
        requires(bridge);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
    }

    protected void execute() {
        timeToShootTwoBalls = SmartDashboard.getNumber("ASTB,time", 0.0);
        velocity = SmartDashboard.getNumber("ASTB,speed", 0.0);
        xDistance = shooter.getDistance();
//        velocity = shooter.getBallVelocityFrTable(xDistance);
//        this.ballVelocity = shooter.getImmobileBallVelocity(xDistance);
        shooter.setPIDSpeed(velocity);
        if(/*shooter.isWithinSetPoint(this.ballVelocity,range) &&*/ timeSinceInitialized()>1.0){
            shooter.topRollersForward();
//            collector.forwardBrush();
//            collector.forwardMiddleConveyor();
            collector.reverseBrush();
            collector.reverseMiddleConveyor();
        }
    }

    protected boolean isFinished() {
        if(timeSinceInitialized() > timeToShootTwoBalls){
            return true;
        }else{
            return false;
        }
    }

    protected void end() {
        shooter.disablePID();
        shooter.topRollersOff();
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    protected void interrupted() {
        end();
    }

}
