package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
        private final SimableCANSparkMax m_pivotMotor;
        private final Solenoid m_firstStagePiston;
        private final Solenoid m_secondStagePiston;


        public ArmSubsystem() {
            m_pivotMotor = new SimableCANSparkMax(Constants.PIVOT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
            m_firstStagePiston = new Solenoid(Constants.FIRST_STAGE_PISTON, Solenoid);
            m_secondStagePiston = new Solenoid(Constants.SECOND_STAGE_PISTON, Solenoid);
        }

        public void pivotArmUp() {}
    }

