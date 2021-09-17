    package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.GoSPIDController;
import girlsofsteel.objects.SmoothEncoder;

/**
 * Chassis subsystem for kiwi drive: creates jags, encoders, PID, & drive 
 * function. UNIT: inches
 */
public class Chassis extends Subsystem {
    
    //robot info
    public static final double WHEEL_DIAMETER = 6.0;
    private static final double GEAR_RATIO = 1.0;
    //scale for rotation
    public static final double SCALE = 0.04;
    public static final double DEADZONE_VALUE = 0.2;
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
    //the length of each wheel from the center
    public static final double RIGHT_WHEEL_ROBOT_RADIUS = 21.60873;
    public static final double BACK_WHEEL_ROBOT_RADIUS = 11.16635;
    public static final double LEFT_WHEEL_ROBOT_RADIUS = 21.60873;
    private double[] wheelXs = new double[3];
    private double[] wheelYs = new double[3];
    private double[] wheelRadii = new double[3];
    //encoder info
    public static final boolean REVERSE_DIRECTION = true;
    
    //create jags
    private Jaguar rightJag;
    private Jaguar backJag;
    private Jaguar leftJag;
    //create encoders
    private Encoder rightEncoder;
    private Encoder backEncoder;
    private Encoder leftEncoder;
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
    private GoSPIDController rightPIDRate;
    private GoSPIDController backPIDRate;
    private GoSPIDController leftPIDRate;
    private static final double rightP = 0.0;//CHANGE for real robot
    private static final double rightI = 0.0;//0.00005
    private static final double rightD = 0.0;//CHANGE for real robot
    private static final double backP = 0.0;//CHANGE for real robot
    private static final double backI = 0.0;//0.00005
    private static final double backD = 0.0;//CHANGE for real robot
    private static final double leftP = 0.0;//CHANGE for real robot
    private static final double leftI = 0.0;//0.00005
    private static final double leftD = 0.0;//CHANGE for real robot
    //gryo
    private Gyro gyro;
    private boolean gyroOn;

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
                    public void pidWrite(double output){
                        setRightJag(output);
                    }
                }, GoSPIDController.RATE);//integral constant?
        backPIDRate = new GoSPIDController(backP, backI, backD, backEncoder,
                new PIDOutput(){
                    public void pidWrite(double output){
                        setBackJag(output);
                    }
                }, GoSPIDController.RATE);//integral constant?
        leftPIDRate = new GoSPIDController(leftP, leftI, leftD, leftEncoder,
                new PIDOutput(){
                    public void pidWrite(double output){
                        setLeftJag(output);
                    }
                }, GoSPIDController.RATE);//integral constant?

       gyro = new Gyro(RobotMap.GYRO_PORT);
       

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
        rightEncoder.start();
        backEncoder.start();
        leftEncoder.start();
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
        return rightEncoder.getRate();//negative because jags are negative too
    }
    
    public double getBackEncoderRate(){
        return backEncoder.getRate();
    }
    
    public double getLeftEncoderRate(){
        return leftEncoder.getRate();
    }
    
    public void stopEncoders() {
        rightEncoder.stop();
        backEncoder.stop();
        leftEncoder.stop();
    }

    //PIDs
    public void initRatePIDs() {
        //for normal PID
//        rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
//        backEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
//        leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        //for GoS PID
        rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        backEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        rightPIDRate.enable();
        backPIDRate.enable();
        leftPIDRate.enable();
    }
    
    public void setRightPIDValues(double p, double i, double d){
        rightPIDRate.setPID(p, i, d);
        System.out.println("Setting P to " + p + " I to " + i + "D to " + d);
    }
    
    public void setBackPIDValues(double p, double i, double d){
        backPIDRate.setPID(p, i, d);
    }
    
    public void setLeftPIDValues(double p, double i, double d){
        leftPIDRate.setPID(p, i, d);
    }
    
    public void setPIDValues(){
        rightPIDRate.setPID(rightP, rightI, rightD);
        backPIDRate.setPID(backP, backI, backD);
        leftPIDRate.setPID(leftP, leftI, leftD);
    }
    
    public void setRightPIDRate(double rate) {
        rightPIDRate.setSetpoint(rate);
        System.out.println("Right setpoint is " + rightPIDRate.getSetPoint());
        System.out.println("Right rate is " + rightPIDRate.getRate());
    }

    public void setBackPIDRate(double rate) {
        backPIDRate.setSetpoint(rate);
    }

    public void setLeftPIDRate(double rate) {
        leftPIDRate.setSetpoint(rate);
    }

    public void stopPIDs() {
        setRightPIDRate(0.0);
        setBackPIDRate(0.0);
        setLeftPIDRate(0.0);
        rightPIDRate.disable();
        backPIDRate.disable();
        leftPIDRate.disable();
    }
    
    //gyro
    public boolean isGyroEnabled(){
        return gyroOn;
    }
    
    public void resetGyro(){
        gyroOn = true;
        gyro.reset();
    }
    
    public void stopGyro(){
        gyroOn = false;
    }
    
    public double getGyroAngle(){
        double angle;
        angle = gyro.getAngle();
        angle = angle % 360; //put angle between 0-360
        //angle will always be positive, so I took the line out
        return angle;
    }
    
    //kiwi drive
    public void driveVoltage(double x, double y, double th){
        y = -y;//these are backwards for some reason
        th = -th;//these are backwards for some reason
        if(gyroOn){
            double[] angleArray = getAngleAdjusted(x, y);
            x = angleArray[0];
            y = angleArray[1];
          //  System.out.println("X is " + x);
          //  System.out.println("Y is " + y);
        }
        double rightVoltage = getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), RIGHT_WHEEL);
        double backVoltage = getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), BACK_WHEEL);
        double leftVoltage = getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), LEFT_WHEEL);

        //System.out.println("X: " + x);
        //System.out.println("Y: " + y);
        
        //System.out.println("Right voltage: " + rightVoltage);
        //System.out.println("Back voltage: " + backVoltage);
        //System.out.println("Left voltage: " + leftVoltage);
        
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
        
        setRightJag(rightVoltage);
        setBackJag(backVoltage);
        setLeftJag(leftVoltage);
        
        //System.out.println("Right voltage is " + rightVoltage + "\tBack voltage"
          //      + " is " + backVoltage + "\tLeft voltage is " + leftVoltage);
        
    }//end drive

    public void driveVelocity(double x, double y, double th){
        y = -y;
        th = -th;
        if(gyroOn){
            double[] angleArray = getAngleAdjusted(x, y);
            x = angleArray[0];
            y = angleArray[1];
        }
        setRightPIDRate(getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), RIGHT_WHEEL));
        setBackPIDRate(getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), BACK_WHEEL));
        setLeftPIDRate(getWheelVelocity(getDeadzoneScaled(x),
                getDeadzoneScaled(y), getDeadzoneScaled(th), LEFT_WHEEL));
    }
    
    /**
     * Adjusts the given axis value to a different x & y orientation from the 
     * gyro (whichever direction the robot is facing, the starting straight is
     * straight).
     * @param value joystick axis value
     * @return value adjusted for the new forward orientation on the robot
     */
    private double[] getAngleAdjusted(double x, double y){
        double theta = getGyroAngle();
        System.out.println("Gyro TH: " + theta);
        double x2;
        double y2;
        theta = Math.toRadians(theta);
        x2 = Math.cos(theta)*x - Math.sin(theta)*y;
        y2 = Math.sin(theta)*x + Math.cos(theta)*y;
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
        
        //System.out.println("This is wheel " + wheel + "\tThe speed is " + speed
          //      + "\tThe rotation is " + th + "\tThe total velocity is " + 
            //    (speed + rotation));
        
        return speed + rotation;
    }

    public void initDefaultCommand() {
    }//end initDefaultCommand
}//end Chassis subsystem
