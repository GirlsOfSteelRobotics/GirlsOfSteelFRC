/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.objects.PositionInfo;
import girlsofsteel.objects.ShooterCamera;

public class Shoot extends CommandBase {

    boolean camera;
    
    double speed;
    double time;

    public Shoot(double speed) {
        this.speed = speed;
        camera = false;
        requires(shooter);
    }
    
    public Shoot(){
        camera = true;
        requires(shooter);
    }

    protected void initialize() {
        if(camera){
//            speed = PositionInfo.getSpeed(ShooterCamera.getLocation());
            speed = 0.73;
        }
        shooter.initEncoder();
        time = timeSinceInitialized();
//        shooter.initPID();
    }

    protected void execute() {
        if (timeSinceInitialized() - time > 2) shooter.setShootTrue();
        shooter.setJags(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
//        shooter.disablePID();
        shooter.stopJags();
        shooter.stopEncoder();
        shooter.setShootFalse();
    }

    protected void interrupted() {
        end();
    }
}
