package girlsofsteel;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.AutoTuneCamera;
import girlsofsteel.commands.Collect;
import girlsofsteel.commands.DriveSlowTurning;
import girlsofsteel.commands.TurretTrackTarget;
import girlsofsteel.objects.AutonomousChooser;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;
import girlsofsteel.subsystems.Turret;

public class Watson2012 extends IterativeRobot {


    private final OI m_oi;
    // Create a single static instance of all of your subsystems
    private final Bridge m_bridge;
    private final Chassis m_chassis;
    private final Collector m_collector;
    private final Shooter m_shooter;
    private final Turret m_turret;

    //    Command buttons;
    private final Command m_driveJagsLinear;
    private final Command m_turretTracking;
    private final Command m_collect;

    private final AutonomousChooser m_auto;

    public Watson2012() {

        m_bridge = new Bridge();
        m_chassis = new Chassis();
        m_collector = new Collector();
        m_shooter = new Shooter();
        m_turret = new Turret(m_chassis);
        m_oi = new OI(m_chassis, m_shooter, m_collector, m_turret, m_bridge);

        m_auto = new AutonomousChooser(m_oi, m_shooter, m_chassis, m_bridge, m_collector);
        m_driveJagsLinear = new DriveSlowTurning(m_chassis, m_oi.getDriverJoystick(), 1.0, 0.5);
        m_turretTracking = new TurretTrackTarget(m_turret, m_oi.getOperatorJoystick());
        m_collect = new Collect(m_collector);
    }

    @Override
    public void robotInit() {
        // Initialize all subsystems
        SmartDashboard.putBoolean("Reset Netbook", true);
        SmartDashboard.putData(new AutoTuneCamera(m_chassis));

        SmartDashboard.putData(Scheduler.getInstance());

//        buttons = new Buttons();//runs different commands based on the physical buttons/switches
    }

    @Override
    public void autonomousInit() {
        m_turretTracking.start();
        m_collect.start();
        m_auto.start();
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
        m_collect.cancel();
        m_auto.cancel();
        m_driveJagsLinear.start();
//        buttons.start();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
        SmartDashboard.putNumber("Camera Distance", Camera.getXDistance());
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
    }

    @Override
    public void disabledPeriodic() {
        SmartDashboard.putBoolean("Camera is connected?", Camera.isConnected());
        SmartDashboard.putBoolean("Target is found?", Camera.foundTarget());
        SmartDashboard.putNumber("Camera Distance", Camera.getXDistance());
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
    }
}
