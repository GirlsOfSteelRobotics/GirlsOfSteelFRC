// Copyright (c) 2023 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package org.littletonrobotics.frc2023;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

/**
 * Contains various field dimensions and useful reference points. Dimensions are in meters, and sets
 * of corners start in the lower left moving clockwise.
 *
 * <p>All translations and poses are stored with the origin at the rightmost point on the BLUE
 * ALLIANCE wall. Use the {@link #allianceFlip(Translation2d)} and {@link #allianceFlip(Pose2d)}
 * methods to flip these values based on the current alliance color.
 */
public final class FieldConstants {
    public static final double FIELD_LENGTH = Units.inchesToMeters(651.25);
    public static final double FIELD_WIDTH = Units.inchesToMeters(315.5);
    public static final double TAPE_WIDTH = Units.inchesToMeters(2.0);
    public static final double APRIL_TAG_WIDTH = Units.inchesToMeters(6.0);

    // Dimensions for community and charging station, including the tape.
    public static final class Community {
        // Region dimensions
        public static final double INNER_X = 0.0;
        public static final double MID_X =
            Units.inchesToMeters(132.375); // Tape to the left of charging station
        public static final double OUTER_X =
            Units.inchesToMeters(193.25); // Tape to the right of charging station
        public static final double LEFT_Y = Units.feetToMeters(18.0);
        public static final double MID_Y = LEFT_Y - Units.inchesToMeters(59.39) + TAPE_WIDTH;
        public static final double RIGHT_Y = 0.0;
        public static final Translation2d[] REGION_CORNERS = {
            new Translation2d(INNER_X, RIGHT_Y),
            new Translation2d(INNER_X, LEFT_Y),
            new Translation2d(MID_X, LEFT_Y),
            new Translation2d(MID_X, MID_Y),
            new Translation2d(OUTER_X, MID_Y),
            new Translation2d(OUTER_X, RIGHT_Y),
        };

        // Charging station dimensions
        public static final double CHARGING_STATION_LENGTH = Units.inchesToMeters(76.125);
        public static final double CHARGING_STATION_WIDTH = Units.inchesToMeters(97.25);
        public static final double CHARGING_STATION_OUTER_X = OUTER_X - TAPE_WIDTH;
        public static final double CHARGING_STATION_INNER_X =
            CHARGING_STATION_OUTER_X - CHARGING_STATION_LENGTH;
        public static final double CHARGING_STATION_LEFT_Y = MID_Y - TAPE_WIDTH;
        public static final double CHARGING_STATION_RIGHT_Y = CHARGING_STATION_LEFT_Y - CHARGING_STATION_WIDTH;
        public static final Translation2d[] CHARGING_STATION_CORNERS = {
            new Translation2d(CHARGING_STATION_INNER_X, CHARGING_STATION_RIGHT_Y),
            new Translation2d(CHARGING_STATION_INNER_X, CHARGING_STATION_LEFT_Y),
            new Translation2d(CHARGING_STATION_OUTER_X, CHARGING_STATION_RIGHT_Y),
            new Translation2d(CHARGING_STATION_OUTER_X, CHARGING_STATION_LEFT_Y)
        };

        // Cable bump
        public static final double CABLE_BUMP_INNER_X =
            INNER_X + Grids.OUTER_X + Units.inchesToMeters(95.25);
        public static final double CABLE_BUMP_OUTER_X = CABLE_BUMP_INNER_X + Units.inchesToMeters(7);
        public static final Translation2d[] CABLE_BUMP_CORNERS = {
            new Translation2d(CABLE_BUMP_INNER_X, 0.0),
            new Translation2d(CABLE_BUMP_INNER_X, CHARGING_STATION_RIGHT_Y),
            new Translation2d(CABLE_BUMP_OUTER_X, 0.0),
            new Translation2d(CABLE_BUMP_OUTER_X, CHARGING_STATION_RIGHT_Y)
        };
    }

