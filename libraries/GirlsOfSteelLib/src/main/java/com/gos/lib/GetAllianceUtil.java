package com.gos.lib;

import edu.wpi.first.wpilibj.DriverStation;

import java.util.Optional;

public class GetAllianceUtil {

    public static boolean isBlueAlliance() {
        Optional<DriverStation.Alliance> maybeAlliance = DriverStation.getAlliance();
        return maybeAlliance.isPresent() && maybeAlliance.get() == DriverStation.Alliance.Blue;
    }

    public static boolean isRedAlliance() {
        Optional<DriverStation.Alliance> maybeAlliance = DriverStation.getAlliance();
        return maybeAlliance.isPresent() && maybeAlliance.get() == DriverStation.Alliance.Red;
    }
}
