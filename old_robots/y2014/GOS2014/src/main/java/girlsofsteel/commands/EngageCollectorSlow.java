///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package girlsofsteel.commands;
//
///**
// *
// * @author Sophia and Abby
// */
////This command moves arm wheel to apply continuous pressure to the ball.
//public class EngageCollectorSlow extends CommandBase {
//
//    public EngageCollectorSlow() {
//        requires(collector);
//    }
//
//    protected void initialize() {
//    }
//
//    protected void execute() {
//        //Keeps applying pressure if bottom limit switch isn't pressed
//        if (!collector.isCollectorEngaged()) {
//            collector.moveCollectorUpOrDown(-.1);
//        } else {
//            collector.stopCollector();
//        }
//    }
////moves the collector down to collect the ball but at a slower speed so it doesn't blow the motor up
//
//    protected boolean isFinished() {
//        return false;
//
//    }
//
//    protected void end() {
//        collector.stopCollector();
//    }
////stops the collector wheel so it doesn't keep spinning after the arm is applying pressure to the ball
//
//    protected void interrupted() {
//        end();
//    }
//
//}
