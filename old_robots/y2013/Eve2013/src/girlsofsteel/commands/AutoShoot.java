/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.OI;

/**
 *
 * @author user
 */
public class AutoShoot extends CommandBase {

    private double desiredSpeed;
    private double time;
    private boolean shot;
    private double batteryVoltage;
    private final DriverStation driver;
    private final int counter;

    public AutoShoot() {
        requires(feeder);
        requires(shooter);
        driver = DriverStation.getInstance();
        counter = 0;
    }

    @Override
    protected void initialize() {
        desiredSpeed = OI.ENCODER_SPEED;
        shot = false;

    }

    @Override
    protected void execute() {
        shooter.setJags(oi.JAG_SPEED);
        batteryVoltage = RobotController.getBatteryVoltage();
        SmartDashboard.putNumber("Encoder Rate", shooter.getEncoderRate());
        SmartDashboard.putNumber("Battery Voltage", batteryVoltage);
        if (shooter.getEncoderRate() >= desiredSpeed && !shot) {
            feeder.pushShooter();
            time = timeSinceInitialized();
            shot = true;
        }
        //if the battery lost voltage and is not going up to speed, it increases the speed
//        if (timeSinceInitialized() > 4 && batteryVoltage < 11.0 && oi.JAG_SPEED <= 0.95) {
//            oi.JAG_SPEED += 0.05;
//        }
    }

    @Override
    protected boolean isFinished() {
        return shot && timeSinceInitialized() - time > 0.2;
    }

    @Override
    protected void end() {
        feeder.pullShooter();
        shooter.setJags(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
