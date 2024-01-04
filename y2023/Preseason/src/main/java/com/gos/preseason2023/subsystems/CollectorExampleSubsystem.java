package com.gos.preseason2023.subsystems;


import com.gos.preseason2023.Constants;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CollectorExampleSubsystem extends SubsystemBase {

    // create object for motor controllers
    private final SimableCANSparkMax m_pivotLeft;
    private final SimableCANSparkMax m_pivotRight;
    private final SimableCANSparkMax m_roller;

    public CollectorExampleSubsystem() {
        // set to CAN IDs (constants), motor type is brushless
        m_pivotLeft = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT_LEFT, CANSparkLowLevel.MotorType.kBrushless);
        m_pivotRight = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT_RIGHT, CANSparkLowLevel.MotorType.kBrushless);
        m_roller = new SimableCANSparkMax(Constants.COLLECTOR_ROLLER, CANSparkLowLevel.MotorType.kBrushless);

        // m_pivotLeft.setInverted(true);      fyi we're not using because it's funky with following

        // left motor will always follow what the write motor is doing
        m_pivotRight.follow(m_pivotLeft, true);
    }

    public void movePivot(double speed) {
        m_pivotLeft.set(speed);
    }

    public void moveRoller(double speed) {
        m_roller.set(speed);
    }

}
