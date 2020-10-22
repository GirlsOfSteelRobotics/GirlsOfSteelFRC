/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SmartDashboardNames;

public class SpinningWheelSubsystem extends SubsystemBase {

    private final SpeedController m_wheelMotor;

    private final NetworkTableEntry m_motorSpeedEntry;

    public SpinningWheelSubsystem() {
        m_wheelMotor = new CANSparkMax(Constants.CAN_SPINNING_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);

        NetworkTable table = NetworkTableInstance.getDefault().getTable(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME + "/" + SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME);
        m_motorSpeedEntry = table.getEntry(SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED);
    }

    @Override
    public void periodic() {
        // TODO implement
    }
}
