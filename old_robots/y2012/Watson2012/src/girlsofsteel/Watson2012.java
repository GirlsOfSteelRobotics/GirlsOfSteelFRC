package girlsofsteel;

import girlsofsteel.objects.AutonomousChooser;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.*;
import girlsofsteel.objects.Camera;

public class Watson2012 extends IterativeRobot {

//    Command buttons;
    Command driveJagsLinear;
    Command turretTracking;
    Command collect;

    AutonomousChooser auto;

    public void robotInit() {
        // Initialize all subsystems
        CommandBase.init();
        SmartDashboard.putBoolean("Reset Netbook", true);
        SmartDashboard.putData(new AutoTuneCamera());
        auto = new AutonomousChooser();
        driveJagsLinear = new DriveSlowTurning(1.0, 0.5);
        turretTracking = new TurretTrackTarget();
        collect = new Collect();

        SmartDashboard.putData(Scheduler.getInstance());

//        buttons = new Buttons();//runs different commands based on the physical buttons/switches
    }

    public void autonomousInit() {
        turretTracking.start();
        collect.start();
        auto.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
    }

    public void teleopInit() {
        //auto track is still on from autonomous -> will NOT autonomatically
        //start if you just start with teleop (like to test)
        collect.cancel();
        auto.cancel();
        driveJagsLinear.start();
//        buttons.start();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
        SmartDashboard.putNumber("Camera Distance", Camera.getXDistance());
        SmartDashboard.putNumber("Shooter Encoder", CommandBase.shooter.getEncoderRate());
    }

    public void disabledPeriodic() {
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
        SmartDashboard.putNumber("Camera Distance", Camera.getXDistance());
        SmartDashboard.putNumber("Shooter Encoder", CommandBase.shooter.getEncoderRate());
    }
}
