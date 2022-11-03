package com.gos.preseason2023.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.swerve.SwerveModuleSim;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;

public class SwerveModule {

    private static final double TURNING_GEAR_RATION = (50.0 / 14.0) * (60.0 / 10.0);
    private static final double DRIVE_GEAR_RATION = (50.0 / 14.0) * (19.0 / 25.0) * (45.0 / 15.0);

    private static final double WHEEL_RADIUS = Units.inchesToMeters(4.0 / 2);

    private final SimableCANSparkMax m_spinMotor;
    private final SimableCANSparkMax m_powerMotor;

    private final RelativeEncoder m_spinEncoder;
    private final RelativeEncoder m_powerEncoder; // NOPMD

    private SwerveModuleSimWrapper m_simWrapper;

    public SwerveModule(int spinID, int powerID) {
        m_spinMotor = new SimableCANSparkMax(spinID, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_powerMotor = new SimableCANSparkMax(powerID, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_spinEncoder = m_spinMotor.getEncoder();
        m_powerEncoder = m_powerMotor.getEncoder();

        if (RobotBase.isSimulation()) {
            SwerveModuleSim moduleSim = new SwerveModuleSim(
                DCMotor.getNEO(1),
                DCMotor.getNEO(1),
                WHEEL_RADIUS,
                TURNING_GEAR_RATION,
                DRIVE_GEAR_RATION
            );

            m_simWrapper = new SwerveModuleSimWrapper(
                moduleSim,
                new RevMotorControllerSimWrapper(m_powerMotor),
                new RevMotorControllerSimWrapper(m_spinMotor),
                RevEncoderSimWrapper.create(m_powerMotor),
                RevEncoderSimWrapper.create(m_spinMotor));
        }
    }

    public SwerveModuleSimWrapper getSimWrapper() {
        return m_simWrapper;
    }

    public void goToState(SwerveModuleState state) {
        double error = state.angle.getDegrees() - m_spinEncoder.getPosition();
        if (error > 0) {
            m_spinMotor.set(0.5);
        }
        else {
            m_spinMotor.set(-.5);
        }

        m_powerMotor.set(state.speedMetersPerSecond);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(m_powerEncoder.getVelocity(), Rotation2d.fromDegrees(m_spinEncoder.getPosition()));
    }
}

