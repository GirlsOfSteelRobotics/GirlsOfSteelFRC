package com.gos.rapidreact.subsystems;


import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class HangerSubsystem extends SubsystemBase {
    //these constants are all not correct
    public static final int ENGAGED_RATCHET_ANGLE = 90;
    public static final int DISENGAGED_RATCHET_ANGLE = 0;
    public static final double HANGER_UP_SPEED = 0.8;
    public static final double HANGER_DOWN_SPEED = -HANGER_UP_SPEED;

    private final Servo m_servo;
    private final SimableCANSparkMax m_leader;
    private final SimableCANSparkMax m_follower;

    private final RelativeEncoder m_encoder;


    public HangerSubsystem() {
        m_servo = new Servo(Constants.SERVO_CHANNEL);
        m_leader = new SimableCANSparkMax(Constants.HANGER_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leader.restoreFactoryDefaults();
        m_leader.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_follower = new SimableCANSparkMax(Constants.HANGER_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_follower.restoreFactoryDefaults();
        m_follower.setIdleMode(CANSparkMax.IdleMode.kBrake);


        m_follower.follow(m_leader, false);
        m_encoder = m_leader.getEncoder();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Hanger Height Encoder", m_encoder.getPosition());
    }

    public double getHangerSpeed() {
        return m_leader.get();
    }

    public double getHangerHeight() {
        return m_encoder.getPosition();
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



