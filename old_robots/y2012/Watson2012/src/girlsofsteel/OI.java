package girlsofsteel;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import girlsofsteel.commands.BridgeDown;
import girlsofsteel.commands.BridgeUp;
import girlsofsteel.commands.Collect;
import girlsofsteel.commands.DisableChassis;
import girlsofsteel.commands.DisableShooter;
import girlsofsteel.commands.DisableTurret;
import girlsofsteel.commands.DriveSlowTurning;
import girlsofsteel.commands.DriveSlowVelocity;
import girlsofsteel.commands.HoldPosition;
import girlsofsteel.commands.PS3ManualTurret;
import girlsofsteel.commands.ReverseCollectors;
import girlsofsteel.commands.ReverseTopMiddleRollers;
import girlsofsteel.commands.Shoot;
import girlsofsteel.commands.ShootUsingTable;
import girlsofsteel.commands.StopCollectors;
import girlsofsteel.commands.TurretTrackTarget;

public class OI {

    //driving scales (percentage of max power)
    private final double NORMAL_DRIVE = 1.0;
    private final double MEDIUM_DRIVE = 0.75;
    private final double SLOW_DRIVE = 0.55;

    private final double HALF_TURNING = 0.5;
    private final double MEDIUM_TURNING = 0.75;

    //TODO find out what the driver buttons should be
    //driver joystick (PS3)
    private final int DRIVER_JOYSTICK_PORT = 1;
    private final Joystick driverJoystick;
    private static final int DISABLE_CHASSIS_BUTTON_LEFT = 5; //L1 button on driverJoystick
    private final JoystickButton disableChassisLeft;
    private static final int DISABLE_CHASSIS_BUTTON_RIGHT = 6; //R1 button on driverJoystick
    private final JoystickButton disableChassisRight;
    private static final int NORMAL_DRIVE_JAGS_BUTTON = 7; //L2 button on driverJoystick
    private final JoystickButton normalDriveJags;
    private static final int MEDIUM_DRIVE_JAGS_BUTTON = 8; //R2
    private final JoystickButton mediumDriveJags;
    private static final int SLOW_DRIVE_JAGS_BUTTON = 3; //circle
    private final JoystickButton slowDriveJags;
    private static final int DRIVE_SLOW_VELOCITY_BUTTON = 10; //start
    private final JoystickButton driveSlowVelocity;
    private static final int BRIDGE_ARM_UP_BUTTON = 4; //triangle button on driverJoystick
    private final JoystickButton bridgeArmUp;
    private static final int BRIDGE_ARM_DOWN_BUTTON = 2; //x
    private final JoystickButton bridgeArmDown;
    private static final int HOLD_POSITION_BUTTON = 1; //square
    private final JoystickButton holdPosition;

    //TODO find out the buttons desired for the operator
    //operator controller (PS3)
    private static final int OPERATOR_JOYSTICK_PORT = 2;
    private final Joystick operatorJoystick;
    private static final int DISABLE_SHOOTER_BUTTON = 8; //R2 on operatorJoystick
    private final JoystickButton disableShooter;
    private static final int AUTO_SHOOT_BUTTON = 6; //R1 on operatorJoystick
    private final JoystickButton autoShoot;
    private static final int TURRET_OVERRIDE_BUTTON = 5; //L1 on operatorJoystick
    private final JoystickButton turretOverride;
    private static final int DISABLE_TURRET_BUTTON = 7; //L2
    private final JoystickButton disableTurret;
    private static final int RESTART_TURRET_TRACKING_BUTTON = 10; //start on operatorJoystick
    private final JoystickButton restartTurretTracking;
    private static final int COLLECT_BALLS_BUTTON = 2; //x button on operatorJoystick
    private final JoystickButton collectBalls;
    private static final int STOP_COLLECTOR_BUTTON = 3; //circle on operatorJoystick
    private final JoystickButton stopCollector;
    private static final int COLLECTORS_REVERSE_BUTTON = 4; //triangle
    private final JoystickButton collectorsReverse;
    private static final int SHOOT_FROM_KEY_BUTTON = 1; //square
    private final JoystickButton shootFromKey;
    private static final int REVERSE_TOP_MIDDLE_ROLLERS_BUTTON = 9; //select
    private final JoystickButton reverseTopMiddleRollers;

