package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;
import girlsofsteel.subsystems.Turret;

public class TESTAutonomousShootTwoBalls extends CommandBase {

    private final Shooter m_shooter;
    private final Collector m_collector;

    private double m_timeToShootTwoBalls;

    public TESTAutonomousShootTwoBalls(Chassis chassis, Bridge bridge, Shooter shooter, Collector collector, Turret turret) {

        m_shooter = shooter;
        m_collector = collector;

        SmartDashboard.putNumber("ASTB,speed", 0.0);
        SmartDashboard.putNumber("ASTB,time", 0.0);
        requires(m_shooter);
        requires(chassis);
        requires(m_collector);
        requires(turret);
        requires(bridge);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    protected void execute() {
        m_timeToShootTwoBalls = SmartDashboard.getNumber("ASTB,time", 0.0);
        double velocity = SmartDashboard.getNumber("ASTB,speed", 0.0);
//        velocity = shooter.getBallVelocityFrTable(xDistance);
//        this.ballVelocity = shooter.getImmobileBallVelocity(xDistance);
        m_shooter.setPIDSpeed(velocity);
        if (/*shooter.isWithinSetPoint(this.ballVelocity,range) &&*/ timeSinceInitialized() > 1.0) {
            m_shooter.topRollersForward();
//            collector.forwardBrush();
//            collector.forwardMiddleConveyor();
            m_collector.reverseBrush();
            m_collector.reverseMiddleConveyor();
        }
    }

    @Override
    protected boolean isFinished() {
        return timeSinceInitialized() > m_timeToShootTwoBalls;
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
