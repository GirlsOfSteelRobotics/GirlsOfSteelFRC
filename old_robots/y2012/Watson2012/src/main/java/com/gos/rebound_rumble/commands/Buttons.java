package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;


public class Buttons extends Command {

    private final Chassis m_chassis;
    private final Shooter m_shooter;
    private final Collector m_collector;
    private final Turret m_turret;
    private final OI m_oi;

    private final Command m_autoShoot;
    private final Command m_incrementShoot;
    private final Command m_manualShoot;
    private final Command m_autoTurret;
    private final Command m_setPointTurret;
    private final Command m_manualTurret;


    public Buttons(Chassis chassis, Shooter shooter, Collector collector, Turret turret, OI oi) {
        m_chassis = chassis;
        m_shooter = shooter;
        m_collector = collector;
        m_turret = turret;
        m_oi = oi;

        m_autoShoot = new ShootUsingTable(shooter, oi, true);
        m_incrementShoot = new IncrementShoot(shooter, oi);
        m_manualShoot = new ManualShoot(shooter, oi);
        m_autoTurret = new TurretTrackTarget(turret, oi.getOperatorJoystick());
        m_setPointTurret = new SetPointTurret(turret, oi);
        m_manualTurret = new ManualTurret(turret, oi);
    }

    @Override
    public void initialize() {
    }

    @SuppressWarnings("PMD")
    @Override
    public void execute() {
        //rollers
        if (m_oi.areTopRollersOverriden()) {
            //System.out.println("Top Rollers Override" );
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Overriden" + oi.areTopRollersOverriden());
            //DriverStationLCD.getInstance().updateLCD();
            if (m_oi.areTopRollersForward()) {
                //             DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Forward1" + oi.getTopRollersSwitchValue());
                //           DriverStationLCD.getInstance().updateLCD();
                //         System.out.println("Top Rollers Forward" + oi.getTopRollersSwitchValue());
                m_shooter.topRollersForward();
            } else if (m_oi.areTopRollersReverse()) {
                //                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Reverse3" + oi.getTopRollersSwitchValue());
                //                DriverStationLCD.getInstance().updateLCD();
                //                System.out.println("Top Rollers Reverse");
                m_shooter.topRollersBackward();
            } else if (m_oi.areTopRollersOff()) {
                //                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Off2" + oi.getTopRollersSwitchValue());
                //                DriverStationLCD.getInstance().updateLCD();
                //                 System.out.println("Top Rollers Off");
                m_shooter.topRollersOff();
            }
        }
        if (m_oi.isBrushForward()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Brush Rollers Forward1" + oi.getBrushSwitchValue());
            m_collector.forwardBrush();
            //  System.out.println("Brush Rollers Forward" + oi.getBrushSwitchValue());
        } else if (m_oi.isBrushReverse()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Brush Rollers Reverse3" + oi.getBrushSwitchValue());
            //System.out.println("Brush Rollers Reverse" + oi.getBrushSwitchValue());
            m_collector.reverseBrush();
        } else if (m_oi.isBrushOff()) {
            // DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Brush Rollers Off2" + oi.getBrushSwitchValue());
            //System.out.println("Brush Rollers Off" + oi.getBrushSwitchValue());
            m_collector.stopBrush();
        }
        if (m_oi.isMiddleCollectorForward()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Middle Rollers Forward1" + oi.getMiddleCollectorSwitchValue());
            //System.out.println("Middle Rollers Forward" + oi.getMiddleCollectorSwitchValue());
            m_collector.forwardMiddleConveyor();
        } else if (m_oi.isMiddleCollectorReverse()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Middle Rollers Reverse3" + oi.getMiddleCollectorSwitchValue());
            //System.out.println("Middle Rollers Reverse" + oi.getMiddleCollectorSwitchValue());
            m_collector.reverseMiddleConveyor();
        } else if (m_oi.isMiddleCollectorOff()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Middle Rollers Off2" + oi.getMiddleCollectorSwitchValue());
            //System.out.println("Middle Rollers Off" + oi.getMiddleCollectorSwitchValue());
            m_collector.stopMiddleConveyor();
        }
        //shooter
        if (m_oi.isShooterAutoOn()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "AutoShootSwitchOn!");
            ///DriverStationLCD.getInstance().updateLCD();
            if (m_oi.isShootRunning()) {
                // DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Shoot On:" + oi.isShootRunning());
                // System.out.println("AutoShoot On" + oi.isShootRunning());
                // DriverStationLCD.getInstance().updateLCD();
                m_autoShoot.schedule();
            }
            if (m_oi.isStopShooterRunning()) {
                //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Shoot Off" + oi.isShootRunning());
                //         System.out.println("AutoShoot Off" + oi.isStopShooterRunning());
                //       DriverStationLCD.getInstance().updateLCD();
                m_autoShoot.cancel();
            }
            //Increment is relative
        } else if (m_oi.isShooterIncrementOn()) {
            // DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "ShooterIncrementSwitchOn!");
            // DriverStationLCD.getInstance().updateLCD();
            if (m_oi.isShootRunning()) {
                //    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Relative On" +  oi.isShootRunning());
                //  System.out.println("Relative On" + oi.isShootRunning() + oi.getShooterSliderValue());
                //DriverStationLCD.getInstance().updateLCD();
                m_incrementShoot.schedule();
            }
            if (m_oi.isStopShooterRunning()) {
                //   DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Relative Off" + oi.isShootRunning());
                // System.out.println("Relative On" + oi.isStopShooterRunning() + oi.getShooterSliderValue());
                //  DriverStationLCD.getInstance().updateLCD();
                m_incrementShoot.cancel();
            }
        } else if (m_oi.isShooterManualOn()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "ManualSwitchOn");
            //DriverStationLCD.getInstance().updateLCD();

