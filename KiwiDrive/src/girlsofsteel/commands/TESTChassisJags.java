package girlsofsteel.commands;

public class TESTChassisJags extends CommandBase {
    
    double speed;
    
    public TESTChassisJags(){
        requires(chassis);
//        SmartDashboard.putBoolean("Right Jag", false);
//        SmartDashboard.putBoolean("Back Jag", false);
//        SmartDashboard.putBoolean("Left Jag", false);
//        SmartDashboard.putNumber("Jag Speed", 0.0);
    }
    
    protected void initialize() {
    }

    protected void execute() {
//        speed = SmartDashboard.getNumber("Jag speed", 0.0);
//        if(SmartDashboard.getBoolean("Right Jag", false))
//            chassis.setRightJag(speed);*
//        if(SmartDashboard.getBoolean("Back Jag", false))
//            chassis.setBackJag(speed);
//        if(SmartDashboard.getBoolean("Left Jag", false))
//            chassis.setLeftJag(speed);
        chassis.setRightJag(0.5);
        chassis.setBackJag(0.5);
        chassis.setLeftJag(0.5);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}