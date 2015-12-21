package girlsofsteel.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.RobotMap;
import girlsofsteel.objects.GoSPIDController;

public class Chassis extends Subsystem {

    private final Jaguar rightFront = new Jaguar(RobotMap.rightFrontJag);
    private final Jaguar rightBack = new Jaguar(RobotMap.rightBackJag);
    private final Jaguar leftBack = new Jaguar(RobotMap.leftBackJag);
    private final Jaguar leftFront = new Jaguar(RobotMap.leftFrontJag);
    
    //a & b are 1 & 2 -> they might be wrong, but only switched
    int rightAChannel = 4;
    int rightBChannel = 3;
    public Encoder rightEncoder = new Encoder(rightAChannel, rightBChannel, false,
            CounterBase.EncodingType.k4X);
    
    int leftAChannel = 2;
    int leftBChannel = 1;
    public Encoder leftEncoder = new Encoder(leftAChannel, leftBChannel, true,
            CounterBase.EncodingType.k4X);

    public Chassis() {
        //SmartDashboard.putData("PID", PID);
        //SmartDashboard tunes PID, sets SetPoint, & allows enable/disable
    }
    
   public double getRightRate (){
        return rightEncoder.getRate();
    }
   
   public double getLeftRate(){
       return leftEncoder.getRate();
   }
    
    public void setRight(double rightSpeed){
        rightFront.set(rightSpeed);
        rightBack.set(rightSpeed);
    }
    
    public void setLeft(double leftSpeed){
        leftFront.set(-1*leftSpeed);
        leftBack.set(-1*leftSpeed);
    }
    
    public void spinRightMotor(Joystick joystick) {
        //rightFront.set(deadzone(joystick.getY(), 0.15));
        rightBack.set(deadzone(joystick.getY(), 0.15));
    }
    

    /**if the joystickValue in the range of the joystick where we want it to not move
     * then the new joystick value is the scaled by the deadZoneRange slope
     * @return newJoystickValue
     * @param double joystickValue, @param double deadZoneRange
     */
    private double deadzone(double joystickValue, double deadZoneRange) {
        double newJoystickValue = 0.0;
        if (joystickValue > deadZoneRange) {
            newJoystickValue = (joystickValue - deadZoneRange) * (1 / (1 - deadZoneRange));
        } else if (joystickValue < (-1 * deadZoneRange)) {
            newJoystickValue = (joystickValue + deadZoneRange) * (1 / (1 - deadZoneRange));
        }
              
        return newJoystickValue;
    }

    public void initRightEncoder(double unit) {
        rightEncoder.setDistancePerPulse(unit);
        rightEncoder.start();
    }
    
    public void initLeftEncoder(double unit){
        leftEncoder.setDistancePerPulse(unit);
        leftEncoder.start();
    }

    public double rightEncoderDistance() {
        return rightEncoder.getDistance();
    }
    
    public double leftEncoderDistance(){
        return leftEncoder.getDistance();
    }
    
    public void endRightEncoder() {
        rightEncoder.stop();
    }
    
    public void endLeftEncoder(){
        leftEncoder.stop();
    }

    protected void initDefaultCommand() {
    }
}