    //buttons
    private static final int AUTO_SHOOT_PHYSICAL_BUTTON = 9;
    private static final int STOP_SHOOTER_PHYSICAL_BUTTON = 16;
    private static final int TOP_ROLLERS_OVERRIDE_SWITCH = 8; //in a commented out method
    //switches
    private static final int TURRET_OVERRIDE_SWITCH_A = 11;
    private static final int TURRET_OVERRIDE_SWITCH_B = 15;
    private static final int SHOOTER_OVERRIDE_SWITCH_A = 10;
    private static final int SHOOTER_OVERRIDE_SWITCH_B = 12;
    private static final int BRUSH_SWITCH_A = 6;
    private static final int BRUSH_SWITCH_B = 8;
    private static final int MIDDLE_COLLECTOR_SWITCH_A = 2;
    private static final int MIDDLE_COLLECTOR_SWITCH_B = 4;
    private static final int TOP_ROLLERS_SWITCH_A = 1;
    private static final int TOP_ROLLERS_SWITCH_B = 3;
    //slider
    private static final int SHOOTER_SLIDER = 6;
    //knob -> not used currently
    private static final int TURRET_KNOB_A = 5;
    private static final int TURRET_KNOB_B = 7;
    //TODO can we read from the knob without the flicking?
    //counter
    private static final int AUTONOMOUS_COUNTER_ONE = 1;
    private static final int AUTONOMOUS_COUNTER_TWO = 2;
    private static final int AUTONOMOUS_COUNTER_THREE = 3;
    private static final int AUTONOMOUS_COUNTER_FIVE = 5;
    private static final int AUTONOMOUS_COUNTER_SEVEN = 7;
    //lights -> TODO figure out lights (digital input numbers & how to use)
    public static final int SHOOTER_LIGHT_A = 36;
    public static final int SHOOTER_LIGHT_B = 37;

