package com.gos.lib.rev.swerve.config;

public enum SwerveGearingKit {
    LOW(DriveMotorPinionTeeth.T12, DriveMotorSpurTeeth.T22),
    MEDIUM(DriveMotorPinionTeeth.T13, DriveMotorSpurTeeth.T22),
    HIGH(DriveMotorPinionTeeth.T14, DriveMotorSpurTeeth.T22),

    EXTRA_HIGH_1(DriveMotorPinionTeeth.T14, DriveMotorSpurTeeth.T21),
    EXTRA_HIGH_2(DriveMotorPinionTeeth.T14, DriveMotorSpurTeeth.T20),
    EXTRA_HIGH_3(DriveMotorPinionTeeth.T15, DriveMotorSpurTeeth.T20),
    EXTRA_HIGH_4(DriveMotorPinionTeeth.T16, DriveMotorSpurTeeth.T20),
    EXTRA_HIGH_5(DriveMotorPinionTeeth.T16, DriveMotorSpurTeeth.T19);

    public final DriveMotorPinionTeeth m_pinionTeeth;
    public final DriveMotorSpurTeeth m_spurTeeth;

    SwerveGearingKit(DriveMotorPinionTeeth driveMotorPinionTeeth, DriveMotorSpurTeeth driveMotorSpurTeeth) {
        m_pinionTeeth = driveMotorPinionTeeth;
        m_spurTeeth = driveMotorSpurTeeth;
    }
}
