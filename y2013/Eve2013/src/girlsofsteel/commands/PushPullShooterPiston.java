/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.OI;

/**
 *
 * @author Sylvie
 * If used in a commandgroup, the entire commandgroup will become uninterruptible
 */
public class PushPullShooterPiston extends CommandBase {

    double desiredSpeed;
    double time;
    boolean shot = false;

    public PushPullShooterPiston() {
        requires(feeder);
        setInterruptible(false);
    }

    protected void initialize() {
        desiredSpeed = OI.ENCODER_SPEED;
        shot = false;
    }

    protected void execute() {
        SmartDashboard.putNumber("Encoder Rate", shooter.getEncoderRate());
        if(shooter.isTimeToShoot()){
            feeder.pushShooter();
            time = timeSinceInitialized();
            shot = true;
        }
    }

    protected boolean isFinished() {
        return shot && timeSinceInitialized() - time > 0.2;
    }

    protected void end() {
        feeder.pullShooter();
    }

    protected void interrupted() {
        end();
    }
}
