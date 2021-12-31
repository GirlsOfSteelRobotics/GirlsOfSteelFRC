/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Feeder;
import girlsofsteel.subsystems.Shooter;

/**
 * @author user
 */
public class AutoShoot extends CommandBase {

    private final Feeder m_feeder;
    private final Shooter m_shooter;

    private double m_desiredSpeed;
    private double m_time;
    private boolean m_shot;
    private double m_batteryVoltage;

    public AutoShoot(Feeder feeder, Shooter shooter) {
        m_feeder = feeder;
        m_shooter = shooter;
        requires(m_feeder);
        requires(m_shooter);
    }

    @Override
    protected void initialize() {
        m_desiredSpeed = OI.ENCODER_SPEED;
        m_shot = false;

    }

    @Override
    protected void execute() {
        m_shooter.setJags(OI.JAG_SPEED);
        m_batteryVoltage = RobotController.getBatteryVoltage();
        SmartDashboard.putNumber("Encoder Rate", m_shooter.getEncoderRate());
        SmartDashboard.putNumber("Battery Voltage", m_batteryVoltage);
        if (m_shooter.getEncoderRate() >= m_desiredSpeed && !m_shot) {
            m_feeder.pushShooter();
            m_time = timeSinceInitialized();
            m_shot = true;
        }
        //if the battery lost voltage and is not going up to speed, it increases the speed
//        if (timeSinceInitialized() > 4 && batteryVoltage < 11.0 && oi.JAG_SPEED <= 0.95) {
//            oi.JAG_SPEED += 0.05;
//        }
    }

    @Override
    protected boolean isFinished() {
        return m_shot && timeSinceInitialized() - m_time > 0.2;
    }

    @Override
    protected void end() {
        m_feeder.pullShooter();
        m_shooter.setJags(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
