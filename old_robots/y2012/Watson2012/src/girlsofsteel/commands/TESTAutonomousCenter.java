package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTAutonomousCenter extends CommandGroup {

    boolean autoTrack;

    double distance = 1.3;

    double time;

    public TESTAutonomousCenter(){
        SmartDashboard.putBoolean("Auto Track?", false);
        autoTrack = SmartDashboard.getBoolean("Auto Track?", false);
        addSequential(new AutonomousKeyShootTwoBalls(autoTrack));
        /* inits shooter encoder & PID
         * gets distance from the camera
         * gets the velocity needed to shoot using that distance
         * sets the PID setpoint (speed) to that velocity
         * starts the top rollers, middle conveyor, & brush
         * when the PID is within 1.0 of the target velocity
         * ends after the magical value of shooting 2 balls (right now, set to 8)
         * stops the PID, rop rollers, brush, & middle conveyor
         */
//        addSequential(new MoveToSetPoint(distance));
////        //move 2.0meters towards the bridges
//        addParallel(new BridgeDownContinuous());
////        //push the bridge down
//        addParallel(new Collect());
////        //start collecting balls (turn on bottom 2 collectors)
//        addSequential(new ShootUsingTable());
        //shoot again -> using camera distance & the table
    }

    public void end(){
        new DisableChassis().start();
        new DisableShooter().start();
        new StopCollectors().start();
    }

    protected void interrupted() {
        end();
    }

}
