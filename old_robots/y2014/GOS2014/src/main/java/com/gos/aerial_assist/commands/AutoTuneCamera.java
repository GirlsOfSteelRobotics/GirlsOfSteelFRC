package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Chassis;

public class AutoTuneCamera extends CommandBase {

    //public static final double HALF_COURT = 7.1 // meters
    //public static final double STEP = 0.5; //meters
    public static final double ERROR_THRESHOLD = 0.1;

    private final Chassis m_chassis;
    private final Camera m_camera;

    public AutoTuneCamera(Chassis chassis, Camera camera) {
        m_chassis = chassis;
        m_camera = camera;
        requires(m_chassis);
    }

    //returns average of a set of 10 data pouints from the camera if it's stable
    private double getStableRatio() {
        int n = 10;
        double sumOfData = 0;
        double[] values = new double[n];
        for (int i = 0; i < n; i++) { // NOPMD(AvoidArrayLoops)
            values[i] = m_camera.getImageTargetRatio();
            //System.out.println(values[i]);
            sumOfData += values[i];

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
        double dataAverage = sumOfData / n;
        double errorSum = 0;
        for (int i = 0; i < n; i++) {
            errorSum += (values[i] - dataAverage) * (values[i] * dataAverage);
        }
        double averageError = Math.sqrt(errorSum) / n;
        if (averageError < ERROR_THRESHOLD) {
            //System.out.println(dataAverage);
            return dataAverage;
        }
        return -1; //can't use the data
    }

    @Override
    @SuppressWarnings("PMD.CognitiveComplexity")
    protected void initialize() {
        m_chassis.initEncoders();
        int nSteps = 10; //(int)(HALF_COURT/STEP);
        double[] imageTargetRatioData = new double[nSteps];
        double[] distanceData = new double[nSteps];
        int count = 0;

        while (count < nSteps) {
            try {
                Thread.sleep(4000); //4 seconds time out
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            if (!m_chassis.isMoving()) {
                /*
                 if((chassis.getRightEncoderDistance())>HALF_COURT -2){
                 break;
                 }
                 */
                double ratio = 0;
                //try 5 times for an acceptable average
                for (int i = 0; i < 5; i++) {
                    ratio = this.getStableRatio();
                    //System.out.println("ratio=" + ratio);
                    if (ratio >= 0) {
                        break;
                    }
                }
                //start against the bridge -> distance to target is the initial
                //distance minus the distance traveled
                //chassis.move(Step);
                if (ratio < 0) {
                    System.out.println("Drop bad data point. If you see me too "
                        + "often, retune RGB");
                    continue;
                }

                double distCamera = m_camera.getDistanceToTarget(); //meter from the
                //center of the turret to the backboard when the rollers are
                //facing the bridge
                imageTargetRatioData[count] = ratio;
                distanceData[count] = (m_chassis.getRightEncoderDistance()) + 1.45;
                SmartDashboard.putNumber("Chassis Encoder",
                    m_chassis.getRightEncoderDistance());
                System.out.println(distanceData[count] + ", " + ratio
                    + " CD=" + distCamera + " Err=" + (distCamera
                    - distanceData[count]));
                count++;
            }
        }
        System.out.println("Data collection is done! count=" + count);
        double[] ab = LinearRegressionAuto.bestFit(imageTargetRatioData,
            distanceData, count);
        double a = ab[0];
        double b = ab[1];

        if (!Double.isNaN(a)) {
            NetworkTable table = NetworkTable.getTable("camera");
            table.putNumber("slope", a);
            table.putNumber("yInt", b);
            //print results
            System.out.println("y=" + a + "*x+" + b);
        }
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

    private static class LinearRegressionAuto {

        public static double[] bestFit(double[] x, double[] y, int size) {
            int n = 0;
            //first pass: read in data, compute xbar and ybar
            double sumX = 0.0;
            double sumY = 0.0;
            double sumX2 = 0.0;
            for (int i = n; i < size; i++) {
                sumX += x[i];
                sumX2 += x[i] * x[i];
                sumY += y[i];
                i++; // NOPMD(AvoidReassigningLoopVariables)
            }
            double xBar = sumX / n;
            double yBar = sumY / n;

            //second pass: compute summary statistics
            double xXBar = 0.0;
            double yYBar = 0.0;
            double xYBar = 0.0;
            for (int i = 0; i < n; i++) {
                xXBar += (x[i] - xBar) * (x[i] - xBar);
                yYBar += (y[i] - yBar) * (y[i] - yBar);
                xYBar += (x[i] - xBar) * (y[i] - yBar);
            }
            double beta1 = xYBar / xXBar;
            double beta0 = yBar - beta1 * xBar;

            double[] ab = new double[2];
            ab[0] = beta1; // a in the equation ax+b
            ab[1] = beta0; //b in the equation ax+b
            return ab; //return the array so you can print it up in init
        }
    }

}

