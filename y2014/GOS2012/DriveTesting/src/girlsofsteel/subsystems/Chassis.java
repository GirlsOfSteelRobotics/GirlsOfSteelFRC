package girlsofsteel.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.EncoderGoSPIDController;


public class Chassis extends Subsystem {
    
    private final double EPSILON = 0.1;
    
    private final double WHEEL_DISTANCE = 23953; //RANDOM CHANGE THIS -> NEED TO MEASURE IT OUT IN **METERS**
            //wheel distance is the radius of the circle the robot makes when it goes in a circle
    
    //create Jags
    private final Jaguar rightFrontJag = new Jaguar(RobotMap.RIGHT_FRONT_JAG);
    private final Jaguar rightBackJag = new Jaguar(RobotMap.RIGHT_BACK_JAG);
    private final Jaguar leftFrontJag = new Jaguar(RobotMap.LEFT_FRONT_JAG);
    private final Jaguar leftBackJag = new Jaguar(RobotMap.LEFT_BACK_JAG);

    //create stuff for encoders
    public Encoder rightEncoder = new Encoder(RobotMap.ENCODER_RIGHT_CHANNEL_A,
            RobotMap.ENCODER_RIGHT_CHANNEL_B,false,CounterBase.EncodingType.k4X);
    public Encoder leftEncoder = new Encoder(RobotMap.ENCODER_LEFT_CHANNEL_A,
            RobotMap.ENCODER_LEFT_CHANNEL_B, true,CounterBase.EncodingType.k4X);

    
    private final double WHEEL_DIAMETER = 0.1524;
    private final double GEAR_RATIO = 15.0/22.0;
    private final double PULSES = 250.0; //not sure -> change
    private final double ENCODER_UNIT = (WHEEL_DIAMETER*Math.PI*GEAR_RATIO)/PULSES;
    
    //create info for PIDs
    private final static double rightRateP = 0.1; //values for practice robot -> change for real
    private final static double rightRateI = 0.01; //change for real robot
    private final static double rightRateD = 0.0; //change for watson
    
    private final static double leftRateP = 0.1; //change for Watson
    private final static double leftRateI = 0.01; //cahnge for real robot (Watson)
    private final static double leftRateD = 0.0; //change from practice to real
    
    private final static double rightPositionP = 0.0; //no idea, please change when tuning
    private final static double rightPositionI = 0.0; //again, please change when you know the real values
    private final static double rightPositionD = 0.0; //please change when position control is tuned for the real robot
    
    private final static double leftPositionP = 0.0; //please change for position control tuned values
    private final static double leftPositionI = 0.0; //change for real position control values
    private final static double leftPositionD = 0.0; //we don't even have practive values for these, please change
    
    EncoderGoSPIDController rightRatePID = new EncoderGoSPIDController(rightRateP,
            rightRateI, rightRateD, rightEncoder,
            //this is an anonymous class, it lets us send values to both jags
            //new output parameter
            new PIDOutput() {
                public void pidWrite(double output) {
                    setRightJags(output);
                }
            }, EncoderGoSPIDController.RATE);
    
    EncoderGoSPIDController leftRatePID = new EncoderGoSPIDController(leftRateP,
            leftRateI, leftRateD, leftEncoder,
            //this is an anonymous class, it lets us send values to both jags
            //new output parameter
            new PIDOutput() {
                public void pidWrite(double output) {
                    setLeftJags(output);
                }
            }, EncoderGoSPIDController.RATE);
    
    EncoderGoSPIDController rightPositionPID = new EncoderGoSPIDController(
            rightPositionP,rightPositionI,rightPositionD,rightEncoder,
            new PIDOutput(){
                public void pidWrite(double output){
                    setRightJags(output);
                }
            }, EncoderGoSPIDController.POSITION);
    
    EncoderGoSPIDController leftPositionPID = new EncoderGoSPIDController(
            leftPositionP,leftPositionI,leftPositionD,leftEncoder,
            new PIDOutput(){
                public void pidWrite(double output){
                    setLeftJags(output);
                }
            },EncoderGoSPIDController.POSITION);
    
    public Chassis() {
    }
    
    protected void initDefaultCommand() {    
    }
    
    //jags
    private void setRightJags(double speed){
        rightFrontJag.set(speed);
        rightBackJag.set(speed);
    }
    
