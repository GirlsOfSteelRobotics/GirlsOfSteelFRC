package girlsofsteel.commands;

public class TESTChassisJagsAndEncoders extends CommandBase {
    
    double speed;
    
    public TESTChassisJagsAndEncoders(){
        requires(chassis);
//        SmartDashboard.putBoolean("Right Jag", false);
//        SmartDashboard.putBoolean("Back Jag", false);
//        SmartDashboard.putBoolean("Left Jag", false);
//        SmartDashboard.putNumber("Jag speed", 0.0);
//        SmartDashboard.putNumber("Right Encoder:", chassis.getRightEncoderDistance());
//        SmartDashboard.putNumber("Back Encoder:", chassis.getBackEncoderDistance());
//        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoderDistance());
    }
    
    protected void initialize() {
        chassis.initEncoders();
    }

    protected void execute() {
//        speed = SmartDashboard.getNumber("Jag speed", 0.0);
//        SmartDashboard.putNumber("Right Encoder", chassis.getRightEncoderDistance());
//        SmartDashboard.putNumber("Back Encoder:", chassis.getBackEncoderDistance());
//        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoderDistance());
//        if(SmartDashboard.getBoolean("Right Jag", false)){
//            chassis.setRightJag(speed);
//        }
//        if(SmartDashboard.getBoolean("Back Jag", false)){
//            chassis.setBackJag(speed);
//        }
//        if(SmartDashboard.getBoolean("Left Jag", false)){
//            chassis.setLeftJag(speed);
//        }        
        chassis.setRightJag(0.2);
//        System.out.println("R Distance:" + chassis.getRightEncoderDistance());
        System.out.println("R Rate: " + chassis.getRightEncoderRate());
        chassis.setBackJag(0.2);
//        System.out.println("B Distance:" + chassis.getBackEncoderDistance());
        System.out.println("B Rate: " + chassis.getBackEncoderRate());
        chassis.setLeftJag(0.2);
//        System.out.println("L Distance:" + chassis.getLeftEncoderDistance());
        System.out.println("L Rate: " + chassis.getLeftEncoderRate());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
        chassis.stopEncoders();
    }

    protected void interrupted() {
        end();
    }
    
}