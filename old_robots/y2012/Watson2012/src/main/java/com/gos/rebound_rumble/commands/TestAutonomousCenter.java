package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;

public class TestAutonomousCenter extends SequentialCommandGroup {

    private final boolean m_autoTrack;

    private final Chassis m_chassis;
    private final Collector m_collector;
    private final Shooter m_shooter;

    public TestAutonomousCenter(Chassis chassis, Collector collector, Shooter shooter, Turret turret) {
        m_chassis = chassis;
        m_collector = collector;
        m_shooter = shooter;

        SmartDashboard.putBoolean("Auto Track?", false);
        m_autoTrack = SmartDashboard.getBoolean("Auto Track?", false);
        addCommands(new AutonomousKeyShootTwoBalls(chassis, collector, shooter, turret, m_autoTrack));
        /* inits shooter encoder & PID
         * gets distance from the camera
         * gets the velocity needed to shoot using that distance
         * sets the PID setpoint (speed) to that velocity
         * starts the top rollers, middle conveyor, & brush
         * when the PID is within 1.0 of the target velocity
         * ends after the magical value of shooting 2 balls (right now, set to 8)
         * stops the PID, rop rollers, brush, & middle conveyor
         */
        //        addCommands(new MoveToSetPoint(distance));
        ////        //move 2.0meters towards the bridges
        //        addParallel(new BridgeDownContinuous());
        ////        //push the bridge down
        //        addParallel(new Collect());
        ////        //start collecting balls (turn on bottom 2 collectors)
        //        addCommands(new ShootUsingTable());
        //shoot again -> using camera distance & the table
    }

    @Override
    public void end(boolean interrupted) {
        new DisableChassis(m_chassis).schedule();
        new DisableShooter(m_shooter).schedule();
        new StopCollectors(m_collector).schedule();
    }



}
