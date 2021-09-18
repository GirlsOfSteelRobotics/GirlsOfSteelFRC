package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import girlsofsteel.RobotMap;

public class Chassis extends Subsystem {

    public static final double DEADZONE_RANGE = 0.1;//can be changed
    
    //create jags
    private final Jaguar rightJagFront = new Jaguar(RobotMap.RIGHT_JAG_FRONT);
    private final Jaguar rightJagBack = new Jaguar(RobotMap.RIGHT_JAG_BACK);
    private final Jaguar leftJagFront = new Jaguar(RobotMap.LEFT_JAG_FRONT);
    private final Jaguar leftJagBack = new Jaguar(RobotMap.LEFT_JAG_BACK);
   
    //create encoder objects
    private static final boolean REVERSE_DIRECTION = true;
    private Encoder rightEncoder = new Encoder(RobotMap.ENCODER_RIGHT_CHANNEL_A,
            RobotMap.ENCODER_RIGHT_CHANNEL_B, !REVERSE_DIRECTION,
            CounterBase.EncodingType.k4X);//check reverse direction
    private Encoder leftEncoder = new Encoder(RobotMap.ENCODER_LEFT_CHANNEL_A,
            RobotMap.ENCODER_LEFT_CHANNEL_B, !REVERSE_DIRECTION,
            CounterBase.EncodingType.k4X);//check reverse direction
    //measurements to calculate the units for the encoders
    private final double WHEEL_DIAMETER = 6.0; //inches
    private final double GEAR_RATIO = 12.0/26.0; //teeth on small/teeth on big
    private final double PULSES_RIGHT = 360.0; //change for real
    private final double PULSES_LEFT = 360.0; //change for real
    //calculate the units for the encoders
    private final double ENCODER_UNIT_RIGHT = (WHEEL_DIAMETER * Math.PI
            * GEAR_RATIO) / PULSES_RIGHT;
    private final double ENCODER_UNIT_LEFT = (WHEEL_DIAMETER * Math.PI
            * GEAR_RATIO) / PULSES_LEFT;
    //PID information
    private final static double P_RATE_RIGHT = 0.0;//change for real
    private final static double I_RATE_RIGHT = 0.0;//change for real
    private final static double D_RATE_RIGHT = 0.0;//change for real
    private final static double P_RATE_LEFT = 0.0;//change for real
    private final static double I_RATE_LEFT = 0.0;//change for real
    private final static double D_RATE_LEFT = 0.0;//change for real
    //create PID controller
    private PIDController rightRatePID = new PIDController(P_RATE_RIGHT,
            I_RATE_RIGHT, D_RATE_RIGHT, rightEncoder,
            new PIDOutput(){
                public void pidWrite(double output){
                    setRightJags(output);
                }
            });
    private PIDController leftRatePID = new PIDController(P_RATE_LEFT,
            I_RATE_LEFT, D_RATE_LEFT, leftEncoder,
            new PIDOutput(){
                public void pidWrite(double output){
                    setLeftJags(output);
                }
            });
    
    //jags:
            
    /**
     * Send a speed to the right jags.
     * @param speed value to send to the jags (-1 to 1)
     */
    public void setRightJags(double speed){
        rightJagFront.set(speed);
        rightJagBack.set(speed);
    }//end setRightJags
    
    /**
     * Send a speed to the left jags.
     * @param speed value to send to the jags (-1 to 1)
     */
    public void setLeftJags(double speed){
        leftJagFront.set(-speed);
        leftJagBack.set(-speed);
    }//end setRightJags
    
    /**
     * Sets the jags to 0 (stops them).
     */
    public void stopJags(){
        setRightJags(0.0);
        setLeftJags(0.0);
    }//end stopJags
    
    //drive with jags:
    
    /**
     * Moves the robot based on axis values. Can be adjusted for arcade or tank
     * drive, squared or linear, deadzone or not.
     * @param xAxis x axis value from operator
     * @param yAxis y axis value from operator
     */
    public void driveJags(double xAxis, double yAxis){
        setRightJags(getDeadzoneValue(yAxis) - getDeadzoneValue(xAxis));
        setLeftJags(getDeadzoneValue(yAxis) + getDeadzoneValue(xAxis));
    }//end driveJags
    
