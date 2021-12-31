//package girlsofsteel.commands;
//
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//
//public class FindCameraFunction extends CommandBase {
//
//
//    protected void initialize() {
//
//        System.out.println("data collection done! cnt=" + CameraAuto.count);
//        double[] ab = LinearRegression.bestFit(CameraAuto.imageTargetRatioData, CameraAuto.distanceData, CameraAuto.count);
//        double a = ab[0];
//        double b = ab[1];
//
//        if (!Double.isNaN(a)) {
//
//            NetworkTable table = NetworkTable.getTable("camera");
//            table.putDouble("Slope", a);
//            table.putDouble("yInt", b);
//            // print results
//            System.out.println("y   = " + a + " * x + " + b);
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
//
//class LinearRegression {
//
//    public static double[] bestFit(double[] x, double[] y, int size) {
//        int n = 0;
//
//        // first pass: read in data, compute xbar and ybar
//        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
//        for (; n < size; ++n) {
//            sumx += x[n];
//            sumx2 += x[n] * x[n];
//            sumy += y[n];
//            n++;
//        }
//        double xbar = sumx / n;
//        double ybar = sumy / n;
//
//        // second pass: compute summary statistics
//        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
//        for (int i = 0; i < n; i++) {
//            xxbar += (x[i] - xbar) * (x[i] - xbar);
//            yybar += (y[i] - ybar) * (y[i] - ybar);
//            xybar += (x[i] - xbar) * (y[i] - ybar);
//        }
//        double beta1 = xybar / xxbar;
//        double beta0 = ybar - beta1 * xbar;
//
//        // print results
//        // System.out.println("y   = " + beta1 + " * x + " + beta0);
//
//
//        double[] aAndB = new double[2];
//        aAndB[0] = beta1; // a in: aX + b
//        aAndB[1] = beta0; // b in: aX + b
//        return aAndB;
//    }
//}
