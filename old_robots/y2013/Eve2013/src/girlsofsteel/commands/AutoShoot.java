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

    double desiredSpeed;
    double time;
    boolean shot;
    double batteryVoltage;
    DriverStation driver;
    int counter;

    public AutoShoot() {
        requires(feeder);
        requires(shooter);
        driver = DriverStation.getInstance();
        counter = 0;
    }

    protected void initialize() {
        desiredSpeed = OI.ENCODER_SPEED;
        shot = false;

    }

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

    protected boolean isFinished() {
        return shot && timeSinceInitialized() - time > 0.2;
    }

    protected void end() {
        feeder.pullShooter();
        shooter.setJags(0.0);
    }

    protected void interrupted() {
        end();
    }
}
