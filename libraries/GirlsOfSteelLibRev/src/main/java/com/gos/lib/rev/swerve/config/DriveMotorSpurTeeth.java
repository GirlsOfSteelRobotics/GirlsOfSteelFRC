package com.gos.lib.rev.swerve.config;

public enum DriveMotorSpurTeeth {
    T19(19),
    T20(20),
    T21(21),
    T22(22);

    public final int m_teeth;

    DriveMotorSpurTeeth(int teeth) {
        m_teeth = teeth;
    }
}
