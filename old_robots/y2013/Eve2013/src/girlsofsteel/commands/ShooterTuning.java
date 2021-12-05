/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * Make shoot method that given a desired encoder speed and current battery voltage,
 * it will set the jags to a suitable speed from the table. Also be prepared to
 * extrapolate values (see code from last year).
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Sylvie
 */
public class ShooterTuning extends CommandBase {

    private double batteryVoltage;
    private final DriverStation driver;
    private int counter;
    private boolean done;
    private double speed = 0.0;
    private double time;

    public ShooterTuning() {
        requires(shooter);
        driver = DriverStation.getInstance();
    }

    @Override
    protected void initialize() {
        SmartDashboard.putNumber("speed", 0.0);
        SmartDashboard.putBoolean("test speed", false);
    }

    @Override
    protected void execute() {
        if (SmartDashboard.getBoolean("test speed", false)) {
            batteryVoltage = RobotController.getBatteryVoltage();
            speed = SmartDashboard.getNumber("speed", 0.0);
            time = timeSinceInitialized();
            shooter.setJags(speed);
            if (timeSinceInitialized() - time > 3) {
                shooter.fillArray(speed, shooter.getEncoderRate(), batteryVoltage);
                time = timeSinceInitialized();
                counter++;
            }
            //arbitrary number
            if (counter == 25) {
                done = true;
            }
        }
                System.out.println("Point Voltage: " + speed + "\t");
                System.out.print("Point Shooter Encoder Speed: " + shooter.getEncoderRate());
                System.out.println("Point Battery Voltage: " + batteryVoltage);

    }

    @Override
    protected boolean isFinished() {
        return done;
    }

    @Override
    protected void end() {
     shooter.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
