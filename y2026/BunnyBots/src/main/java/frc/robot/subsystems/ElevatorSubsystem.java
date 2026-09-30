package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;

public class ElevatorSubsystem {
    private final SparkFlex m_leader;
    private final SparkFlex m_follower;
    private final RelativeEncoder m_encoder;

    public ElevatorSubsystem() {
        m_leader = new SparkFlex(Constants.ELEVATOR_MOTOR_ID, MotorType.kBrushless);
        m_follower = new SparkFlex(Constants.ELEVATOR_FOLLOW_MOTOR_ID, MotorType.kBrushless);
        m_encoder = m_leader.getEncoder();
    }

    public double getHeight() {
        return m_encoder.getPosition();
    }

    public void setPower(double power) {
        m_leader.set(power);
    }

    public void clearStickyFaults() {
        m_leader.clearFaults();
        m_follower.clearFaults();
    }

    public void stop() {
        m_leader.set(0);
    }

    public double getEncoderVel() {
        return m_encoder.getVelocity();
    }


}
