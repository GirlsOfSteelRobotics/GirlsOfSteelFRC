/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.codelabs.basic_simulator.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.codelabs.basic_simulator.Constants;
import com.gos.codelabs.basic_simulator.SmartDashboardNames;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class SpinningWheelSubsystem extends SubsystemBase {

    private final SimableCANSparkMax m_wheelMotor;
    private final CANPIDController m_pidController;
    private ISimWrapper m_simulator;

    private final NetworkTableEntry m_motorSpeedEntry;

    public SpinningWheelSubsystem() {
        m_wheelMotor = new SimableCANSparkMax(Constants.CAN_SPINNING_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);
        m_pidController = m_wheelMotor.getPIDController();

        NetworkTable table = NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME + "/" + SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME);
        m_motorSpeedEntry = table.getEntry(SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED);

        if (RobotBase.isSimulation()) {
            m_simulator = new FlywheelSimWrapper(Constants.FlywheelSimConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_wheelMotor),
                    RevEncoderSimWrapper.create(m_wheelMotor));
        }
    }

    public void spinAtRpm(double rpm) {
        // TODO implement
    }

    @Override
    public void periodic() {
        m_motorSpeedEntry.setNumber(m_wheelMotor.get());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}