    private void setLeftJags(double speed){
        speed = speed*-1;//CHANGE FOR A DIFFERENT ROBOT -- ONLY THE PRACTICE ONE WITH WEIGHT
        leftFrontJag.set(speed);
        leftBackJag.set(speed);
    }
    
    public void stopJags(){
        setRightJags(0.0);
        setLeftJags(0.0);
    }
    
    //driving
    
    private static final double MAX_RATE = 15;
    
    public void driveJagsSquared(double xAxis, double yAxis, double deadzoneRange,
            double scale){
        
        xAxis = square(deadzone(xAxis,deadzoneRange),scale);
        yAxis = -square(deadzone(yAxis,deadzoneRange),scale);
        
        setRightJags((yAxis - xAxis));
        setLeftJags((yAxis + xAxis));
        
    }

    public void driveJagsLinear(double xAxis, double yAxis, double deadzoneRange) {

        xAxis = deadzone(xAxis,deadzoneRange);
        yAxis = -deadzone(yAxis,deadzoneRange);

        setRightJags((yAxis - xAxis));
        setLeftJags((yAxis + xAxis));

    }
        
    public void driveJagsSqrt(double xAxis, double yAxis, double deadzoneRange,
        double scale){
        
        xAxis = sqrt(deadzone(xAxis,deadzoneRange),scale);
        yAxis = -sqrt(deadzone(yAxis,deadzoneRange),scale);
        
        setRightJags((yAxis - xAxis));
        setLeftJags((yAxis + xAxis));
        
    }
    
    public void driveVelocitySquared(double xAxis, double yAxis, double deadzoneRange,
            double scale){
        
        double maxRate = Math.sqrt(MAX_RATE);
        
        xAxis = square(mappingJoystickToRate(deadzone(xAxis,deadzoneRange), maxRate),scale);
        yAxis = -square(mappingJoystickToRate(deadzone(yAxis,deadzoneRange), maxRate),scale);
 
        double setpointX = yAxis - xAxis;
        double setpointY = yAxis + xAxis;
        
        rightRatePID.setSetPoint(setpointX);
        leftRatePID.setSetPoint(setpointY);
        
    }

    public void driveVelocityLinear(double xAxis, double yAxis, double deadzoneRange){
        
        xAxis = mappingJoystickToRate(deadzone(xAxis,deadzoneRange), MAX_RATE);
        yAxis = -mappingJoystickToRate(deadzone(yAxis,deadzoneRange), MAX_RATE);
 
        rightRatePID.setSetPoint((yAxis - xAxis));
        leftRatePID.setSetPoint((yAxis + xAxis));
   
    }    
    
    public void driveVelocitySqrt(double xAxis, double yAxis, double deadzoneRange,
            double scale){
        
        xAxis = sqrt(mappingJoystickToRate(deadzone(xAxis,deadzoneRange), MAX_RATE),scale);
        yAxis = -sqrt(mappingJoystickToRate(deadzone(yAxis,deadzoneRange), MAX_RATE),scale);
 
        rightRatePID.setSetPoint((yAxis - xAxis));
        leftRatePID.setSetPoint((yAxis + xAxis));
   
    }
    
    private double square(double joystickValue, double scale){
        double newJoystickValue;
        
        if(joystickValue > 0){
            newJoystickValue = scale*(joystickValue*joystickValue);
        }else{
            newJoystickValue = -scale*(joystickValue*joystickValue);
        }
        return newJoystickValue;
    }
    
    private double sqrt(double joystickValue, double scale){
        double newJoystickValue;
        
        if(joystickValue > 0){
            newJoystickValue = scale*Math.sqrt(joystickValue);
        }else{
            newJoystickValue = -scale*Math.sqrt(Math.abs(joystickValue));
        }
        return newJoystickValue;
    }

    /**if the joystickValue in the range of the joystick where we want it to not move
     * then the new joystick value is the scaled by the deadZoneRange slope
     * @return newJoystickValue
     * @param double joystickValue, @param double deadZoneRange
     */
    private double deadzone(double joystickValue, double deadZoneRange) {
        double newJoystickValue = 0.0;
        if (joystickValue > deadZoneRange) {
            newJoystickValue = (joystickValue/ (1 - deadZoneRange))-deadZoneRange;
        } else if (joystickValue < (-1 * deadZoneRange)) {
            newJoystickValue = (joystickValue/ (1 - deadZoneRange))+deadZoneRange;
        }else{
            newJoystickValue = 0.0;
        }
              
        return newJoystickValue;
    }
    
