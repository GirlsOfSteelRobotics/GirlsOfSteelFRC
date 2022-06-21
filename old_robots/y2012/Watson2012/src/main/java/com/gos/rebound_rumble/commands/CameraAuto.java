//package com.gos.girlsofsteel.commands;
//
//import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//
//public class CameraAuto extends SequentialCommandGroup {
//
//    public static int NumberOfCameraDataPoints = 11;
//    public static int count = 0;
//    public static double[] imageTargetRatioData = new double[NumberOfCameraDataPoints];
//    public static double[] distanceData = new double[NumberOfCameraDataPoints];
//
//
//    public CameraAuto() {
//
//            addCommands (new ResetEncoder());
//        for (int x = 0; x < NumberOfCameraDataPoints; x++) {
//           // count = x;
//
//            addCommands(new FindCameraDataPoint());
//            //addCommands(new MoveToSetPoint(-1));
//            System.out.println("DONE MOVING!");
//
//        }
//        addCommands(new FindCameraFunction());
//    }
//}
