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

    private double desiredSpeed;
    private double time;
    private boolean shot;

    public PushPullShooterPiston() {
        requires(feeder);
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        desiredSpeed = OI.ENCODER_SPEED;
        shot = false;
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Encoder Rate", shooter.getEncoderRate());
        if(shooter.isTimeToShoot()){
            feeder.pushShooter();
            time = timeSinceInitialized();
            shot = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return shot && timeSinceInitialized() - time > 0.2;
    }

    @Override
    protected void end() {
        feeder.pullShooter();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