    private double mappingJoystickToRate(double joystickValue, double maxRate){
        double newJoystickValue;
        
        newJoystickValue = joystickValue*maxRate;
        
        return newJoystickValue;
    }

    public void move(double distance){
        double setPoint = MathUtils.pow(GEAR_RATIO, -1)*distance;
        rightPositionPID.setSetPoint(setPoint);
        leftPositionPID.setSetPoint(setPoint);
    }
    
    public void turn(double degreesToTurn){
        double setPoint = convertDegreesToPositionChange(degreesToTurn);
        if(setPoint > 0){
            rightPositionPID.setSetPoint(-setPoint);
            leftPositionPID.setSetPoint(setPoint);
        }else if(setPoint < 0){
            rightPositionPID.setSetPoint(setPoint);
            leftPositionPID.setSetPoint(-setPoint);
        }
    }
    
    public void goToLocation(double xDistance, double yDistance){
        double degreesToTurn = Math.tan(xDistance/yDistance)*180/Math.PI;
        double distance = Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
        turn(degreesToTurn);
        move(distance);
    }
    
    private double convertDegreesToPositionChange(double degreesToTurn){
        double positionChange;
        positionChange = degreesToTurn*WHEEL_DISTANCE*Math.PI/360;
        return positionChange;
    }
    
    public boolean isMoveFinished(double distanceToMove){
        double setPoint = MathUtils.pow(GEAR_RATIO, -1)*distanceToMove;
        if(isWithinRange(getRightEncoderDistance(),setPoint)
                && isWithinRange(getLeftEncoderDistance(),setPoint)){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isTurnFinished(double degreesToTurn){
        double setPoint = convertDegreesToPositionChange(degreesToTurn);
        if( isWithinRange(getRightEncoderDistance(),setPoint)
                && isWithinRange(getLeftEncoderDistance(),setPoint) ){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean isWithinRange(double point, double target){
        if(point > target-EPSILON || point < target+EPSILON){
            return true;
        }else{
            return false;
        }
    }
    
    //PID
    public void initRightRatePID() {
        rightRatePID.setSetPoint(0.0);
        rightRatePID.enable();
    }
    
    public void initLeftRatePID() {
        leftRatePID.setSetPoint(0.0);
        leftRatePID.enable();
    }
    
    public void initLeftPositionPID(){
        leftPositionPID.setSetPoint(0.0);
        leftPositionPID.enable();
    }

    public void initRightPositionPID(){
        rightPositionPID.setSetPoint(0.0);
        rightPositionPID.enable();
    }
    
    public void setRightRatePID(double setPoint){
        rightRatePID.setSetPoint(setPoint);
    }
    
    public void setLeftRatePID(double setPoint){
        leftRatePID.setSetPoint(setPoint);
    }
    
    public void setRightPositionPID(double setPoint){
        rightPositionPID.setSetPoint(setPoint);
    }
    
    public void setLeftPositionPID(double setPoint){
        leftPositionPID.setSetPoint(setPoint);
    }
    
    public void disableRightRatePID(){
        rightRatePID.disable();
    }
    
    public void disableLeftRatePID(){
        leftRatePID.disable();
    }
    
    public void disableRightPositionPID(){
        rightPositionPID.disable();
    }
    
    public void disableLeftPositionPID(){
        leftPositionPID.disable();
    }
    
    //encoder    
    public void initRightEncoder() {
        rightEncoder.setDistancePerPulse(ENCODER_UNIT);
        rightEncoder.start();
    }

    public void initLeftEncoder(){
        leftEncoder.setDistancePerPulse(ENCODER_UNIT);
        leftEncoder.start();
    }
    
    public double getRightEncoderDistance() {
        return rightEncoder.getDistance();
    }
    
    public double getLeftEncoderDistance(){
        return leftEncoder.getDistance();
    }
    
    public void endRightEncoder() {
        rightEncoder.stop();
    }
    
    public void endLeftEncoder(){
        leftEncoder.stop();
    }
    
}