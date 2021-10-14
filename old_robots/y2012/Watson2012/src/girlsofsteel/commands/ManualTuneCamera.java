package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.objects.Camera;


/*
 * for the chassis -> you cannot control the robot with the joystick as well as
 * the PID controller (which needs to be initialized if using the move method)
 * right now I commented out the move & the PID
 */
public class ManualTuneCamera extends CommandBase {

    Joystick driverJoystick;
    double xAxis;
    double yAxis;
    public static final double HalfCourt = 7.1; //meaters (7.1)
    public static final double Step = .5; //meaters
    public static final double ErrorThreshold = .01;
    int count = 0;
    double[] imageTargetRatioData = new double[50];
    double[] distanceData = new double[50];

    public ManualTuneCamera() {
        requires(chassis);
        SmartDashboard.putBoolean("collect data", false);
    }

    //Returns average of a set of 10 data points from the camera if it's stable.
    private double getStableRatio() {
        int max = 10;
        double sumOfData = 0;
        double[] values = new double[max];
        for (int i = 0; i < max; i++) {
            values[i] = Camera.getImageTargetRatio();
            //System.out.println(values[i]);
            sumOfData += values[i];

            try {
                Thread.sleep(50);
            } catch (Exception ex) {
            }
        }
        double dataAverage = (sumOfData / max);
        double errorSum = 0;
        for (int i = 0; i < max; i++) {
            errorSum += (values[i] - dataAverage) * (values[i] - dataAverage);
        }
        double averageError = Math.sqrt(errorSum) / max;
        if (averageError < ErrorThreshold) {
            // System.out.println(dataAverage);
            return dataAverage;
        }
        return -1;//-1 means that it can't use the data.
    }

    protected void initialize() {
        chassis.initEncoders();
//        chassis.initPositionPIDs();
        driverJoystick = oi.getDriverJoystick();
    }

    protected void execute() {
        //xAxis = driverJoystick.getX() * 0.5;
        //yAxis = driverJoystick.getY() * 0.5;
        //chassis.driveJagsLinear(xAxis, yAxis);
//        if (oi.isCollectCameraDataPressed()) {
        if (SmartDashboard.getBoolean("collect data", false)) {
            //for (int n = 0; n < 5; n++) {
            if (!chassis.isMoving()) {
                
                try {
                    Thread.sleep(2000);//3 seconds time out. 
                } catch (Exception ex) {
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
                
                distanceData[count] = (chassis.getRightEncoderDistance());
                System.out.println("collected one data point " + distanceData[count] + ", " + ratio);
                //Start against the bridge. distance to target is he initial distance minus the distance travled.
//                chassis.move(Step);
                
                if (ratio >= 0) {
//                }
                    imageTargetRatioData[count] = ratio;
                    distanceData[count] = (chassis.getRightEncoderDistance());
                    System.out.println("collected one data point " + distanceData[count] + ", " + ratio);

                }
                
            }
            
            if (count > 50) {
                count = 49;
            } else {
                count++;
            }
            
        }
    }

    protected boolean isFinished() {
        if ((chassis.getRightEncoderDistance()) > HalfCourt - 2) {
            return true;
        } else {
            return false;
        }
    }

    protected void end() {
        System.out.println("data collection done! cnt=" + count);
        double[] ab = LineReg.bestFit(imageTargetRatioData, distanceData, count + 1);
        double a = ab[0];
        double b = ab[1];

        if (!Double.isNaN(a)) {
            NetworkTable table = NetworkTable.getTable("camera");
            table.putDouble("Slope", a);
            table.putDouble("yInt", b);
            // print results
            System.out.println("y   = " + a + " * x + " + b);
        }
        
        chassis.stopJags();
        chassis.endEncoders();
    }

    protected void interrupted() {
        end();
    }
}

class LineReg {

    public static double[] bestFit(double[] x, double[] y, int size) {
        int n = 0;

        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (; n < size; ++n) {
            sumx += x[n];
            sumx2 += x[n] * x[n];
            sumy += y[n];
            n++;
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
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