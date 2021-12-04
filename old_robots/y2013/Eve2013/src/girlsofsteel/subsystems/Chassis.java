    package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.GoSPIDController;
import girlsofsteel.objects.SmoothEncoder;

/**
 * Chassis subsystem for kiwi drive: creates jags, encoders, PID, & drive
 * function. UNIT: inches
 */
@SuppressWarnings("PMD.ExcessivePublicCount")
public class Chassis extends Subsystem {

    //tentative angles from 0 being straight forward
    public static final double ROTATION_THRESHOLD = 10;
    public static final int FEEDER_RIGHT = -88;
    public static final int FEEDER_LEFT = 6;
    public static final int BACK_RIGHT_OFFSET = 49;
    public static final int BACK_LEFT_OFFSET = 46;

    //scale for rotation
    public static final double SLOW_ROTATE = 0.3;
    public static final double SCALE = 0.04;
    public static final double DEADZONE_VALUE = 0.2;
    //robot info
    public static final double WHEEL_DIAMETER = 6.0;
    private static final double GEAR_RATIO = 1.0;
    //wheel numbers
    public static final int RIGHT_WHEEL = 0;
    public static final int BACK_WHEEL = 1;
    public static final int LEFT_WHEEL = 2;
    //x & y of wheel vectors
    //right wheel = (cos(112*pi/180), sin(112*pi/180))
    public static final double RIGHT_X = -0.3746065934;
    public static final double RIGHT_Y = 0.9271838546;
    //back wheel = (1,0)
    public static final double BACK_X = 1.0;
    public static final double BACK_Y = 0.0;
    //left wheel = (cos(248*pi/180),sin(248*pi/180))
    public static final double LEFT_X = -0.3746065934;
    public static final double LEFT_Y = -0.9271838546;

    //Lengths on the wheel triangle
    public static final double A_LENGTH = 33;
    public static final double B_LENGTH = 25;
    public static final double C_LENGTH = 25;
    public static final double X_LENGTH = A_LENGTH/2;
    public static final double Y_LENGTH = Math.sqrt((B_LENGTH*B_LENGTH)-(
            X_LENGTH*X_LENGTH))/2;

    //the length of each wheel from the center
    public static final double RIGHT_WHEEL_ROBOT_RADIUS = 21.60873; /* for Eve: Math.sqrt(
            (X_LENGTH*X_LENGTH)+(Y_LENGTH*Y_LENGTH)); */
    public static final double BACK_WHEEL_ROBOT_RADIUS = 11.16635; // for Eve: Y_LENGTH;
    public static final double LEFT_WHEEL_ROBOT_RADIUS = RIGHT_WHEEL_ROBOT_RADIUS; // value = 21.60873
            // for Eve: Math.sqrt((X_LENGTH*X_LENGTH)+(Y_LENGTH*Y_LENGTH));
    private final double[] wheelXs = new double[3];
    private final double[] wheelYs = new double[3];
    private final double[] wheelRadii = new double[3];
    //encoder info
    public static final boolean REVERSE_DIRECTION = true;

