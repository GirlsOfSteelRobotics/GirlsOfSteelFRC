package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Manipulator extends Subsystem {
    private final CANTalon m_collectRight;
    private final CANTalon m_collectLeft;

    private final DoubleSolenoid m_pusher;

    private final CANTalon m_pivotA;
    private final CANTalon m_pivotB;

    public Manipulator() {
        m_collectRight = new CANTalon(RobotMap.COLLECT_RIGHT);
        m_collectLeft = new CANTalon(RobotMap.COLLECT_LEFT);

        m_pusher = new DoubleSolenoid(0, 1);

        m_pivotA = new CANTalon(RobotMap.PIVOT_RIGHT);
        m_pivotB = new CANTalon(RobotMap.PIVOT_LEFT);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void collectIn(double speed){
        m_collectRight.set(speed);
        m_collectLeft.set(speed);
    }

    public void pusherOut(){
        m_pusher.set(DoubleSolenoid.Value.kForward);
    }

    public void pusherIn(){
        m_pusher.set(DoubleSolenoid.Value.kReverse);
    }

    public void pivotUp(double speed){
        System.out.println("Pivot Up Speed" + speed);
        m_pivotA.set(-speed);
        m_pivotB.set(-speed);
    }

    public void pivotDown(double speed){
        System.out.println("Pivot Down Speed" + speed);
        m_pivotA.set(speed);
        m_pivotB.set(speed);
    }

    public void stopCollector() {
        m_collectRight.set(0);
        m_collectLeft.set(0);
    }

    public void stopPivot(){
        m_pivotA.set(0);
        m_pivotB.set(0);
    }
}
