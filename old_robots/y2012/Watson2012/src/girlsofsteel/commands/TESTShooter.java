package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTShooter extends CommandBase {

    private double speed;

    public TESTShooter(){
        SmartDashboard.putNumber("Shooter Jag Speed", 0.0);
    }

    @Override
    protected void initialize() {
        shooter.initEncoder();
    }

    @Override
    protected void execute() {
        speed = SmartDashboard.getNumber("Shooter Jag Speed", 0.0);
        shooter.setJags(speed);
        SmartDashboard.putNumber("Shooter Encoder", shooter.getEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.stopJags();
        shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
