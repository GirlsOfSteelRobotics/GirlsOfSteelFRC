///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package girlsofsteel.commands;
//
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
///**
// *
// * @author Sonia and Alex
// */
//public class LightSensorFeeder extends CommandBase {
//    
//    boolean previousLight;
//    
//    //This command constantly counts the number of frisbees in the feeder, using a light sensor
//    
//    protected void initialize() {
//    }
//
//    public void execute() {
//        previousLight = false;
//        //If the light sensor sees a frisbee, the counter increases by one
//        if(!previousLight && feeder.getLightSensor()){
//            feeder.addToFrisbeeCounter();
//        }
//        previousLight = feeder.getLightSensor();
//       
//        //This prints # of frisbees to the driver station
//        //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, 
//                //"Number of Frisbees:" + feeder.getFrisbeeCounter());
//       
//       //Prints to smart dashboard
//       SmartDashboard.putNumber("Number of Frisbees", feeder.getFrisbeeCounter());
//    }
//
//    //This command should never end -> Should always be a counter
//    protected boolean isFinished() {
//        return false;
//    }
//
//    protected void end() {
//    }
//
//    protected void interrupted() {
//        end();
//    }
//    
//}