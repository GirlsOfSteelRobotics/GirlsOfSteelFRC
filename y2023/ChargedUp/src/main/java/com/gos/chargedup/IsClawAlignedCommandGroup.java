package com.gos.chargedup;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IsClawAlignedCommandGroup extends SequentialCommandGroup {

    private ChassisSubsystem m_chassis;
    private ArmSubsystem m_arm;
    private TurretSubsystem m_turret;

    private double m_robotAngle;

    private double m_turretAngle;

    private double m_armExtension;


    public IsClawAlignedCommandGroup(ChassisSubsystem chassis, ArmSubsystem arm, TurretSubsystem turret) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super();
        m_chassis = chassis;
        m_arm = arm;
        m_turret = turret;

        m_robotAngle = chassis.getPose().getRotation().getDegrees();
        m_turretAngle = m_turret.getTurretAngleDeg();
        //m_armExtension = arm.

    }


}