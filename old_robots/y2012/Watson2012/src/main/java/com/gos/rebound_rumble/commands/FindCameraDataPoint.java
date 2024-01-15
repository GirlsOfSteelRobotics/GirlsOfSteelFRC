//package com.gos.girlsofsteel.commands;
//
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//import com.gos.girlsofsteel.objects.Camera;
//
//public class FindCameraDataPoint extends Command {
//
//    public static final double ErrorThreshold = .01;
//
//    //Returns average of a set of 10 data points from the camera if it's stable.
//    private double getStableRatio() {
//        int n = 10;
//        double sumOfData = 0;
//        double[] values = new double[n];
//        for (int i = 0; i < n; i++) {
//            values[i] = Camera.getImageTargetRatio();
//            //System.out.println(values[i]);
//            sumOfData += values[i];
//
//            try {
//                Thread.sleep(50);
//            } catch (Exception ex) {
//            }
//        }
//        double dataAverage = (sumOfData / n);
//        double errorSum = 0;
//        for (int i = 0; i < n; i++) {
//            errorSum += (values[i] - dataAverage) * (values[i] - dataAverage);
//        }
//        double averageError = Math.sqrt(errorSum) / n;
//        if (averageError < ErrorThreshold) {
//            // System.out.println(dataAverage);
//            return dataAverage;
//        }
//        return -1; //-1 means that it can't use the data.
//    }
//
//    protected void initialize() {
//
//        //Start against the bridge. distance to target is he initial distance minus the distance travled.
//        if (!chassis.isMoving()) {
//
//            double ratio = this.getStableRatio();
//
//            if (ratio == -1) {
//                System.out.println("bad data point! If you see me too often, re-tune RGB.");
//            } else {
//
//                CameraAuto.imageTargetRatioData[CameraAuto.count] = ratio;
//                CameraAuto.distanceData[CameraAuto.count] = (chassis.getRightEncoderDistance()) + 1.4;
//
//
//                double distCamera = Camera.getXDistance(); // - (38 * 0.0254); // convert inches to meters
//                System.out.println((CameraAuto.distanceData[CameraAuto.count]) + ", " + (distCamera)+ ", " + ratio);
//            }
//            try {
//                Thread.sleep(2000);  //sleeping for someone to move the robot backwards.
//            } catch (Exception ex) {
//            }
//            System.out.println("sleep worked");
//            System.out.println("count = " + CameraAuto.count);
//            CameraAuto.count ++;
//        }
//    }
//
//    protected void execute() {
//    }
//
//    protected boolean isFinished() {
//        return true;
//    }
//
//    protected void end() {
//    }
//
//    protected void interrupted() {
//        end();
//    }
//}
