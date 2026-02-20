package com.gos.lib.led.driverstation;

import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverStationLedDriver {

    public enum BitField {
        ARDUINO_BIT_0(24),
        ARDUINO_BIT_1(25),
        ARDUINO_BIT_2(26),
        ARDUINO_BIT_3(27),
        ARDUINO_BIT_4(28),
        ARDUINO_BIT_5(29),
        ARDUINO_BIT_6(30),
        ARDUINO_BIT_7(31),

        ARDUINO_BIT_8(8),
        ARDUINO_BIT_9(9),
        ARDUINO_BIT_10(10),
        ARDUINO_BIT_11(11),
        ARDUINO_BIT_12(12),
        ARDUINO_BIT_13(13),
        ARDUINO_BIT_14(14),
        ARDUINO_BIT_15(15);

        public final int m_bitNumber;
        public final int m_mask;

        BitField(int bitNumber) {
            m_bitNumber = bitNumber;
            m_mask = 1 << bitNumber;
        }
    }

    private byte m_port;

    private int m_data;

    public DriverStationLedDriver(int port) {
        m_port = (byte) port;
    }

    public void clear() {
        m_data = 0;
    }

    /**
     * "Inserts" an integer value into the strip mask. You need to be careful
     * that the value can fit within numBits, and that the bits don't
     * cross over the upper / lower byte divide (i.e. keep it arduino bits 0-7 or 8-15
     *
     * @param value The value of the integer
     * @param startBit The bit number to start at
     * @param numBits The number of bits used for this field
     */
    public void setInt(int value, int startBit, int numBits) {
        int slotMask = (int) ((1L << numBits) - 1);
        int cleanValue = value & slotMask;
        int clearMask = ~(slotMask << startBit);

        this.m_data = (this.m_data & clearMask) | (cleanValue << startBit);
    }

    public void setBit(BitField bit, boolean value) {
        if (value) {
            m_data |= bit.m_mask;
        } else {
            m_data &= ~bit.m_mask;
        }
    }

    public void setBit(int i, boolean value) {
        if (value) {
            m_data |= (1 << i);
        } else {
            m_data &= ~(1 << i);
        }
    }

    public void setData(short data) {
        m_data = data;
    }

    public void write() {
        short outputs = 0;
        short leftRumble = (short) (m_data & 0xFFFF);
        short rightRumble = (short) ((m_data >> 16) & 0xFFFF);

        if (RobotBase.isReal()) {
            DriverStationJNI.setJoystickOutputs(m_port, outputs, leftRumble, rightRumble);
        } else  {
            DriverStationJNI.setJoystickOutputs(m_port, outputs, rightRumble, leftRumble);
        }


        SmartDashboard.putNumber("LED Data", m_data);
        SmartDashboard.putNumber("LED Right", rightRumble);
        SmartDashboard.putNumber("LED Left", leftRumble);
    }
}
