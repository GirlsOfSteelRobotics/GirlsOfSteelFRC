///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package girlsofsteel.commands;
//
//import edu.wpi.first.wpilibj.Joystick;
//
///**
// *
// * @author Sylvie
// */
//public class CollectorArmZAxis extends CommandBase {
//
//    Joystick operator;
//
//    public CollectorArmZAxis() {
//        requires(collector);
//        operator = oi.getOperatorJoystick();
//    }
//
//    protected void initialize() {
//    }
//
//    protected void execute() {
//        double jagSpeed = -operator.getThrottle();
//        if(jagSpeed < 0 && collector.isCollectorEngaged())
//            jagSpeed = 0;
//        else if(jagSpeed > 0 && collector.isCollectorDisengaged())
//            jagSpeed = 0;
//        collector.moveCollectorToZJoystick(jagSpeed);
//        //collector.moveCollectorToZJoystick(-operator.getThrottle());//check
//    }
//
//    protected boolean isFinished() {
//        return false;
//    }
//
//    protected void end() {
//        collector.stopCollector();
//    }
//
//    protected void interrupted() {
//        end();
//    }
//
//}
