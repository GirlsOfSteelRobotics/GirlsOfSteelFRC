package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.objects.Camera;

public class ShootUsingTable extends CommandBase {

    Joystick operatorJoystick;

    boolean bank;//bank does not do anything -> tuned for banking
    double cameraDistance;

    public ShootUsingTable(boolean bank) { //bank = false for autonomous
        //bank = true for everything else (should be fairly straight to the hoop
        requires(shooter);
        this.bank = bank;
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        operatorJoystick = oi.getOperatorJoystick();
        cameraDistance = Camera.getXDistance()/* - 38*(0.0254/1.0)*/;
        //why subtract the fender? we add the fender into the calculations for
        //shooter data table
    }

    protected void execute() {
//        if(bank){
//            shooter.autoShootBank(cameraDistance);
//        }else{
            shooter.autoShoot(cameraDistance);
//        }
        if(Math.abs(operatorJoystick.getThrottle()) >= 0.3 ||
                Math.abs(operatorJoystick.getTwist()) >= 0.3){
            shooter.topRollersForward();
        }
        System.out.println("Encoder Values:" + shooter.getEncoderRate());
    }

    protected boolean isFinished() {
        return !Camera.isConnected(); //return opposite so when it is connected
        //it returns false so it does NOT finish
    }

    protected void end() {
        if(!oi.areTopRollersOverriden()){
            shooter.topRollersOff();
        }
        shooter.disablePID();
        shooter.stopEncoder();
    }

    protected void interrupted() {
        end();
    }
}
