package org.usfirst.frc.team3335.util;

import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

public class CANTalon extends TalonSRX implements SpeedController{
    public CANTalon(int deviceNumber) {
        super(deviceNumber);
    }
}