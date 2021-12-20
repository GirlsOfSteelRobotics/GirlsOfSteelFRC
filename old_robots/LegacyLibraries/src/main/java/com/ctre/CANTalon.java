package com.ctre;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

@SuppressWarnings("PMD.GodClass")
public class CANTalon extends WPI_TalonSRX {

    public CANTalon(int deviceNumber) {
        super(deviceNumber);
    }
}
