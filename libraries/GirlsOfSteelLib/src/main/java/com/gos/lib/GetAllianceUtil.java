package com.gos.lib;

import edu.wpi.first.wpilibj.DriverStation;

public class GetAllianceUtil {

    public static boolean isBlueAlliance() {
        return DriverStation.getAlliance() == DriverStation.Alliance.Blue;
    }

    public static boolean isRedAlliance() {
        return DriverStation.getAlliance() == DriverStation.Alliance.Red;
    }
}