    //  Default Constructor
    public OI() {
        driverJoystick = new Joystick(DRIVER_JOYSTICK_PORT);

        disableChassisLeft = new JoystickButton(driverJoystick, DISABLE_CHASSIS_BUTTON_LEFT);
        disableChassisRight = new JoystickButton(driverJoystick, DISABLE_CHASSIS_BUTTON_RIGHT);
        normalDriveJags = new JoystickButton(driverJoystick, NORMAL_DRIVE_JAGS_BUTTON);
        slowDriveJags = new JoystickButton(driverJoystick, SLOW_DRIVE_JAGS_BUTTON);
        mediumDriveJags = new JoystickButton(driverJoystick, MEDIUM_DRIVE_JAGS_BUTTON);
        driveSlowVelocity = new JoystickButton((driverJoystick), DRIVE_SLOW_VELOCITY_BUTTON);
        bridgeArmDown = new JoystickButton(driverJoystick, BRIDGE_ARM_DOWN_BUTTON);
        bridgeArmUp = new JoystickButton(driverJoystick, BRIDGE_ARM_UP_BUTTON);
        holdPosition = new JoystickButton(driverJoystick, HOLD_POSITION_BUTTON);

        disableChassisLeft.whenPressed(new DisableChassis());
        disableChassisRight.whenPressed(new DisableChassis());
        normalDriveJags.whenPressed(new DriveSlowTurning(NORMAL_DRIVE,HALF_TURNING));
        slowDriveJags.whenPressed(new DriveSlowTurning(SLOW_DRIVE, HALF_TURNING));
        mediumDriveJags.whenPressed((new DriveSlowTurning(MEDIUM_DRIVE, MEDIUM_TURNING)));
        driveSlowVelocity.whenPressed(new DriveSlowVelocity());//at the momement
        //doesn't work -> not really necessary (not used for bridge really)
        bridgeArmDown.whenPressed(new BridgeDown());
        bridgeArmUp.whenPressed(new BridgeUp());
        holdPosition.whileHeld(new HoldPosition());
        holdPosition.whenReleased(new DriveSlowTurning(SLOW_DRIVE, HALF_TURNING));

        operatorJoystick = new Joystick(OPERATOR_JOYSTICK_PORT);

        collectBalls = new JoystickButton(operatorJoystick, COLLECT_BALLS_BUTTON);
        collectorsReverse = new JoystickButton(operatorJoystick, COLLECTORS_REVERSE_BUTTON);
        stopCollector = new JoystickButton(operatorJoystick, STOP_COLLECTOR_BUTTON);
        turretOverride = new JoystickButton(operatorJoystick, TURRET_OVERRIDE_BUTTON);
        restartTurretTracking = new JoystickButton(operatorJoystick, RESTART_TURRET_TRACKING_BUTTON);
        autoShoot = new JoystickButton(operatorJoystick, AUTO_SHOOT_BUTTON);
        disableShooter = new JoystickButton(operatorJoystick, DISABLE_SHOOTER_BUTTON);
        disableTurret = new JoystickButton(operatorJoystick,DISABLE_TURRET_BUTTON);
        shootFromKey = new JoystickButton(operatorJoystick,SHOOT_FROM_KEY_BUTTON);
        reverseTopMiddleRollers = new JoystickButton(operatorJoystick,REVERSE_TOP_MIDDLE_ROLLERS_BUTTON);

        //TODO ask drive team if they want to switch some of these to "while held" functions
        //maybe shooting?
        collectBalls.whenPressed(new Collect());
        collectorsReverse.whenPressed(new ReverseCollectors());
        stopCollector.whenPressed(new StopCollectors());
        turretOverride.whenPressed(new PS3ManualTurret());
        restartTurretTracking.whenPressed(new TurretTrackTarget());
        autoShoot.whileHeld(new ShootUsingTable(true));
        disableShooter.whenPressed(new DisableShooter());
        disableTurret.whenPressed(new DisableTurret());
        shootFromKey.whileHeld(new Shoot(24.0));//dead-reckoning from key
        reverseTopMiddleRollers.whileHeld(new ReverseTopMiddleRollers());
    }

    public Joystick getDriverJoystick() {
        return driverJoystick;
    }

    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }

    public boolean isCollectCameraDataPressed(){
//        if(operatorJoystick.getRawButton(COLLECT_CAMERA_DATA)){
        return true;
    }

    //buttons
    //shooter
    boolean autoShootRunning = false;
    boolean stopShooterRunning = false;
    boolean topRollersOverriden = false;
    int currValue = 0;
    int preValue = 0;

    public boolean isShootRunning() {
        try {
            if (DriverStationEnhancedIO.getInstance().getDigital(AUTO_SHOOT_PHYSICAL_BUTTON)) {
                stopShooterRunning = false;
                autoShootRunning = true;
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "Stop Shoot:" + stopShooterRunning);
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "Shoot:" + autoShootRunning);
            }
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        return autoShootRunning;
    }

    public boolean isStopShooterRunning() {
        try {
            if (DriverStationEnhancedIO.getInstance().getDigital(STOP_SHOOTER_PHYSICAL_BUTTON)) {
                stopShooterRunning = true;
                autoShootRunning = false;
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "Stop Shoot:" + stopShooterRunning);
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "Shoot:" + autoShootRunning);
            }
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        return stopShooterRunning;
    }

