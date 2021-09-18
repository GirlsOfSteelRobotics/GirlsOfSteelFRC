package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.Command;


public class Buttons extends CommandBase {

    Command autoShoot = new ShootUsingTable(true);
    Command incrementShoot = new IncrementShoot();
    Command manualShoot = new ManualShoot();
    Command autoTurret = new TurretTrackTarget();
    Command setPointTurret = new SetPointTurret();
    Command manualTurret = new ManualTurret();

    protected void initialize() {
    }

    protected void execute() {
        //rollers
        if (oi.areTopRollersOverriden()) {
            //System.out.println("Top Rollers Override" );
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Overriden" + oi.areTopRollersOverriden());
            //DriverStationLCD.getInstance().updateLCD();
            if (oi.areTopRollersForward()) {
   //             DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Forward1" + oi.getTopRollersSwitchValue());
     //           DriverStationLCD.getInstance().updateLCD();
       //         System.out.println("Top Rollers Forward" + oi.getTopRollersSwitchValue());
                shooter.topRollersForward();
            } else if (oi.areTopRollersReverse()) {
//                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Reverse3" + oi.getTopRollersSwitchValue());
//                DriverStationLCD.getInstance().updateLCD();
//                System.out.println("Top Rollers Reverse");
                shooter.topRollersBackward();
            } else if (oi.areTopRollersOff()) {
//                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Top Rollers Off2" + oi.getTopRollersSwitchValue());
//                DriverStationLCD.getInstance().updateLCD();
//                 System.out.println("Top Rollers Off");
                shooter.topRollersOff();
            }
        }
        if (oi.isBrushForward()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Brush Rollers Forward1" + oi.getBrushSwitchValue());
            collector.forwardBrush();
           //  System.out.println("Brush Rollers Forward" + oi.getBrushSwitchValue());
        } else if (oi.isBrushReverse()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Brush Rollers Reverse3" + oi.getBrushSwitchValue());
             //System.out.println("Brush Rollers Reverse" + oi.getBrushSwitchValue());
            collector.reverseBrush();
        } else if (oi.isBrushOff()) {
           // DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Brush Rollers Off2" + oi.getBrushSwitchValue());
             //System.out.println("Brush Rollers Off" + oi.getBrushSwitchValue());
            collector.stopBrush();
        }
        if (oi.isMiddleCollectorForward()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Middle Rollers Forward1" + oi.getMiddleCollectorSwitchValue());
             //System.out.println("Middle Rollers Forward" + oi.getMiddleCollectorSwitchValue());
            collector.forwardMiddleConveyor();
        } else if (oi.isMiddleCollectorReverse()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Middle Rollers Reverse3" + oi.getMiddleCollectorSwitchValue());
             //System.out.println("Middle Rollers Reverse" + oi.getMiddleCollectorSwitchValue());
            collector.reverseMiddleConveyor();
        } else if (oi.isMiddleCollectorOff()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Middle Rollers Off2" + oi.getMiddleCollectorSwitchValue());
             //System.out.println("Middle Rollers Off" + oi.getMiddleCollectorSwitchValue());
            collector.stopMiddleConveyor();
        }
        //shooter
        if (oi.isShooterAutoOn()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "AutoShootSwitchOn!");
            ///DriverStationLCD.getInstance().updateLCD();
            if (oi.isShootRunning()) {
               // DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Shoot On:" + oi.isShootRunning());
               // System.out.println("AutoShoot On" + oi.isShootRunning());
               // DriverStationLCD.getInstance().updateLCD();
                autoShoot.start();
            }
            if (oi.isStopShooterRunning()) {
                //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Shoot Off" + oi.isShootRunning());
       //         System.out.println("AutoShoot Off" + oi.isStopShooterRunning());
         //       DriverStationLCD.getInstance().updateLCD();
                autoShoot.cancel();
            }
            //Increment is relative
        } else if (oi.isShooterIncrementOn()) {
           // DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "ShooterIncrementSwitchOn!");
           // DriverStationLCD.getInstance().updateLCD();
            if (oi.isShootRunning()) {
            //    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Relative On" +  oi.isShootRunning());
              //  System.out.println("Relative On" + oi.isShootRunning() + oi.getShooterSliderValue());
                //DriverStationLCD.getInstance().updateLCD();
                incrementShoot.start();
            } if (oi.isStopShooterRunning()) {
             //   DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Relative Off" + oi.isShootRunning());
               // System.out.println("Relative On" + oi.isStopShooterRunning() + oi.getShooterSliderValue());
              //  DriverStationLCD.getInstance().updateLCD();
                 incrementShoot.cancel();
            }
        } else if (oi.isShooterManualOn()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "ManualSwitchOn");
            //DriverStationLCD.getInstance().updateLCD();

            if (oi.isShootRunning()) {
              //  DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Manual On" + oi.isShootRunning());
                //System.out.println("Manual On" + oi.isShootRunning() + oi.getShooterSliderValue());
               // DriverStationLCD.getInstance().updateLCD();
                 manualShoot.start();
            } if (oi.isStopShooterRunning()) {
              //  DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Manual Off" + oi.isShootRunning());
              //  System.out.println("Manual Off" + oi.isStopShooterRunning() + oi.getShooterSliderValue());
              //  DriverStationLCD.getInstance().updateLCD();
                  manualShoot.cancel();
            }
        }
        //turret
        if (oi.isTurretAutoOn()) {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
              //      "Auto Turret On" + oi.getTurretOverrideSwitchValue());
            autoTurret.start();
        } else {
            //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
              //      "Auto Turret Off" + oi.getTurretOverrideSwitchValue());
//            System.out.println("Auto Turret Off" + oi.getTurretOverrideSwitchValue());
            autoTurret.cancel();
        } 
        
        if (oi.isTurretSetPositionOn()) {
//            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1,
//                  "Turret Set Position On" + oi.getTurretOverrideSwitchValue());
//            System.out.println("Turret Set Position On" + oi.getTurretOverrideSwitchValue());
            setPointTurret.start();
          //  DriverStation.getInstance().setDigitalOut(oi.TURRET_SET_POSITION_LIGHT, true);
        } else {//NOTHING IS SET TO HAPPEN IN HERE FOR SOME ODD REASON!!!
//            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Auto Turret Off" + oi.getTurretOverrideSwitchValue());
//             System.out.println("Auto Turret Off" + oi.getTurretOverrideSwitchValue());
//            DriverStation.getInstance().setDigitalOut(oi.TURRET_SET_POSITION_LIGHT, false);
            setPointTurret.cancel();
        } 
        
        if (oi.isTurretManualOn()) {
//            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Manual Turret On" + oi.getTurretOverrideSwitchValue());
//              System.out.println("Manual Turret On" + oi.getTurretOverrideSwitchValue());
            manualTurret.start();
            //DriverStation.getInstance().setDigitalOut(oi.TURRET_MANUAL_LIGHT, true);
        } else{
//            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "Manual Turret Off" + oi.getTurretOverrideSwitchValue());
//           System.out.println("Manual Turret Off" + oi.getTurretOverrideSwitchValue());
            manualTurret.cancel();
           //DriverStation.getInstance().setDigitalOut(oi.TURRET_MANUAL_LIGHT, false);
        }
//        DriverStationLCD.getInstance().updateLCD();
    }
   
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        collector.stopBrush();
        collector.stopMiddleConveyor();
        shooter.topRollersOff();
        shooter.stopJags();
        shooter.stopEncoder();
        turret.stopJag();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
}
