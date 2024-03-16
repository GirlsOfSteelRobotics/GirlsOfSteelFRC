package com.gos.crescendo2024;

import edu.wpi.first.math.util.Units;

public class RobotExtrinsics {
    public static final double ROBOT_WIDTH;
    public static final double ROBOT_LENGTH;

    static {
        if (Constants.IS_COMPETITION_ROBOT) {
            ROBOT_WIDTH = Units.inchesToMeters(25);
            ROBOT_LENGTH = Units.inchesToMeters(25);
        } else {
            ROBOT_WIDTH = Units.inchesToMeters(28);
            ROBOT_LENGTH = Units.inchesToMeters(28);
        }
    }
}
