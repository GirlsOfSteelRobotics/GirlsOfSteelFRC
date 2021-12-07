package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTGyro extends CommandBase {

    public TESTGyro(){
        requires(chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Gyro Angle",chassis.getTheta());
        SmartDashboard.putNumber("Turret Encoder Angle",turret.getEncoderDistance());
        SmartDashboard.putNumber("Turret Angle (Summation)", turret.getTurretAngle());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
