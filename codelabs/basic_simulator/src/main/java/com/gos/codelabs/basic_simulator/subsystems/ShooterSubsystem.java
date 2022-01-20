/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.codelabs.basic_simulator.subsystems;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.codelabs.basic_simulator.Constants;
import com.gos.codelabs.basic_simulator.SmartDashboardNames;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class ShooterSubsystem extends SubsystemBase {

    private final SimableCANSparkMax m_wheelMotor;
    private final SparkMaxPIDController m_pidController;
    private ISimWrapper m_simulator;

    public ShooterSubsystem() {
        m_wheelMotor = new SimableCANSparkMax(Constants.CAN_SPINNING_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);
        m_pidController = m_wheelMotor.getPIDController();

        NetworkTable table = NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME + "/" + SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME);

        if (RobotBase.isSimulation()) {
            m_simulator = new FlywheelSimWrapper(Constants.FlywheelSimConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_wheelMotor),
                    RevEncoderSimWrapper.create(m_wheelMotor));
        }
    }

    public void setPercentOutput(double percentOutput) {
        // TODO implement
    }

    public void spinAtRpm(double rpm) {
        // TODO implement
    }

    public double getRpm() {
        // TODO implement
        return 0;
    }

    public boolean isAtRpm(double rpm) {
        // TODO implement
        return false;
    }

    public void stop() {
        // TODO implement
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}
