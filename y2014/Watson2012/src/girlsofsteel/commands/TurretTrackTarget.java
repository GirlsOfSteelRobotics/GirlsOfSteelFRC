package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.objects.Camera;

public class TurretTrackTarget extends CommandBase {

    Joystick operatorJoystick;
    
    double difference; //How much the driver wants it to move
    
    public TurretTrackTarget() {
        requires(turret);
    }

    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
        operatorJoystick = oi.getOperatorJoystick();
    }

    protected void execute() {
        turret.changeTurretOffset();
        if (Camera.foundTarget()) {
            turret.autoTrack();
            System.out.println("Turret Angle:  " + turret.getTurretAngle());
        }else{
            difference = operatorJoystick.getX()*5.0;
            if(difference < -2.0 || difference > 2.0){
                turret.setPIDSetPoint(turret.getTurretAngle() + difference);
            }
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        turret.disablePID();
    }

    protected void interrupted() {
        end();
    }
}
