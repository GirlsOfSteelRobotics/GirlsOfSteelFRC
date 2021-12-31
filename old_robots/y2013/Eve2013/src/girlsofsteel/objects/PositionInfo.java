package girlsofsteel.objects;

import girlsofsteel.subsystems.Chassis;

public class PositionInfo {

    //CHANGE NAMES TO BE SPECIFIC PLACES & ADD ANGLES
    public static final int NO_POSITION = 0;
    public static final int BACK_RIGHT = 1;
    public static final int BACK_LEFT = 2;

    private static final Position[] positions = new Position[4];

    public static void init() {
        //set the should-be 3 positions here
        positions[NO_POSITION] = new Position(0, 0);
        positions[BACK_RIGHT] = new Position(
            Chassis.BACK_RIGHT_OFFSET,
            0.735);//78
        positions[BACK_LEFT] = new Position(
            Chassis.BACK_LEFT_OFFSET,
            0.81);
    }//end constructor

    public static void set(int position, int angleAdjustment, double shooterSpeed) {
        positions[position] = new Position(angleAdjustment, shooterSpeed);
    }//end set

    public static int getAngle(int position) {
        return positions[position].getAngle();
    }//end getAngle

    public static double getSpeed(int position) {
        return positions[position].getSpeed();
    }//eng getSpeed

    private static class Position {

        private final int m_angle;
        private final double m_speed;

        public Position(int angleAdjustment, double shooterSpeed) {
            m_angle = angleAdjustment;
            m_speed = shooterSpeed;
        }

        public int getAngle() {
            return m_angle;
        }

        public double getSpeed() {
            return m_speed;
        }

    }//end inner class

}//end class