    //create jags
    private final Jaguar rightJag;
    private final Jaguar backJag;
    private final Jaguar leftJag;
    //create encoders
    private final Encoder rightEncoder;
    private final Encoder backEncoder;
    private final Encoder leftEncoder;
    private static final double ENCODER_PULSES = 360.0;//CHANGE for real robot
    private static final double RIGHT_ENCODER_UNIT = (WHEEL_DIAMETER * Math.PI
            * GEAR_RATIO) / ENCODER_PULSES;
    private static final double BACK_ENCODER_UNIT = (WHEEL_DIAMETER * Math.PI
            * GEAR_RATIO) / ENCODER_PULSES;
    private static final double LEFT_ENCODER_UNIT = (WHEEL_DIAMETER * Math.PI
            * GEAR_RATIO) / ENCODER_PULSES;
    //create normal PID
//    private PIDController rightPIDRate;
//    private PIDController backPIDRate;
//    private PIDController leftPIDRate;
    //create GoSPID
    private final GoSPIDController rightPIDRate;
    private final GoSPIDController backPIDRate;
    private final GoSPIDController leftPIDRate;
    private final GoSPIDController rightPIDPosition;
    private final GoSPIDController backPIDPosition;
    private final GoSPIDController leftPIDPosition;
    private static final double rightP = 0.0;//CHANGE for real robot
    private static final double rightI = 0.0;//CHANGE for real robot
    private static final double rightD = 0.0;//CHANGE for real robot
    private static final double backP = 0.0;//CHANGE for real robot
    private static final double backI = 0.0;//CHANGE for real robot
    private static final double backD = 0.0;//CHANGE for real robot
    private static final double leftP = 0.0;//CHANGE for real robot
    private static final double leftI = 0.0;//CHANGE for real robot
    private static final double leftD = 0.0;//CHANGE for real robot
    private static final double rightPositionP = 0.1;//CHANGE for real robot
    private static final double rightPositionI = 0.0;//CHANGE for real robot
    private static final double rightPositionD = 0.0;//CHANGE for real robot
    private static final double backPositionP = 0.1;//CHANGE for real robot
    private static final double backPositionI = 0.0;//CHANGE for real robot
    private static final double backPositionD = 0.0;//CHANGE for real robot
    private static final double leftPositionP = 0.1;//CHANGE for real robot
    private static final double leftPositionI = 0.0;//CHANGE for real robot
    private static final double leftPositionD = 0.0;//CHANGE for real robot
    //gryo
    private final Gyro gyro;
    private boolean gyroOn;
    private boolean rotating;
    private boolean manualRotationDisabled;
    private boolean negative;
    double gyroAdjustment;
    double fieldAdjustment;

    public Chassis() {
        //wheel x & ys
        wheelXs[RIGHT_WHEEL] = RIGHT_X;
        wheelXs[BACK_WHEEL] = BACK_X;
        wheelXs[LEFT_WHEEL] = LEFT_X;
        wheelYs[RIGHT_WHEEL] = RIGHT_Y;
        wheelYs[BACK_WHEEL] = BACK_Y;
        wheelYs[LEFT_WHEEL] = LEFT_Y;

        //robot radii
        wheelRadii[RIGHT_WHEEL] = RIGHT_WHEEL_ROBOT_RADIUS;
        wheelRadii[BACK_WHEEL] = BACK_WHEEL_ROBOT_RADIUS;
        wheelRadii[LEFT_WHEEL] = LEFT_WHEEL_ROBOT_RADIUS;

        //jags
        rightJag = new Jaguar(RobotMap.RIGHT_WHEEL_JAG);
        backJag = new Jaguar(RobotMap.BACK_WHEEL_JAG);
        leftJag = new Jaguar(RobotMap.LEFT_WHEEL_JAG);

        //normal encoders
//        rightEncoder = new Encoder(RobotMap.RIGHT_WHEEL_CHANNEL_A,
//                RobotMap.RIGHT_WHEEL_CHANNEL_B, !REVERSE_DIRECTION,
//                EncodingType.k4X);
//        backEncoder = new Encoder(RobotMap.BACK_WHEEL_CHANNEL_A,
//                RobotMap.BACK_WHEEL_CHANNEL_B, REVERSE_DIRECTION,
//                EncodingType.k4X);
//        leftEncoder = new Encoder(RobotMap.LEFT_WHEEL_CHANNEL_A,
//                RobotMap.LEFT_WHEEL_CHANNEL_B, REVERSE_DIRECTION,
//                EncodingType.k4X);

        //smooth encoders
        rightEncoder = new SmoothEncoder(RobotMap.RIGHT_WHEEL_CHANNEL_A,
                RobotMap.RIGHT_WHEEL_CHANNEL_B, !REVERSE_DIRECTION,
                EncodingType.k4X);
        backEncoder = new SmoothEncoder(RobotMap.BACK_WHEEL_CHANNEL_A,
                RobotMap.BACK_WHEEL_CHANNEL_B, REVERSE_DIRECTION,
                EncodingType.k4X);
        leftEncoder = new SmoothEncoder(RobotMap.LEFT_WHEEL_CHANNEL_A,
                RobotMap.LEFT_WHEEL_CHANNEL_B, REVERSE_DIRECTION,
                EncodingType.k4X);

        //normal PID
//        rightPIDRate = new PIDController(rightP, rightI, rightD, rightEncoder,
//                new PIDOutput(){
//                    public void pidWrite(double output) {
//                        setRightJag(output);
//                    }
//                });
//        backPIDRate = new PIDController(backP, backI, backD, backEncoder,
//                new PIDOutput(){
//                    public void pidWrite(double output) {
//                        setBackJag(output);
//                    }
//                });
//        leftPIDRate = new PIDController(leftP, leftI, leftD, leftEncoder,
//                new PIDOutput(){
//                    public void pidWrite(double output){
//                        setLeftJag(output);
//                    }
//                });

        //GoSPID
        rightPIDRate = new GoSPIDController(rightP, rightI, rightD,
                rightEncoder, new PIDOutput(){
                    @Override
                    public void pidWrite(double output){
                        setRightJag(output);
                    }
                }, GoSPIDController.RATE); //ADD integral constant
        backPIDRate = new GoSPIDController(backP, backI, backD, backEncoder,
                new PIDOutput(){
                    @Override
                    public void pidWrite(double output){
                        setBackJag(output);
                    }
                }, GoSPIDController.RATE);//ADD integral constant
        leftPIDRate = new GoSPIDController(leftP, leftI, leftD, leftEncoder,
                new PIDOutput(){
                    @Override
                    public void pidWrite(double output){
                        setLeftJag(output);
                    }
                }, GoSPIDController.RATE);//ADD integral constant


        rightPIDPosition = new GoSPIDController(rightPositionP, rightPositionI,
                rightPositionD, rightEncoder,
                new PIDOutput(){
                    @Override
                    public void pidWrite(double output){
                        setRightJag(output);
                    }
                }, GoSPIDController.POSITION);
        backPIDPosition = new GoSPIDController(backPositionP, backPositionI,
                backPositionD, backEncoder,
                new PIDOutput(){
                    @Override
                    public void pidWrite(double output){
                        setBackJag(output);
                    }
                }, GoSPIDController.POSITION);
        leftPIDPosition = new GoSPIDController(leftPositionP, leftPositionI,
                leftPositionD, leftEncoder,
                new PIDOutput(){
                    @Override
                    public void pidWrite(double output){
                        setLeftJag(output);
                    }
                }, GoSPIDController.POSITION);

        gyro = new AnalogGyro(RobotMap.GYRO_PORT);

        gyroOn = false;
        rotating = false;
        manualRotationDisabled = false;
        negative = false;
        gyroAdjustment = 0.0;
        fieldAdjustment = 0.0;
    }//end constructor

