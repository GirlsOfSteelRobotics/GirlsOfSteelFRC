//package girlsofsteel.commands;
//
//import edu.wpi.first.wpilibj.command.CommandGroup;
//
//public class CameraAuto extends CommandGroup {
//
//    public static int NumberOfCameraDataPoints = 11;
//    public static int count = 0;
//    public static double[] imageTargetRatioData = new double[NumberOfCameraDataPoints];
//    public static double[] distanceData = new double[NumberOfCameraDataPoints];
//
//    
//    public CameraAuto() {
//        
//            addSequential (new ResetEncoder());
//        for (int x = 0; x < NumberOfCameraDataPoints; x++) {
//           // count = x;
//            
//            addSequential(new FindCameraDataPoint());
//            //addSequential(new MoveToSetPoint(-1));
//            System.out.println("DONE MOVING!");
//            
//        }
//        addSequential(new FindCameraFunction());
//    }
//}