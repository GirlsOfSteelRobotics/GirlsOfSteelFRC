package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;
import girlsofsteel.subsystems.Turret;

public class AutonomousKeyShootTwoBalls extends CommandBase {

    private static final double timeToShootTwoBalls = 7.0;

    private final Collector m_collector;
    private final Shooter m_shooter;
    private final Turret m_turret;

    private final boolean m_autoTrack;

    public AutonomousKeyShootTwoBalls(Chassis chassis, Collector collector, Shooter shooter, Turret turret, boolean autoTrack){
        m_collector = collector;
        m_shooter = shooter;
        m_turret = turret;
        requires(m_shooter);
        requires(m_collector);
        requires(m_turret);
        requires(chassis);
        this.m_autoTrack = autoTrack;
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    protected void execute() {
        m_shooter.setPIDValues();
        if(m_autoTrack){
            m_turret.autoTrack();
        }
        double velocity = 24.0;
        m_shooter.setPIDSpeed(velocity);
        if(m_shooter.isWithinSetPoint(velocity)){
            m_shooter.topRollersForward();
            m_collector.forwardBrush();
            m_collector.forwardMiddleConveyor();
        }
    }

    @Override
    protected boolean isFinished() {
        return timeSinceInitialized() > timeToShootTwoBalls;
    }

    @Override
    protected void end() {
        m_shooter.disablePID();
        m_shooter.topRollersOff();
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