    //set jags
    public void setRightJag(double speed) {
        rightJag.set(-speed);
    }

    public void setBackJag(double speed) {
        backJag.set(-speed);
    }

    public void setLeftJag(double speed) {
        leftJag.set(-speed);
    }

    //stop jags
    public void stopJags() {
        setRightJag(0.0);
        setBackJag(0.0);
        setLeftJag(0.0);
    }

    //set encoders
    public void initEncoders() {
        rightEncoder.setDistancePerPulse(RIGHT_ENCODER_UNIT);
        backEncoder.setDistancePerPulse(BACK_ENCODER_UNIT);
        leftEncoder.setDistancePerPulse(LEFT_ENCODER_UNIT);
    }

    public double getRightEncoderDistance() {
        return rightEncoder.getDistance();
    }

    public double getBackEncoderDistance() {
        return backEncoder.getDistance();
    }

public double getLeftEncoderDistance() {
        return leftEncoder.getDistance();
    }
    public double getRightEncoderRate(){
        return rightEncoder.getRate(); //(negative because jags are negative too) ~KiwiDrive
    }

    public double getBackEncoderRate(){
        return backEncoder.getRate();
    }

    public double getLeftEncoderRate(){
        return leftEncoder.getRate();
    }

    public void stopEncoders() {
    }

    //PIDs
    public void initRatePIDs() {
        rightPIDRate.setOutputThreshold(0.05);//CHANGE maybe?
        /*
        //for normal PID
        rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        backEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        */
        //for GoS PID
        rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        backEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        rightPIDRate.enable();
        backPIDRate.enable();
        leftPIDRate.enable();
    }

