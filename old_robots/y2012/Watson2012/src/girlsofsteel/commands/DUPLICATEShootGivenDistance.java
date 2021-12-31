package girlsofsteel.commands;

import girlsofsteel.subsystems.Shooter;

public class DUPLICATEShootGivenDistance extends CommandBase {
    private final Shooter m_shooter;
    private final double m_speed;

    public DUPLICATEShootGivenDistance(Shooter shooter, double speed) {
        m_shooter = shooter;
        requires(m_shooter);
        this.m_speed = speed;
    }

    @Override
    protected void initialize() {
        m_shooter.initPID();
        m_shooter.initEncoder();
        System.out.println("Shooting initialized");
    }

    @Override
    protected void execute() {
        m_shooter.shoot(m_speed);

    }

    @Override
    protected boolean isFinished() {
        //If this is bridge shooting, wait a long time. Otherwise(key shooting) don't wait a long time
        if (m_speed > 25) {
            return timeSinceInitialized() > 10;
        } else {
            return timeSinceInitialized() > 5;
        }
    }

    @Override
    protected void end() {
        System.out.println("Shooting Finished");
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
