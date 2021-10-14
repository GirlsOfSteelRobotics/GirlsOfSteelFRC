/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import girlsofsteel.commands.CommandBase;

/**
 *
 * @author sophia
 */
    public class ChassisJagsAndEncoders extends CommandBase {
    //copied from KiwiDrive code
    
    double speed;
    
    public ChassisJagsAndEncoders(){
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
        
        //tests to see if Jags goes to set speed
        double allowedError = 0.05;
        double desiredSpeed = 0.2;
        chassis.setRightJag(desiredSpeed);
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        speed = chassis.getRightEncoderRate();
        if (speed > desiredSpeed - allowedError && speed < desiredSpeed + allowedError) {
            System.out.println("Passed Right Jag speed test");
        }
//        System.out.println("R Distance:" + chassis.getRightEncoderDistance());
        //System.out.println("R Rate: " + chassis.getRightEncoderRate());
        desiredSpeed = 0.3;
        chassis.setBackJag(desiredSpeed);
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
//        System.out.println("B Distance:" + chassis.getBackEncoderDistance());
        //System.out.println("B Rate: " + chassis.getBackEncoderRate());
        speed = chassis.getBackEncoderRate();
        if (speed > desiredSpeed - allowedError && speed < desiredSpeed + allowedError) {
            System.out.println("Passed Back Jag speed test");
        }
        desiredSpeed = 0.4;
        chassis.setLeftJag(desiredSpeed);
        try {
                wait(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
//        System.out.println("L Distance:" + chassis.getLeftEncoderDistance());
        speed = chassis.getLeftEncoderRate();
        if (speed > desiredSpeed - allowedError && speed < desiredSpeed + allowedError) {
            System.out.println("Passed Left Jag speed test");
        }
    
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