    // Dimensions for grids and nodes
    public static final class Grids {
        // X layout
        public static final double OUTER_X = Units.inchesToMeters(54.25);
        public static final double LOW_X =
            OUTER_X - (Units.inchesToMeters(14.25) / 2.0); // Centered when under cube nodes
        public static final double MID_X = OUTER_X - Units.inchesToMeters(22.75);
        public static final double HIGH_X = OUTER_X - Units.inchesToMeters(39.75);

        // Y layout
        public static final int NODE_ROW_COUNT = 9;
        public static final double NODE_FIRST_Y = Units.inchesToMeters(20.19);
        public static final double NODE_SEPARATION_Y = Units.inchesToMeters(22.0);

        // Z layout
        public static final double CUBE_EDGE_HIGH = Units.inchesToMeters(3.0);
        public static final double HIGH_CUBE_Z = Units.inchesToMeters(35.5) - CUBE_EDGE_HIGH;
        public static final double MID_CUBE_Z = Units.inchesToMeters(23.5) - CUBE_EDGE_HIGH;
        public static final double HIGH_CONE_Z = Units.inchesToMeters(46.0);
        public static final double MID_CONE_Z = Units.inchesToMeters(34.0);

        // Translations (all nodes in the same column/row have the same X/Y coordinate)
        public static final Translation2d[] LOW_TRANSLATIONS = new Translation2d[NODE_ROW_COUNT];
        public static final Translation2d[] MID_TRANSLATIONS = new Translation2d[NODE_ROW_COUNT];
        public static final Translation3d[] MID_3D_TRANSLATIONS = new Translation3d[NODE_ROW_COUNT];
        public static final Translation2d[] HIGH_TRANSLATIONS = new Translation2d[NODE_ROW_COUNT];
        public static final Translation3d[] HIGH_3D_TRANSLATIONS = new Translation3d[NODE_ROW_COUNT];

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

        // Complex low layout (shifted to account for cube vs cone rows and wide edge nodes)
        public static final double COMPLEX_LOW_X_CONES =
            OUTER_X - Units.inchesToMeters(16.0) / 2.0; // Centered X under cone nodes
        public static final double COMPLEX_LOW_X_CUBES = LOW_X; // Centered X under cube nodes
        public static final double COMPLEX_LOW_OUTER_Y_OFFSET =
            NODE_FIRST_Y - Units.inchesToMeters(3.0) - (Units.inchesToMeters(25.75) / 2.0);

        public static final Translation2d[] COMPLEX_LOW_TRANSLATIONS = {
            new Translation2d(COMPLEX_LOW_X_CONES, NODE_FIRST_Y - COMPLEX_LOW_OUTER_Y_OFFSET),
            new Translation2d(COMPLEX_LOW_X_CUBES, NODE_FIRST_Y + NODE_SEPARATION_Y * 1),
            new Translation2d(COMPLEX_LOW_X_CONES, NODE_FIRST_Y + NODE_SEPARATION_Y * 2),
            new Translation2d(COMPLEX_LOW_X_CONES, NODE_FIRST_Y + NODE_SEPARATION_Y * 3),
            new Translation2d(COMPLEX_LOW_X_CUBES, NODE_FIRST_Y + NODE_SEPARATION_Y * 4),
            new Translation2d(COMPLEX_LOW_X_CONES, NODE_FIRST_Y + NODE_SEPARATION_Y * 5),
            new Translation2d(COMPLEX_LOW_X_CONES, NODE_FIRST_Y + NODE_SEPARATION_Y * 6),
            new Translation2d(COMPLEX_LOW_X_CUBES, NODE_FIRST_Y + NODE_SEPARATION_Y * 7),
            new Translation2d(
                COMPLEX_LOW_X_CONES, NODE_FIRST_Y + NODE_SEPARATION_Y * 8 + COMPLEX_LOW_OUTER_Y_OFFSET),
        };
    }