    public void setRightPIDRateValues(double p, double i, double d){
        rightPIDRate.setPID(p, i, d);

    }

    public void setBackPIDRateValues(double p, double i, double d){
        backPIDRate.setPID(p, i, d);
    }

    public void setLeftPIDRateValues(double p, double i, double d){
        leftPIDRate.setPID(p, i, d);
    }

    //from kiwi code
    public void setPIDValue () {
        rightPIDRate.setPID(rightP, rightI, rightD);
        backPIDRate.setPID(backP, backI, backD);
        leftPIDRate.setPID(leftP, leftI, leftD);
    }

    public void setRightPIDRate(double rate) {
        rightPIDRate.setSetpoint(rate);
    }

    public void setBackPIDRate(double rate) {
        backPIDRate.setSetpoint(rate);
    }

    public void setLeftPIDRate(double rate) {
        leftPIDRate.setSetpoint(rate);
    }

    public void stopRatePIDs() {
        setRightPIDRate(0.0);
        setBackPIDRate(0.0);
        setLeftPIDRate(0.0);
        rightPIDRate.disable();
        backPIDRate.disable();
        leftPIDRate.disable();
    }

    public void initHoldPosition(){
        rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        backEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        rightPIDPosition.setPID(rightPositionP, rightPositionI, rightPositionD);
        backPIDPosition.setPID(backPositionP, backPositionI, backPositionD);
        leftPIDPosition.setPID(leftPositionP, leftPositionI, leftPositionD);
        rightPIDPosition.enable();
        backPIDPosition.enable();
        leftPIDPosition.enable();
    }

    public void holdPosition(){
        rightPIDPosition.setSetpoint(0.0);
        backPIDPosition.setSetpoint(0.0);
        leftPIDPosition.setSetpoint(0.0);
    }

    public void disablePositionPIDs(){
        rightPIDPosition.disable();
        backPIDPosition.disable();
        leftPIDPosition.disable();
        stopJags();
    }

    //gyro
    public void setFieldAdjustment(int angle){
        fieldAdjustment = -angle;
        gyroAdjustment = angle;
    }

    public double getFieldAdjustment(){
        return fieldAdjustment;
    }

    public boolean isGyroEnabled(){
        return gyroOn;
    }

    private double getGyro(){
        double angle = -gyro.getAngle();
        while(angle < 0){
            angle += 360;
        }
        while(angle > 360){
            angle -= 360;
        }
        return angle;
    }

    public void resetGyro(){
        gyroOn = true;
        //gyro.reset(); NOT WORKING! (solution below)
        gyroAdjustment = -getGyro();
        fieldAdjustment -= getGyro();
    }

    public void stopGyro(){
        gyroOn = false;
    }

    public double getGyroAngle(){
        double angle;
        angle = getGyro() + gyroAdjustment;
        while(angle < 360){
            angle += 360;
        }
        angle = angle % 360; //put angle between 0-360
        return angle;
    }

