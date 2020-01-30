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

  private final Chassis m_chassis;
  private final NetworkTable m_mockLimelightTable;
  private final NetworkTable m_limelightRayTable;

  static {
    VISION_TARGETS = new ArrayList<>();

    double fieldLongDim = 52 * 12 + 5.25;
    double fieldShortDim = 26 * 12 + 11.25;
    double targetOffset = 5.5 * 12;

    VISION_TARGETS.add(new TargetLocation("Top", fieldLongDim, -fieldShortDim / 2 - targetOffset, 0, 0.0, null, null, null));
    VISION_TARGETS.add(new TargetLocation("Bot", 0, -fieldShortDim / 2 + targetOffset, -180, null, -fieldShortDim, null, 0.0));
  }

  public CameraSimulator(Robot robot) {
    super(CAMERA_FOV);

    m_chassis = robot.getContainer().getChassis();
    m_mockLimelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTable coordinateGuiTable = NetworkTableInstance.getDefault().getTable("CoordinateGui");
    coordinateGuiTable.getEntry(".type").setString("CoordinateGui");

    m_limelightRayTable = coordinateGuiTable.getSubTable("CameraSim");
    m_limelightRayTable.getEntry(".type").setString("CameraSim");
  }

  @SuppressWarnings("PMD")
  public void update() {

    double robotX = m_chassis.getX();
    double robotY = m_chassis.getY();
    double robotAngle = m_chassis.getHeading();

    double minDistance = Double.MAX_VALUE;
    TargetLocation bestTarget = null;
    TargetDeltaStruct bestTargetDelta = null;

    for (TargetLocation loc : VISION_TARGETS) {

      double dx = loc.m_x - robotX;
      double dy = loc.m_y - robotY;
      double dAngle = Math.toDegrees(Math.atan2(dy, dx));
      double angleFromRobot = dAngle - robotAngle;

      while (angleFromRobot < -180) {
        angleFromRobot += 360;
      }

      while (angleFromRobot > 180) {
        angleFromRobot -= 360;
      }

      TargetDeltaStruct deltaStruct = new TargetDeltaStruct();
      deltaStruct.m_distance = Math.sqrt(dx * dx + dy * dy);
      deltaStruct.m_angle = angleFromRobot;

      if (isTargetValid(deltaStruct, robotX, robotY, robotAngle, loc) == RejectionReason.VALID) {
        if (deltaStruct.m_distance < minDistance && deltaStruct.m_distance < CAMERA_MAX_DISTANCE) {
          bestTargetDelta = deltaStruct;
          bestTarget = loc;
          minDistance = deltaStruct.m_distance;
        }
      }
    }

    if (bestTargetDelta == null) {
      m_mockLimelightTable.getEntry("tv").setNumber(0);

      m_limelightRayTable.getEntry("Positions").setString("");
    } else {
      double el = Math.toDegrees(
        Math.atan2(100, bestTargetDelta.m_distance));
      double az = bestTargetDelta.m_angle;
      m_mockLimelightTable.getEntry("tv").setNumber(1);
      m_mockLimelightTable.getEntry("tx").setNumber(az);
      m_mockLimelightTable.getEntry("ty").setNumber(el);

      String positionsText = robotX + "," + robotY + "," + bestTarget.m_x + "," + bestTarget.m_y;
      m_limelightRayTable.getEntry("Positions").setString(positionsText);
    }

  }
}