    // Dimensions for loading zone and substations, including the tape
    public static final class LoadingZone {
        // Region dimensions
        public static final double WIDTH = Units.inchesToMeters(99.0);
        public static final double INNER_X = FieldConstants.FIELD_LENGTH;
        public static final double MID_X = FIELD_LENGTH - Units.inchesToMeters(132.25);
        public static final double OUTER_X = FIELD_LENGTH - Units.inchesToMeters(264.25);
        public static final double LEFT_Y = FieldConstants.FIELD_WIDTH;
        public static final double MID_Y = LEFT_Y - Units.inchesToMeters(50.5);
        public static final double RIGHT_Y = LEFT_Y - WIDTH;
        public static final Translation2d[] REGION_CORNERS = {
            new Translation2d(
                MID_X, RIGHT_Y), // Start at lower left next to border with opponent community
            new Translation2d(MID_X, MID_Y),
            new Translation2d(OUTER_X, MID_Y),
            new Translation2d(OUTER_X, LEFT_Y),
            new Translation2d(INNER_X, LEFT_Y),
            new Translation2d(INNER_X, RIGHT_Y),
        };

        // Double substation dimensions
        public static final double DOUBLE_SUBSTATION_LENGTH = Units.inchesToMeters(14.0);
        public static final double DOUBLE_SUBSTATION_X = INNER_X - DOUBLE_SUBSTATION_LENGTH;
        public static final double DOUBLE_SUBSTATION_SHELF_Z = Units.inchesToMeters(37.375);
        public static final double DOUBLE_SUBSTATION_CENTER_Y = Units.inchesToMeters(265.74);

        // Single substation dimensions
        public static final double SINGLE_SUBSTATION_WIDTH = Units.inchesToMeters(22.75);
        public static final double SINGLE_SUBSTATION_LEFT_X =
            FieldConstants.FIELD_LENGTH - DOUBLE_SUBSTATION_LENGTH - Units.inchesToMeters(88.77);
        public static final double SINGLE_SUBSTATION_CENTER_X =
            SINGLE_SUBSTATION_LEFT_X + (SINGLE_SUBSTATION_WIDTH / 2.0);
        public static final double SINGLE_SUBSTATION_RIGHT_X =
            SINGLE_SUBSTATION_LEFT_X + SINGLE_SUBSTATION_WIDTH;
        public static final Translation2d SINGLE_SUBSTATION_TRANSLATION =
            new Translation2d(SINGLE_SUBSTATION_CENTER_X, LEFT_Y);

        public static final double SINGLE_SUBSTATION_HEIGHT = Units.inchesToMeters(18.0);
        public static final double SINGLE_SUBSTATION_LOW_Z = Units.inchesToMeters(27.125);
        public static final double SINGLE_SUBSTATION_CENTER_Z =
            SINGLE_SUBSTATION_LOW_Z + (SINGLE_SUBSTATION_HEIGHT / 2.0);
        public static final double SINGLE_SUBSTATION_HIGH_Z =
            SINGLE_SUBSTATION_LOW_Z + SINGLE_SUBSTATION_HEIGHT;
    }

    // Locations of staged game pieces
    public static final class StagingLocations {
        public static final double CENTER_OFFSET_X = Units.inchesToMeters(47.36);
        public static final double POSITION_X = FIELD_LENGTH / 2.0 - Units.inchesToMeters(47.36);
        public static final double FIRST_Y = Units.inchesToMeters(36.19);
        public static final double SEPARATION_Y = Units.inchesToMeters(48.0);
        public static final Translation2d[] TRANSLATIONS = new Translation2d[4];

        static {
            for (int i = 0; i < TRANSLATIONS.length; i++) {
                TRANSLATIONS[i] = new Translation2d(POSITION_X, FIRST_Y + (i * SEPARATION_Y));
            }
        }
    }
}
