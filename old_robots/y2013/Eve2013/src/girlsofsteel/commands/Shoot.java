/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;


public class Shoot extends CommandBase {

    private final boolean camera;

    private double speed;
    private double time;

    public Shoot(double speed) {
        this.speed = speed;
        camera = false;
        requires(shooter);
    }

    public Shoot(){
        camera = true;
        requires(shooter);
    }

    @Override
    protected void initialize() {
        if(camera){
//            speed = PositionInfo.getSpeed(ShooterCamera.getLocation());
            speed = 0.73;
        }
        shooter.initEncoder();
        time = timeSinceInitialized();
//        shooter.initPID();
    }

    @Override
    protected void execute() {
        if (timeSinceInitialized() - time > 2) { shooter.setShootTrue(); }
        shooter.setJags(speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
//        shooter.disablePID();
        shooter.stopJags();
        shooter.stopEncoder();
        shooter.setShootFalse();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
