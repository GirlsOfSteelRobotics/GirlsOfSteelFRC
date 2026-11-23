package com.gos.lib;

import org.wpilib.driverstation.MatchState;
import org.wpilib.driverstation.Alliance;

import java.util.Optional;

/**
 * This is a helper to make getting the current alliance that is less verbose than dealing with the Optionals
 */
public class GetAllianceUtil {

    /**
     * True if the DS is connected and you are on the blue alliance
     * @return True if on blue
     */
    public static boolean isBlueAlliance() {
        Optional<Alliance> maybeAlliance = MatchState.getAlliance();
        return maybeAlliance.isPresent() && maybeAlliance.get() == Alliance.BLUE;
    }

    /**
     * True if the DS is connected and you are on the red alliance
     * @return True if on red
     */
    public static boolean isRedAlliance() {
        Optional<Alliance> maybeAlliance = MatchState.getAlliance();
        return maybeAlliance.isPresent() && maybeAlliance.get() == Alliance.RED;
    }
}
