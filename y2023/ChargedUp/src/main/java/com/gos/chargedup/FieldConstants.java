package com.gos.chargedup;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class FieldConstants {
    // y layout of field for turret auto pivoting
    public static final int NODE_ROW_COUNT = 9;
    public static final Translation2d[] LOW_TRANSLATIONS = new Translation2d[NODE_ROW_COUNT];
    public static final Translation2d[] MID_TRANSLATIONS = new Translation2d[NODE_ROW_COUNT];
    public static final Translation3d[] MID_3D_TRANSLATIONS = new Translation3d[NODE_ROW_COUNT];
    public static final double NODE_FIRST_Y = Units.inchesToMeters(20.19);
    public static final double NODE_SEPARATION_Y = Units.inchesToMeters(22.0);

    //additional turret auto variables
    public static final double CUBE_EDGE_HIGH = Units.inchesToMeters(3.0);
    public static final double HIGH_CUBE_Z = Units.inchesToMeters(35.5) - CUBE_EDGE_HIGH;
    public static final double MID_CUBE_Z = Units.inchesToMeters(23.5) - CUBE_EDGE_HIGH;
    public static final double HIGH_CONE_Z = Units.inchesToMeters(46.0);
    public static final double MID_CONE_Z = Units.inchesToMeters(34.0);
    public static final Translation2d[] HIGH_TRANSLATIONS = new Translation2d[NODE_ROW_COUNT];
    public static final Translation3d[] HIGH_3D_TRANSLATIONS = new Translation3d[NODE_ROW_COUNT];


    // x layout of the field for turret auto pivoting
    public static final double OUTER_X = Units.inchesToMeters(193.25);
    public static final double LOW_X = OUTER_X - (Units.inchesToMeters(14.25) / 2.0);
    public static final double MID_X = OUTER_X - Units.inchesToMeters(22.75);
    public static final double HIGH_X = OUTER_X - Units.inchesToMeters(39.75);

    static {
        for (int i = 0; i < NODE_ROW_COUNT; i++) {
            boolean isCube = i == 1 || i == 4 || i == 7;
            LOW_TRANSLATIONS[i] = new Translation2d(LOW_X, NODE_FIRST_Y + NODE_SEPARATION_Y * i);
            MID_TRANSLATIONS[i] = new Translation2d(MID_X, NODE_FIRST_Y + NODE_SEPARATION_Y * i);
            MID_3D_TRANSLATIONS[i] =
                new Translation3d(MID_X, NODE_FIRST_Y + NODE_SEPARATION_Y * i, isCube ? MID_CUBE_Z : MID_CONE_Z);
            HIGH_3D_TRANSLATIONS[i] =
                new Translation3d(
                    HIGH_X, NODE_FIRST_Y + NODE_SEPARATION_Y * i, isCube ? HIGH_CUBE_Z : HIGH_CONE_Z);
            HIGH_TRANSLATIONS[i] = new Translation2d(HIGH_X, NODE_FIRST_Y + NODE_SEPARATION_Y * i);
        }
    }
}
