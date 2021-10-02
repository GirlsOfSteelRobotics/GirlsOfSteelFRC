/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SmartDashboardNames;
import frc.robot.lib.properties.PidProperty;
import frc.robot.lib.properties.RevPidPropertyBuilder;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class ShooterSubsystem extends SubsystemBase {

    private final SimableCANSparkMax m_wheelMotor;
    private final CANEncoder m_encoder;
    private final CANPIDController m_pidController;
    private final PidProperty m_pidProperty;
    private ISimWrapper m_simulator;

    private final NetworkTableEntry m_motorSpeedEntry;
    private final NetworkTableEntry m_rpmEntry;
    private final NetworkTableEntry m_desiredRpmEntry;

    public ShooterSubsystem() {
        m_wheelMotor = new SimableCANSparkMax(Constants.CAN_SPINNING_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);
        m_encoder = m_wheelMotor.getEncoder();
        m_pidController = m_wheelMotor.getPIDController();

        m_pidProperty = RevPidPropertyBuilder.createBuilder("Shooter", false, m_pidController, 0)
                .addP(0)
                .addFF(0)
                .build();

        NetworkTable table = NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME + "/" + SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME);
        m_motorSpeedEntry = table.getEntry(SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED);
        m_rpmEntry = table.getEntry(SmartDashboardNames.SPINNING_WHEEL_RPM);
        m_desiredRpmEntry = table.getEntry(SmartDashboardNames.SPINNING_WHEEL_DESIRED_RPM);

        if (RobotBase.isSimulation()) {
            m_simulator = new FlywheelSimWrapper(Constants.FlywheelSimConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_wheelMotor),
                    RevEncoderSimWrapper.create(m_wheelMotor));
        }
    }

    public void setPercentOutput(double percentOutput) {
        m_wheelMotor.set(percentOutput);
    }

    public void spinAtRpm(double rpm) {
        m_desiredRpmEntry.setNumber(rpm);
        m_pidController.setReference(rpm, ControlType.kVelocity);
    }

    public double getRpm() {
        return m_encoder.getVelocity();
    }

    public boolean isAtRpm(double rpm) {
        return Math.abs(getRpm() - rpm) < 10;
    }

    public void stop() {
        m_wheelMotor.set(0);
    }

    @Override
    public void periodic() {
        m_pidProperty.updateIfChanged();
        m_motorSpeedEntry.setNumber(m_wheelMotor.get());
        m_rpmEntry.setNumber(getRpm());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

}
