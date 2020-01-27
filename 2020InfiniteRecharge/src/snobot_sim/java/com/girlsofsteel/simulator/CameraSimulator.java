package com.girlsofsteel.simulator;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

public class CameraSimulator extends BaseCameraSimulator {

    private static final double CAMERA_FOV = 50;
    private static final double CAMERA_MAX_DISTANCE = 15 * 12;

    private static final List<TargetLocation> VISION_TARGETS;

    private final Chassis mChassis;
    private final NetworkTable mMockLimelightTable;
    private final NetworkTable mLimelightRayTable;

    static {
        VISION_TARGETS = new ArrayList<>();

        double field_long_dim = 52 * 12 + 5.25;
        double field_short_dim = 26 * 12 + 11.25;
        double target_offset = 5.5 * 12;

        VISION_TARGETS.add(new TargetLocation("Top", field_long_dim, -field_short_dim / 2 - target_offset, 0, 0.0, null, null, null));
        VISION_TARGETS.add(new TargetLocation("Bot", 0, -field_short_dim / 2 + target_offset, -180, null, -field_short_dim, null, 0.0));
    }

    public CameraSimulator(Robot aRobot) {
        super(CAMERA_FOV);

        mChassis = aRobot.getContainer().getChassis();
        mMockLimelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTable coordinateGuiTable = NetworkTableInstance.getDefault().getTable("CoordinateGui");
        coordinateGuiTable.getEntry(".type").setString("CoordinateGui");

        mLimelightRayTable = coordinateGuiTable.getSubTable("CameraSim");
        mLimelightRayTable.getEntry(".type").setString("CameraSim");
    }

    @SuppressWarnings("PMD")
    public void update() {

        double robotX = mChassis.getX();
        double robotY = mChassis.getY();
        double robotAngle = mChassis.getHeading();

        double minDistance = Double.MAX_VALUE;
        TargetLocation bestTarget = null;
        TargetDeltaStruct bestTargetDelta = null;

        for (TargetLocation loc : VISION_TARGETS) {

            double dx = loc.mX - robotX;
            double dy = loc.mY - robotY;
            double dAngle = Math.toDegrees(Math.atan2(dy, dx));
            double angleFromRobot = dAngle - robotAngle;

            while (angleFromRobot < -180) {
                angleFromRobot += 360;
            }

            while (angleFromRobot > 180) {
                angleFromRobot -= 360;
            }

            TargetDeltaStruct deltaStruct = new TargetDeltaStruct();
            deltaStruct.mDistance = Math.sqrt(dx * dx + dy * dy);
            deltaStruct.mAngle = angleFromRobot;

            if (isTargetValid(deltaStruct, robotX, robotY, robotAngle, loc) == RejectionReason.VALID) {
                if (deltaStruct.mDistance < minDistance && deltaStruct.mDistance < CAMERA_MAX_DISTANCE) {
                    bestTargetDelta = deltaStruct;
                    bestTarget = loc;
                    minDistance = deltaStruct.mDistance;
                }
            }
        }

        if (bestTargetDelta == null) {
            mMockLimelightTable.getEntry("tv").setNumber(0);

            mLimelightRayTable.getEntry("Positions").setString("");
        } else {
            double el = Math.toDegrees(
                Math.atan2(100, bestTargetDelta.mDistance));
            double az = bestTargetDelta.mAngle;
            mMockLimelightTable.getEntry("tv").setNumber(1);
            mMockLimelightTable.getEntry("tx").setNumber(az);
            mMockLimelightTable.getEntry("ty").setNumber(el);

            String positionsText = robotX + "," + robotY + "," + bestTarget.mX + "," + bestTarget.mY;
            mLimelightRayTable.getEntry("Positions").setString(positionsText);
        }

    }
}
