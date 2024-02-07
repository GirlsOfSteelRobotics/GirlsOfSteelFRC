package com.gos.rebound_rumble.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Chassis;


/*
 * for the chassis -> you cannot control the robot with the joystick as well as
 * the PID controller (which needs to be initialized if using the move method)
 * right now I commented out the move & the PID
 */
public class ManualTuneCamera extends GosCommandBase {

    private final Chassis m_chassis;

    public static final double HALF_COURT = 7.1; //meaters (7.1)
    public static final double STEP = .5; //meaters
    public static final double ERROR_THRESHOLD = .01;
    private int m_count;
    private double[] m_imageTargetRatioData = new double[50];
    private double[] m_distanceData = new double[50];


    public ManualTuneCamera(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        SmartDashboard.putBoolean("collect data", false);
    }

    //Returns average of a set of 10 data points from the camera if it's stable.
    private double getStableRatio() {
        int max = 10;
        double sumOfData = 0;
        double[] values = new double[max];
        for (int i = 0; i < max; i++) { // NOPMD(AvoidArrayLoops)
            values[i] = Camera.getImageTargetRatio();
            //System.out.println(values[i]);
            sumOfData += values[i];

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace(); // NOPMD
            }
        }
        double dataAverage = (sumOfData / max);
        double errorSum = 0;
        for (int i = 0; i < max; i++) {
            errorSum += (values[i] - dataAverage) * (values[i] - dataAverage);
        }
        double averageError = Math.sqrt(errorSum) / max;
        if (averageError < ERROR_THRESHOLD) {
            // System.out.println(dataAverage);
            return dataAverage;
        }
        return -1; //-1 means that it can't use the data.
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        //        chassis.initPositionPIDs();
    }

    @SuppressWarnings("PMD")
    @Override
    public void execute() {
        //xAxis = driverJoystick.getX() * 0.5;
        //yAxis = driverJoystick.getY() * 0.5;
        //chassis.driveJagsLinear(xAxis, yAxis);
        //        if (oi.isCollectCameraDataPressed()) {
        if (SmartDashboard.getBoolean("collect data", false)) {
            //for (int n = 0; n < 5; n++) {
            if (!m_chassis.isMoving()) {

                try {
                    Thread.sleep(2000); //3 seconds time out.
                } catch (Exception ex) {
                    ex.printStackTrace(); // NOPMD
                }

                double ratio = 0;

                //Try 10 times for an aceptable average.
                for (int i = 0; i < 10; i++) {
                    ratio = this.getStableRatio();
                    System.out.println("ratio=" + ratio);
                    if (ratio > 0) {
                        break;
                    }
                }

                m_distanceData[m_count] = (m_chassis.getRightEncoderDistance());
                System.out.println("collected one data point " + m_distanceData[m_count] + ", " + ratio);
                //Start against the bridge. distance to target is he initial distance minus the distance travled.
                //                chassis.move(Step);

                if (ratio >= 0) {
                    m_imageTargetRatioData[m_count] = ratio;
                    m_distanceData[m_count] = (m_chassis.getRightEncoderDistance());
                    System.out.println("collected one data point " + m_distanceData[m_count] + ", " + ratio);

                }

            }

            if (m_count > 50) {
                m_count = 49;
            } else {
                m_count++;
            }

        }
    }

    @Override
    public boolean isFinished() {
        return (m_chassis.getRightEncoderDistance()) > HALF_COURT - 2;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("data collection done! cnt=" + m_count);
        double[] ab = LineReg.bestFit(m_imageTargetRatioData, m_distanceData, m_count + 1);
        double a = ab[0];
        double b = ab[1];

        if (!Double.isNaN(a)) {
            NetworkTable table = NetworkTableInstance.getDefault().getTable("camera");
            table.getEntry("Slope").setNumber(a);
            table.getEntry("yInt").setNumber(b);
            // print results
            System.out.println("y   = " + a + " * x + " + b);
        }

        m_chassis.stopJags();
        m_chassis.endEncoders();
    }




    private static class LineReg {

        public static double[] bestFit(double[] x, double[] y, int size) {
            int n = 0;

            // first pass: read in data, compute xbar and ybar
            double sumx = 0.0;
            double sumy = 0.0;
            double sumx2 = 0.0;
            for (; n < size; ++n) {
                sumx += x[n];
                sumx2 += x[n] * x[n];
                sumy += y[n];
                n++;
            }
            double xbar = sumx / n;
            double ybar = sumy / n;

            // second pass: compute summary statistics
            double xxbar = 0.0;
            double yybar = 0.0;
            double xybar = 0.0;
            for (int i = 0; i < n; i++) {
                xxbar += (x[i] - xbar) * (x[i] - xbar);
                yybar += (y[i] - ybar) * (y[i] - ybar);
                xybar += (x[i] - xbar) * (y[i] - ybar);
            }
            double beta1 = xybar / xxbar;
            double beta0 = ybar - beta1 * xbar;

            // print results
            // System.out.println("y   = " + beta1 + " * x + " + beta0);
            double[] aAndB = new double[2];
            aAndB[0] = beta1; // a in: aX + b
            aAndB[1] = beta0; // b in: aX + b
            return aAndB;
        }
    }
}
