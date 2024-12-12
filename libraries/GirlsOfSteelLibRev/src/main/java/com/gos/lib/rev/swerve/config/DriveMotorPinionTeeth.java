package com.gos.lib.rev.swerve.config;

public enum DriveMotorPinionTeeth {
    T12(12),
    T13(13),
    T14(14),
    T15(15),
    T16(16);

    public final int m_teeth;

    DriveMotorPinionTeeth(int teeth) {
        m_teeth = teeth;
    }
}
