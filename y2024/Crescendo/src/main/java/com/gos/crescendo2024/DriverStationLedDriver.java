package com.gos.crescendo2024;

import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverStationLedDriver {
    public enum BitField {
        ARDUINO_BIT_0((1 << 24)),
        ARDUINO_BIT_1((1 << 25)),
        ARDUINO_BIT_2((1 << 26)),
        ARDUINO_BIT_3((1 << 27)),
        ARDUINO_BIT_4((1 << 28)),
        ARDUINO_BIT_5((1 << 29)),
        ARDUINO_BIT_6((1 << 30)),
        ARDUINO_BIT_7((1 << 31)),

        ARDUINO_BIT_8((1 << 8)),
        ARDUINO_BIT_9((1 << 9)),
        ARDUINO_BIT_10((1 << 10)),
        ARDUINO_BIT_11((1 << 11)),
        ARDUINO_BIT_12((1 << 12)),
        ARDUINO_BIT_13((1 << 13)),
        ARDUINO_BIT_14((1 << 14)),
        ARDUINO_BIT_15((1 << 15));

        public final int m_bit;

        BitField(int bit) {
            m_bit = bit;
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

    public void setBit(BitField bit, boolean value) {
        if (value) {
            m_data |= bit.m_bit;
        } else {
            m_data &= ~bit.m_bit;
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
        DriverStationJNI.setJoystickOutputs(m_port, outputs, leftRumble, rightRumble);


        SmartDashboard.putNumber("LED Data", m_data);
        SmartDashboard.putNumber("LED Right", rightRumble);
        SmartDashboard.putNumber("LED Left", leftRumble);
    }
}