    //kiwi drive
    public void driveVoltage(double x, double y, double th, double scale,
            boolean gyro){
         double commandScale = Math.max(Math.abs(x),Math.abs(y));
        // scales command back to desired magnitude
        // y = 1 , x = 0, at gyro 45 degrees yields x = = y = root(3)/2.
        // this scales back to desired magnitude of x = y = 1.
        // untested, more code in if(gyro)


        y = -y;//these are backwards for some reason
        th = -th;//backwards because counterclockwise is positive
        if(gyro){
            double[] angleArray = getAngleAdjusted(x, y);
            x = angleArray[0];
            y = angleArray[1];
            double newCommandScale = Math.max(Math.abs(x),Math.abs(y));
            x = x * commandScale / newCommandScale;
            y = y * commandScale / newCommandScale;
        }
        double rightVoltage = getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), RIGHT_WHEEL)*scale;
        double backVoltage = getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), BACK_WHEEL)*scale;
        double leftVoltage = getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), LEFT_WHEEL)*scale;

        if(Math.abs(rightVoltage) > 1 || Math.abs(backVoltage) > 1 ||
                Math.abs(leftVoltage) > 1){
            double max = Math.abs(rightVoltage);
            if(max < Math.abs(backVoltage))
                max = Math.abs(backVoltage);
            if(max < Math.abs(leftVoltage))
                max = Math.abs(leftVoltage);
            rightVoltage = rightVoltage/max;
            backVoltage = backVoltage/max;
            leftVoltage = leftVoltage/max;
        }

     //   System.out.println("Right: " + rightVoltage + "\tRA: " + rightJag.get()
     //           + "\tBack: " + backVoltage + "\tBA: " + backJag.get()
     //           + "\tLeft: " + leftVoltage + "\tLA: " + leftJag.get());

        setRightJag(rightVoltage);
        setBackJag(backVoltage);
        setLeftJag(leftVoltage);

    }//end drive

    public void driveVelocity(double x, double y, double th, double scale, boolean gyro){
        //kiwi drive code does not have "double scale" or "boolean gyro"
        y = -y;
        th = -th;
        if(gyro){
            double[] angleArray = getAngleAdjusted(x, y);
            x = angleArray[0];
            y = angleArray[1];
        }
        setRightPIDRate(getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), RIGHT_WHEEL)*scale);
        setBackPIDRate(getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), BACK_WHEEL)*scale);
        setLeftPIDRate(getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), LEFT_WHEEL)*scale);
    }

    /**
     * Adjusts the given axis value to a different x & y orientation from the
     * gyro (whichever direction the robot is facing, the starting straight is
     * straight).
     * @param value joystick axis value
     * @return value adjusted for the new forward orientation on the robot
     */
    private double[] getAngleAdjusted(double x, double y){
        double theta = -getGyroAngle();
        double x2;
        double y2;
        theta = Math.toRadians(theta);
        x2 = Math.cos(theta)*x - Math.sin(theta)*y;
        y2 = Math.sin(theta)*x + Math.cos(theta)*y;
        //find which is the max percent
// ************** NOT WORKING SO COMMENTED OUT, TEST!!!!!!!!!!!!
//        boolean xMax = true;
//        if(Math.abs(y) > Math.abs(x)){
//            xMax = false;
//        }
//        //use it to scale the new values
//        if(xMax){
//            x2 = x2 * (x/x2);
//            y2 = y2 * (x/x2);
//        }else{
//            x2 = x2 * (y/y2);
//            y2 = y2 * (y/y2);
//        }
        double[] returnXY = {x2,y2};
        return returnXY;
    }

    private double getDeadzoneScaled(double value){
        if(value > DEADZONE_VALUE){
            return (value / (1 - DEADZONE_VALUE)) - DEADZONE_VALUE;
        }else if(value < -DEADZONE_VALUE){
            return (value / (1 - DEADZONE_VALUE)) + DEADZONE_VALUE;
        }else{
            return 0.0;
        }
    }

    private double getWheelVelocity(double x, double y, double th, int wheel) {

        //speed = dot product of robot's (x,y) & wheel (x,y)
        double speed = (x*wheelXs[wheel]) + (y*wheelYs[wheel]);

        double rotation = th * wheelRadii[wheel] * SCALE;

        if(manualRotationDisabled){//the rotate command is controlling rotation
            rotation = 0;
        }

        if(rotating){//voltage mode!
            rotation = SLOW_ROTATE * wheelRadii[wheel] * SCALE;
            if(negative){
                rotation *= -1.0;
            }
        }

        return speed + rotation;
    }

    public void startManualRotation(){//when drivers should control rotation
        manualRotationDisabled = false;
    }

    public void startAutoRotation(){
        manualRotationDisabled = true;
    }

    public void stopAutoRotation(){
        manualRotationDisabled = false;
        rotating = false;
    }

    //when drivers cannot control rotation, but there is no need to currently
    //rotate to a given point
    public void pauseAutoRotation(){
        rotating = false;
    }

    public boolean isAutoRotating(){
        return manualRotationDisabled;
    }

    public void autoRotateTestBot(double theta){
        rotating = true;
        //decide negative or positive
        if(theta > 0){
            System.out.println("positively");
            negative = false;
        }else{
            System.out.println("negatively");
            negative = true;
        }
    }

    @Override
    public void initDefaultCommand() {
    }//end initDefaultCommand
}//end Chassis subsystem
