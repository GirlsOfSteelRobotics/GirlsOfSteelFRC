/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.sherlock;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networking.NetworkTable;
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import java.awt.Graphics;
//import sun.java2d.loops.DrawLine;
import edu.wpi.first.wpijavacv.*;

public class SherlockLight extends StaticWidget {

    private Graphics DriverAid;

    @Override
    public void init() {
        // Start up
    }

    @Override
    public void propertyChanged(Property prprt) {
        // Do nothing?
    }

    @Override
    public void paint(Graphics g) {
        NetworkTable table = NetworkTable.getTable("camera");
        int topfirstx = (int) table.getDouble("topfirstx");
        int topfirsty = (int) table.getDouble("topfirsty");
        int topsecondx = (int) table.getDouble("topsecondx");
        int topsecondy = (int) table.getDouble("topsecondy");
        int bottomfirstx = (int) table.getDouble("bottomfirstx");
        int bottomfirsty = (int) table.getDouble("bottomfirsty");
        int bottomsecondx = (int) table.getDouble("bottomsecondx");
        int bottomsecondy = (int) table.getDouble("bottomsecondy");

        WPIPoint topfirst = new WPIPoint(topfirstx, topfirsty);
        WPIPoint topsecond = new WPIPoint(topsecondx, topsecondy);
        WPIPoint bottomfirst = new WPIPoint(bottomfirstx, bottomfirsty);
        WPIPoint bottomsecond = new WPIPoint(bottomsecondx, bottomsecondy);

        g.drawLine(topfirstx, topfirsty, bottomfirstx, bottomfirsty);

//        NetworkTable table = NetworkTable.getTable("camera");
//                        table.putBoolean("foundTarget", true);
//                        table.putDouble("xDifference", meanDiffference);
//                        table.putDouble("distanceToBottomTarget", meanDistance);
//                        table.putDouble("imageTargetRatio", meanAverageTargetHight);
    }
}
