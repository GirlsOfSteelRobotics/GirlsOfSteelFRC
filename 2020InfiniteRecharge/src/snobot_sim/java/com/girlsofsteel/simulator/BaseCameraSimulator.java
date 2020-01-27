package com.girlsofsteel.simulator;

public class BaseCameraSimulator {

    protected enum RejectionReason {
        VALID, INVALID_X, INVALID_Y, INVALID_ANGLE
    }

    protected class TargetDeltaStruct {
        protected double mDistance;
        protected double mAngle;

        @Override
        public String toString() {
            return "TargetInfo [mDistance=" + mDistance + ", mAngle=" + mAngle + "]";
        }
    }

    protected static class TargetLocation {
        protected final String mName;
        protected double mX;
        protected double mY;
        protected final double mMinX;
        protected final double mMinY;
        protected final double mMaxX;
        protected final double mMaxY;
        protected final double mAngle;

        public TargetLocation(String name, double aX, double aY, double aAngle, Double aMinX, Double aMinY,
                              Double aMaxX, Double aMaxY) {
            mName = name;
            mX = aX;
            mY = aY;
            mAngle = aAngle;
            mMinX = aMinX == null ? -Double.MAX_VALUE : aMinX;
            mMinY = aMinY == null ? -Double.MAX_VALUE : aMinY;
            mMaxX = aMaxX == null ? Double.MAX_VALUE : aMaxX;
            mMaxY = aMaxY == null ? Double.MAX_VALUE : aMaxY;
        }
    }

    protected double mCameraFov;

    protected BaseCameraSimulator(double aCameraFov) {
        mCameraFov = aCameraFov;
    }

    protected RejectionReason isTargetValid(TargetDeltaStruct aTargetInfo, double aRobotX, double aRobotY,
                                            double aRobotAngle, TargetLocation aPeg) {

        if (aRobotX > aPeg.mMaxX || aRobotX < aPeg.mMinX) {
//			 System.out.println("Ditching " + aPeg.mName + " because of X " +
//			 aRobotX + "[" + aPeg.mMinX + ", " + aPeg.mMaxX + "]");
            return RejectionReason.INVALID_X;
        }

        if (aRobotY > aPeg.mMaxY || aRobotY < aPeg.mMinY) {
//			 System.out.println("Ditching " + aPeg.mName + " because of Y " +
//			 aRobotY + "[" + aPeg.mMinY + ", " + aPeg.mMaxY + "]");
            return RejectionReason.INVALID_Y;
        }

        if (Math.abs(aTargetInfo.mAngle) > mCameraFov) {
//			 System.out.println("Ditching " + aPeg.mName + " because of Angle "
//			 + aTargetInfo.mAngle + "[" + mCameraFov + "]");
            return RejectionReason.INVALID_ANGLE;
        }

        return RejectionReason.VALID;
    }
}
