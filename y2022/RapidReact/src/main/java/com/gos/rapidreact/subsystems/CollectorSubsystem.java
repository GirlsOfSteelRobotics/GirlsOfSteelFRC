package com.gos.rapidreact.subsystems;

import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CollectorSubsystem extends SubsystemBase {
    private static final double ROLLER_SPEED = 0.5;
    private static final double PIVOT_SPEED = 0.5;

    private final SimableCANSparkMax m_roller;
    private final SimableCANSparkMax m_pivot;

    private final RelativeEncoder m_pivotEncoder;

    public CollectorSubsystem() {
        m_roller = new SimableCANSparkMax(Constants.COLLECTOR_ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivot = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_pivotEncoder = m_pivot.getEncoder();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pivot Encoder", m_pivotEncoder.getPosition());
    }

    public void collectorDown() {
        m_pivot.set(PIVOT_SPEED);
    }

    public void collectorUp() {
        m_pivot.set(-PIVOT_SPEED);
    }

    public void rollerIn() {
        m_roller.set(ROLLER_SPEED);
    }

    public void rollerOut() {
        m_roller.set(-ROLLER_SPEED);
    }

    public void rollerStop() {
        m_roller.set(0);
    }

    public void pivotStop() {
        m_pivot.set(0);
    }
}

