package girlsofsteel;

public class RobotMap {

    //chassis
    public static final int RIGHT_JAGS = 2;
    public static final int LEFT_JAGS = 1;
    public static final int ENCODER_RIGHT_CHANNEL_A = 3;
    public static final int ENCODER_RIGHT_CHANNEL_B = 4;
    public static final int ENCODER_LEFT_CHANNEL_A = 1;
    public static final int ENCODER_LEFT_CHANNEL_B = 2;
    public static final int GYRO_RATE_ANALOG = 1;
    public static final int GYRO_TEMPERATURE_ANALOG = 2;

    //shooter
    public static final int SHOOTER_JAGS = 3;
    public static final int ENCODER_SHOOTER_CHANNEL_A = 5;
    public static final int ENCODER_SHOOTER_CHANNEL_B = 6;
    public static final int TOP_ROLLER_SPIKE = 3;

    //turret
    public static final int TURRET_JAG = 4;
    public static final int ENCODER_TURRET_CHANNEL_A = 7;
    public static final int ENCODER_TURRET_CHANNEL_B = 8;

    //bridge
    public static final int BRIDGE_UP_LIMIT_SWITCH = 14;
    public static final int BRIDGE_DOWN_LIMIT_SWITCH = 13;
    public static final int BRIDGE_ARM_JAG = 5;
//    public static final int BRIDGE_ARM_SPIKE = 5;

    //collector
    public static final int MIDDLE_COLLECTOR_SPIKE = 0;
    public static final int BRUSH_JAG = 6;
//    public static final int BRUSH_SPIKE = 6;
    public static final int COLLECTOR_LIMIT_SWITCH = 9;

}