//This button is an analog(because we ran out of digital) so I changed the following function.
    public boolean areTopRollersOverriden() {
//        double switchValue = 0.0;
//        try {
//            switchValue = DriverStationEnhancedIO.getInstance().getAnalogIn(TOP_ROLLERS_OVERRIDE_SWITCH);
//        } catch (EnhancedIOException ex) {
//            ex.printStackTrace();
//        }
//        //System.out.println("Top Rollers switch:" + switchValue);
//        if (switchValue < 0) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }

    public boolean isButtonPressed(int buttonNumber) {
        boolean buttonValue = false;
        try {
            buttonValue = DriverStationEnhancedIO.getInstance().getDigital(buttonNumber);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        return buttonValue;
    }

    //switches
    public int getTurretOverrideSwitchValue() {
        //1 -> auto
        //2 -> set position
        //3 -> manual
        return getSwitchValue(TURRET_OVERRIDE_SWITCH_A, TURRET_OVERRIDE_SWITCH_B);
    }

    public int getShooterOverrideSwitchValue() {
        //1 -> auto
        //2 -> increment (increase/decrease speed)
        //3 -> manual
        return getSwitchValue(SHOOTER_OVERRIDE_SWITCH_A, SHOOTER_OVERRIDE_SWITCH_B);
    }

    public int getBrushSwitchValue() {
        //1 -> forward
        //2 -> off
        //3 -> reverse
        return getSwitchValue(BRUSH_SWITCH_A, BRUSH_SWITCH_B);
    }

    public int getMiddleCollectorSwitchValue() {
        //1 -> forward
        //2 -> off
        //3 -> reverse
        return getSwitchValue(MIDDLE_COLLECTOR_SWITCH_A, MIDDLE_COLLECTOR_SWITCH_B);
    }

    public int getTopRollersSwitchValue() { //only after override
        //1 -> forward
        //2 -> off
        //3 -> reverse
        return getSwitchValue(TOP_ROLLERS_SWITCH_A, TOP_ROLLERS_SWITCH_B);
    }

    private int getSwitchValue(int channelA, int channelB) {
        boolean digitalInputA = true;
        boolean digitalInputB = true;
        try {
            digitalInputA = DriverStationEnhancedIO.getInstance().getDigital(channelA);
            digitalInputB = DriverStationEnhancedIO.getInstance().getDigital(channelB);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        int switchValue;
        if (digitalInputA && !digitalInputB) {
            switchValue = 3;
        } else if (!digitalInputA && !digitalInputB) {
            switchValue = 2;
        } else if (!digitalInputA && digitalInputB) {
            switchValue = 1;
        } else { //THIS SHOULD NEVER HAPPEN.
            switchValue = 4;
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "ERROR WITH THE SWITCH VALUE");
        }
        return switchValue;
    }

    //switch -- turret
    public boolean isTurretAutoOn() {
        return getTurretOverrideSwitchValue() == 2;
    }

    public boolean isTurretSetPositionOn() {
        return getTurretOverrideSwitchValue() == 1;
    }

    public boolean isTurretManualOn() {
        return getTurretOverrideSwitchValue() == 3;
    }

    //switch -- shooter
    public boolean isShooterAutoOn() {
        return getShooterOverrideSwitchValue() == 2;
    }

    public boolean isShooterIncrementOn() {
        return getShooterOverrideSwitchValue() == 1;
    }

    public boolean isShooterManualOn() {
        return getShooterOverrideSwitchValue() == 3;
    }

    //rollers
    public boolean isBrushForward() {
        return getBrushSwitchValue() == 1;
    }

    public boolean isBrushOff() {
        return getBrushSwitchValue() == 2;
    }

    public boolean isBrushReverse() {
        return getBrushSwitchValue() == 3;
    }

    public boolean isMiddleCollectorForward() {
        return getMiddleCollectorSwitchValue() == 1;
    }

    public boolean isMiddleCollectorOff() {
        return getMiddleCollectorSwitchValue() == 2;
    }

    public boolean isMiddleCollectorReverse() {
        return getMiddleCollectorSwitchValue() == 3;
    }

    public boolean areTopRollersForward() {
        return getTopRollersSwitchValue() == 1;
    }

    public boolean areTopRollersOff() {
        return getTopRollersSwitchValue() == 2;
    }

    public boolean areTopRollersReverse() {
        return getTopRollersSwitchValue() == 3;
    }

    //slider
    public double getShooterSliderValue() {
        double sliderValue = 0.0;
        try {
            sliderValue = DriverStationEnhancedIO.getInstance().getAnalogIn(SHOOTER_SLIDER);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        return sliderValue;
    }

    //knob
    public double getTurretKnobValue(double deadzone) {
       double returnVal;
        try {
            currValue = DriverStationEnhancedIO.getInstance().getEncoder(1);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }

        if(currValue - preValue > deadzone){
           returnVal = -5.0;
        }

        else if(currValue - preValue < -deadzone){
            returnVal = 5.0;
        }
        else{
            returnVal = 0.0;
        }
        preValue = currValue;
       return returnVal;
    }

    /*
     * Autonomous Counter Analog Values:
     *
     * 1: A1
     * 2: A3
     * 3: A1 & A3
     * 4: A5
     * 5: A1 & A5
     * 6: A3 & A5
     * 7: A1 & A3 & A5
     * 8: A7
     * 9: A1 & A7
     * 10: A2
     * 11: A1 & A2
     * 12: A2 & A3
     * 13: A1 & A2 & A3
     * 14: A2 & A5
     * 15: A1 & A2 & A5
     * NOTE: A = analog input
     */
    public int getAutonomousCounterValue() {
        boolean auto1 = false;
        try {
            auto1 = changeAnalogs(DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_ONE));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        boolean auto2 = false;
        try {
            auto2 = changeAnalogs(DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_TWO));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        boolean auto3 = false;
        try {
            auto3 = changeAnalogs(DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_THREE));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        boolean auto5 = false;
        try {
            auto5 = changeAnalogs(DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_FIVE));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        boolean auto7 = false;
        try {
            auto7 = changeAnalogs(DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_SEVEN));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }

        int autoNumber = 0;

        if(auto1 && !auto2 && !auto3 && !auto5 && !auto7){
            autoNumber = 1;
        }
        if(!auto1 && !auto2 && auto3 && !auto5 && !auto7){
            autoNumber = 2;
        }
        else if(auto1 && !auto2 && auto3 && !auto5 && !auto7){
            autoNumber = 3;
        }
        else if(!auto1 && !auto2 && !auto3 && auto5 && !auto7){
            autoNumber = 4;
        }
        else if(auto1 && !auto2 && !auto3 && auto5 && !auto7){
            autoNumber = 5;
        }
        else if(!auto1 && !auto2 && auto3 && auto5 && !auto7){
            autoNumber = 6;
        }
        else if(auto1 && !auto2 && auto3 && auto5 && !auto7){
            autoNumber = 7;
        }
        else if(!auto1 && !auto2 && !auto3 && !auto5 && auto7){
            autoNumber = 8;
        }
        else if(auto1 && !auto2 && !auto3 && !auto5 && auto7){
            autoNumber = 9;
        }
        else if(!auto1 && auto2 && !auto3 && !auto5 && !auto7){
            autoNumber = 10;
        }
        else if(auto1 && auto2 && !auto3 && !auto5 && !auto7){
            autoNumber = 11;
        }
        else if(!auto1 && auto2 && auto3 && !auto5 && !auto7){
            autoNumber = 12;
        }
        else if(auto1 && auto2 && auto3 && !auto5 && !auto7){
            autoNumber = 13;
        }
        else if(!auto1 && auto2 && !auto3 && auto5 && !auto7){
            autoNumber = 14;
        }
        else if(auto1 && auto2 && !auto3 && auto5 && !auto7){
            autoNumber = 15;
        }

        System.out.println("auto1: " + auto1 + "auto2: " + auto2 + "auto3: " + auto3 + "auto5: " + auto5 + "auto7: " + auto7);
        return autoNumber;

    }

    private boolean changeAnalogs(double analogValue) {
        return analogValue > 3;
    }
//If ever used, change line numbers in the driver station prints
    public void printAnalogAutonomous() {
        try {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Counter 1:" + DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_ONE));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        DriverStationLCD.getInstance().updateLCD();
        try {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "Counter 2:" + DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_TWO));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        DriverStationLCD.getInstance().updateLCD();
        try {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, "Counter 3:" + DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_THREE));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        DriverStationLCD.getInstance().updateLCD();
        try {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "Counter 5:" + DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_FIVE));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        DriverStationLCD.getInstance().updateLCD();
        try {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Counter 7:" + DriverStationEnhancedIO.getInstance().getAnalogIn(AUTONOMOUS_COUNTER_SEVEN));
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        DriverStationLCD.getInstance().updateLCD();
    }
}
