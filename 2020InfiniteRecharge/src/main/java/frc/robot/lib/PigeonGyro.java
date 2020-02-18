package frc.robot.lib;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class PigeonGyro implements IGyroWrapper {

    private final double[] m_angles = new double[3];

    private final PigeonIMU m_pigeon;

    public PigeonGyro(int deviceNumber)
    {
        m_pigeon = new PigeonIMU(deviceNumber);
    }

    @Override
    public void poll() {

        m_pigeon.getYawPitchRoll(m_angles);

    }

    @Override
    public double getYaw() {
        return m_angles[0];
    }

    @Override
    public void setYaw(double angle) {
        m_pigeon.setYaw(angle);
    }
}