            if (m_oi.isShootRunning()) {
                //  DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Manual On" + oi.isShootRunning());
                //System.out.println("Manual On" + oi.isShootRunning() + oi.getShooterSliderValue());
                // DriverStationLCD.getInstance().updateLCD();
                m_manualShoot.schedule();
            }
            if (m_oi.isStopShooterRunning()) {
                //  DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Manual Off" + oi.isShootRunning());
                //  System.out.println("Manual Off" + oi.isStopShooterRunning() + oi.getShooterSliderValue());
                //  DriverStationLCD.getInstance().updateLCD();
                m_manualShoot.cancel();
            }
        }
        //turret
        if (m_oi.isTurretAutoOn()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
            //      "Auto Turret On" + oi.getTurretOverrideSwitchValue());
            m_autoTurret.schedule();
        } else {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
            //      "Auto Turret Off" + oi.getTurretOverrideSwitchValue());
            //            System.out.println("Auto Turret Off" + oi.getTurretOverrideSwitchValue());
            m_autoTurret.cancel();
        }

        if (m_oi.isTurretSetPositionOn()) {
            //            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
            //                  "Turret Set Position On" + oi.getTurretOverrideSwitchValue());
            //            System.out.println("Turret Set Position On" + oi.getTurretOverrideSwitchValue());
            m_setPointTurret.schedule();
            //  DriverStation.setDigitalOut(oi.TURRET_SET_POSITION_LIGHT, true);
        } else { //NOTHING IS SET TO HAPPEN IN HERE FOR SOME ODD REASON!!!
            //            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Auto Turret Off" + oi.getTurretOverrideSwitchValue());
            //             System.out.println("Auto Turret Off" + oi.getTurretOverrideSwitchValue());
            //            DriverStation.setDigitalOut(oi.TURRET_SET_POSITION_LIGHT, false);
            m_setPointTurret.cancel();
        }

        if (m_oi.isTurretManualOn()) {
            //            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Manual Turret On" + oi.getTurretOverrideSwitchValue());
            //              System.out.println("Manual Turret On" + oi.getTurretOverrideSwitchValue());
            m_manualTurret.schedule();
            //DriverStation.setDigitalOut(oi.TURRET_MANUAL_LIGHT, true);
        } else {
            //            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Manual Turret Off" + oi.getTurretOverrideSwitchValue());
            //           System.out.println("Manual Turret Off" + oi.getTurretOverrideSwitchValue());
            m_manualTurret.cancel();
            //DriverStation.setDigitalOut(oi.TURRET_MANUAL_LIGHT, false);
        }
        //        DriverStationLCD.getInstance().updateLCD();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
        m_shooter.topRollersOff();
        m_shooter.stopJags();
        m_shooter.stopEncoder();
        m_turret.stopJag();
        m_chassis.stopJags();
    }


}
