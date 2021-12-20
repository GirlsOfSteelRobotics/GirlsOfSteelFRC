package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Shooter;


public class TestShooter extends CommandBase {

    private final Shooter m_shooter;
    private double m_speed;

    public TestShooter(Shooter shooter){
        m_shooter = shooter;
        SmartDashboard.putNumber("Speed", 0.0);
        SmartDashboard.putBoolean("Shoot", false);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_speed = SmartDashboard.getNumber("Speed",0.0);
        if(SmartDashboard.getBoolean("Shoot", false)){
            m_shooter.setJags(m_speed);
        }
        else{
            m_shooter.stopJags();
            m_shooter.setShootFalse();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_shooter.stopJags();
        m_shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
