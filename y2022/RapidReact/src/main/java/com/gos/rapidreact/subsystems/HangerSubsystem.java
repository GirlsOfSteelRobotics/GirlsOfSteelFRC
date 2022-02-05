package com.gos.rapidreact.subsystems;


import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class HangerSubsystem extends SubsystemBase {
    //these constants are all not correct
    public static final int ENGAGED_RATCHET_ANGLE = 90;
    public static final int DISENGAGED_RATCHET_ANGLE = 0;

    private final Servo m_servo;
    private final SimableCANSparkMax m_leader;
    private final SimableCANSparkMax m_follower;


    public HangerSubsystem() {
        m_servo = new Servo(Constants.SERVO_CHANNEL);
        m_leader = new SimableCANSparkMax(Constants.HANGER_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_follower = new SimableCANSparkMax(Constants.HANGER_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_follower.follow(m_leader, false);

    }

    public void setHangerSpeed(double speed) {
        m_leader.set(speed);
    }

    public void engageRatchet() {
        m_servo.setAngle(ENGAGED_RATCHET_ANGLE);
    }

    public void disengageRatchet() {
        m_servo.setAngle(DISENGAGED_RATCHET_ANGLE);
    }

}



