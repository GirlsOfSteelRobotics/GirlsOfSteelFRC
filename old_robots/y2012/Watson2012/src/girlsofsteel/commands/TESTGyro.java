package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTGyro extends CommandBase {

    public TESTGyro(){
        requires(chassis);
    }

    protected void initialize() {
    }

    protected void execute() {
        SmartDashboard.putNumber("Gyro Angle",chassis.getTheta());
        SmartDashboard.putNumber("Turret Encoder Angle",turret.getEncoderDistance());
        SmartDashboard.putNumber("Turret Angle (Summation)", turret.getTurretAngle());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
