/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author Sylvie
 */
public class ShooterPID extends CommandBase{

    private double speed;
    private double p;
    private double i;
    private double encoderValue;

    public ShooterPID(){
        requires(shooter);
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        SmartDashboard.putNumber("Shooter Speed", 0.0);
        SmartDashboard.putNumber("p value", 0.0);
        SmartDashboard.putNumber("i value", 0.0);
        SmartDashboard.putBoolean("Click when Done Testing Shooter PID", false);
    }

    @Override
    protected void execute() {
        speed = SmartDashboard.getNumber("Shooter Speed", 0.0);
        p = SmartDashboard.getNumber("p value", 0.0);
        i = SmartDashboard.getNumber("i value", 0.0);

        shooter.setPIDValues(p, i, 0.0);
        shooter.setPIDspeed(speed);

        //print encoder value
        SmartDashboard.putNumber("Encoder Value",
                shooter.getEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return !SmartDashboard.getBoolean("Click when Done Testing Shooter PID", true);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
