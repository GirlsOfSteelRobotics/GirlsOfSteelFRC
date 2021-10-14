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

    double speed;
    double p;
    double i;
    double encoderValue;

    public ShooterPID(){
        requires(shooter);
    }

    protected void initialize() {
        shooter.initEncoder();
        shooter.initPID();
        SmartDashboard.putNumber("Shooter Speed", 0.0);
        SmartDashboard.putNumber("p value", 0.0);
        SmartDashboard.putNumber("i value", 0.0);
        SmartDashboard.putBoolean("Click when Done Testing Shooter PID", false);
    }

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

    protected boolean isFinished() {
        return !SmartDashboard.getBoolean("Click when Done Testing Shooter PID", true);
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