    public void driveJagsTank(double right, double left){
        setRightJags(getDeadzoneValue(right));
        setLeftJags(getDeadzoneValue(left));
    }//end driveJagsTank
    
    //encoders:
    
    /**
     * Sets the unit for the encoders & starts them.
     */
    public void initEncoders(){
        rightEncoder.setDistancePerPulse(ENCODER_UNIT_RIGHT);
        leftEncoder.setDistancePerPulse(ENCODER_UNIT_LEFT);
        rightEncoder.start();
        leftEncoder.start();
    }//end initEncoders
    
    /**
     * Stops the encoders.
     */
    public void endEncoders(){
        rightEncoder.stop();
        leftEncoder.stop();
    }//end endEncoders
    
    /**
     * Gets the right encoder distance value.
     * @return right encoder value for the distance from the starting point
     */
    public double getRightEncoderDistance(){
        return rightEncoder.getDistance();
    }//end getRightEncoderDistace
    
    /**
     * Gets the left encoder distance value.
     * @return left encoder value for the distance from the starting point
     */
    public double getLeftEncoderDistance(){
        return leftEncoder.getDistance();
    }//end getLeftEncoderDistance
    
    //PID:
    
    /**
     * Sets the setpoint for the PIDs to 0 & starts them.
     */
    public void initPID(){
        rightRatePID.setSetpoint(0.0);
        leftRatePID.setSetpoint(0.0);
        rightRatePID.enable();
        leftRatePID.enable();
    }//end initPID
    
    /**
     * Sets the P, I, & D values for the right rate PID controller.
     * Does not change the values stored in the class.
     * @param p position value
     * @param i integral value
     * @param d derivative value
     */
    public void setRightPIDValues(double p, double i, double d){
        rightRatePID.setPID(p, i, d);
    }//end setRightPIDValues
    
    /**
     * Sets the P, I, & D values for the left rate PID controller.
     * Does not change the values stored in the class.
     * @param p position value
     * @param i integral value
     * @param d derivative value
     */
    public void setLeftPIDValues(double p, double i, double d){
        leftRatePID.setPID(p, i, d);
    }//end setRightPIDValues
    
    /**
     * Sets the setpoint of the right rate PID.
     */
    public void setRightPIDRate(double setpoint){
        rightRatePID.setSetpoint(setpoint);
    }//end setRightPIDRate
    
    /**
     * Sets the setpoint of the left rate PID.
     */
    public void setLeftPIDRate(double setpoint){
        leftRatePID.setSetpoint(setpoint);
    }//end setLeftPIDRate
    
    /**
     * Sets the PID setpoint to 0 (stops them).
     */
    public void stopPID(){
        rightRatePID.setSetpoint(0);
        leftRatePID.setSetpoint(0);
    }//end stop
    
    public void drivePID(double xAxis, double yAxis){
        rightRatePID.setSetpoint(getDeadzoneValue(yAxis) - 
                getDeadzoneValue(xAxis));
        leftRatePID.setSetpoint(getDeadzoneValue(yAxis) +
                getDeadzoneValue(xAxis));
    }//end drive
    
    /**
     * Adjust joystick values to the deadzone. Anything between the deadzone
     * returns 0, and anything outside of that is shifted so 0 = deadzone
     * threshold value & stretched so 1 still equals 1.
     * @param joystickValue axis value from the joystick
     * @return adjusted value
     */
    private double getDeadzoneValue(double joystickValue){
        if (joystickValue > DEADZONE_RANGE) {
            return (joystickValue / (1 - DEADZONE_RANGE)) - DEADZONE_RANGE;
        } else if (joystickValue < (-1 * DEADZONE_RANGE)) {
            return (joystickValue / (1 - DEADZONE_RANGE)) + DEADZONE_RANGE;
        } else {
            return 0.0;
        }//end else
    }//end getDeadzoneValue
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }//end initDefaultCommand
    
}//end Chassis class