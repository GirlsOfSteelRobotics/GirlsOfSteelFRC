package com.girlsofsteel.simulator;

public class BaseCameraSimulator {

  protected enum RejectionReason {
    VALID, INVALID_X, INVALID_Y, INVALID_ANGLE
  }

  protected class TargetDeltaStruct {
    protected double m_distance;
    protected double m_angle;

    @Override
    public String toString() {
      return "TargetInfo [mDistance=" + m_distance + ", mAngle=" + m_angle + "]";
    }
  }

  protected static class TargetLocation {
    protected final String m_name;
    protected double m_x;
    protected double m_y;
    protected final double m_minX;
    protected final double m_minY;
    protected final double m_axX;
    protected final double m_maxY;
    protected final double m_angle;

    public TargetLocation(String name, double x, double y, double angle, Double minX, Double minY,
                Double maxX, Double maxY) {
      m_name = name;
      m_x = x;
      m_y = y;
      m_angle = angle;
      m_minX = minX == null ? -Double.MAX_VALUE : minX;
      m_minY = minY == null ? -Double.MAX_VALUE : minY;
      m_axX = maxX == null ? Double.MAX_VALUE : maxX;
      m_maxY = maxY == null ? Double.MAX_VALUE : maxY;
    }
  }

  protected double m_cameraFov;

  protected BaseCameraSimulator(double cameraFov) {
    m_cameraFov = cameraFov;
  }

  protected RejectionReason isTargetValid(TargetDeltaStruct targetInfo, double robotX, double robotY,
                      double robotAngle, TargetLocation peg) {

    if (robotX > peg.m_axX || robotX < peg.m_minX) {
      //             System.out.println("Ditching " + peg.mName + " because of X " +
      //             robotX + "[" + peg.mMinX + ", " + peg.mMaxX + "]");
      return RejectionReason.INVALID_X;
    }

    if (robotY > peg.m_maxY || robotY < peg.m_minY) {
      //             System.out.println("Ditching " + peg.mName + " because of Y " +
      //             robotY + "[" + peg.mMinY + ", " + peg.mMaxY + "]");
      return RejectionReason.INVALID_Y;
    }

    if (Math.abs(targetInfo.m_angle) > m_cameraFov) {
      //             System.out.println("Ditching " + peg.mName + " because of Angle "
      //             + targetInfo.mAngle + "[" + mCameraFov + "]");
      return RejectionReason.INVALID_ANGLE;
    }

    return RejectionReason.VALID;
  }
}
