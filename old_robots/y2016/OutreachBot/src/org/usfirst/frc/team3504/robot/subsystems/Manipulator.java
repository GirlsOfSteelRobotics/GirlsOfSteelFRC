package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Manipulator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final CANTalon pivotMotor;
    private final CANTalon collectorMotor;
    private final DoubleSolenoid shooter; //shoots the ball
    private final DoubleSolenoid arm; //opens and closes the arm to get the ball in

    public Manipulator() {
        pivotMotor = new CANTalon(RobotMap.ARM_PIVOT_CAN_ID);
        collectorMotor = new CANTalon(RobotMap.COLLECTOR_CAN_ID);
        shooter = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_A, RobotMap.SHOOTER_PISTON_B);
        arm = new DoubleSolenoid(RobotMap.ARM_PISTON_A, RobotMap.ARM_PISTON_B);
    }

    public void openArm(){
        arm.set(DoubleSolenoid.Value.kForward);
    }

    public void closeArm(){
        arm.set(DoubleSolenoid.Value.kReverse);
    }

    public void shootBall() {
        shooter.set(DoubleSolenoid.Value.kReverse);
    }

    public void shooterIn() {
        shooter.set(DoubleSolenoid.Value.kForward);
    }

    public void collectBall() {
        collectorMotor.set(-1);
    }

    public void releaseBall() {
        collectorMotor.set(1);
    }

    public void stopCollecting() {
        collectorMotor.set(0);
    }

    public void pivotUp() {
        pivotMotor.set(1);
    }

    public void pivotDown() {
        pivotMotor.set(-1);
    }

    public void stopPivot() {
        pivotMotor.set(0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public boolean getTopLimitSwitch(){
        return pivotMotor.isFwdLimitSwitchClosed() == 0;
    }

    public boolean getBottomLimitSwitch(){
        return collectorMotor.isRevLimitSwitchClosed() == 0;
    }
}
