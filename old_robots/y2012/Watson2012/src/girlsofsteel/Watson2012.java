package girlsofsteel;

import girlsofsteel.commands.AutoTuneCamera;
import girlsofsteel.commands.Collect;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.commands.DriveSlowTurning;
import girlsofsteel.commands.TurretTrackTarget;
import girlsofsteel.objects.AutonomousChooser;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import girlsofsteel.commands.*;
import girlsofsteel.objects.Camera;

public class Watson2012 extends IterativeRobot {

//    Command buttons;
    private Command driveJagsLinear;
    private Command turretTracking;
    private Command collect;

    private AutonomousChooser auto;

    @Override
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

    @Override
    public void autonomousInit() {
        turretTracking.start();
        collect.start();
        auto.start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
    }

    @Override
    public void teleopInit() {
        //auto track is still on from autonomous -> will NOT autonomatically
        //start if you just start with teleop (like to test)
        collect.cancel();
        auto.cancel();
        driveJagsLinear.start();
//        buttons.start();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
        SmartDashboard.putNumber("Camera Distance", Camera.getXDistance());
        SmartDashboard.putNumber("Shooter Encoder", CommandBase.shooter.getEncoderRate());
    }

    @Override
    public void disabledPeriodic() {
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
        SmartDashboard.putNumber("Camera Distance", Camera.getXDistance());
        SmartDashboard.putNumber("Shooter Encoder", CommandBase.shooter.getEncoderRate());
    }
}
